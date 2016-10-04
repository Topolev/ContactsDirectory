package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.frontcontroller.Command;
import by.topolev.contacts.servlets.utils.ServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static by.topolev.contacts.servlets.utils.ServletUtil.createPaginator;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class SearchFormPostCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(SearchFormPostCommand.class);

    public static final String COUNT_ROW = "countRow";
    public static final String PAGE = "page";
    public static final String PAGINATOR = "paginator";
    public static final String COUNT = "count";
    public static final String CONTACT_LIST = "contactList";
    public static final String LIST_FIELDS = "listFields";

    private List<String> listFields = new ArrayList<String>(Arrays.asList("first_name","last_name","middle_name","sex","marital_status","nationality", "country", "city", "birthdaymore", "birthdayless"));

    private ContactService contactService = ContactServiceFactory.getContactService();


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String, String> valueFields = getValueFields(req);
        LOG.debug(valueFields.toString());


        int numberOfSearchedContacts = contactService.getCountSearchContact(valueFields);
        int numberOfContactsOnPage = ServletUtil.getRequestParameter(req, COUNT_ROW, int.class, 10);
        int page = ServletUtil.getRequestParameter(req, PAGE, int.class, 0);
        int numberOfPages = (int) Math.ceil((double)numberOfSearchedContacts/numberOfContactsOnPage);

        List<Contact> contactList = contactService.getSearchContact(valueFields, page, numberOfContactsOnPage);
        
        req.setAttribute(PAGINATOR, createPaginator(page,numberOfPages));
        req.setAttribute(COUNT_ROW, numberOfContactsOnPage);
        req.setAttribute(PAGE,page);
        req.setAttribute(COUNT, numberOfSearchedContacts);
        req.setAttribute(CONTACT_LIST, contactList);
        req.setAttribute(LIST_FIELDS, getListOfValuesForFormFields(valueFields));

        return "/searchformwithresult.jsp";
    }

    private Map<String, String> getValueFields(HttpServletRequest req){
        Map<String, String> valueFields = new HashMap<>();
        for (String nameField : listFields){
            String value = ServletUtil.getPostParameter(nameField, req);
            if (value != null){
                valueFields.put(nameField, value.trim());
                req.setAttribute(nameField, value.trim());
            }
        }
        return valueFields;
    }

    private String getListOfValuesForFormFields(Map<String, String> valueFields){
        StringBuilder listOfValues = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = valueFields.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            listOfValues.append(entry.getKey()).append("=");
            if (iterator.hasNext()){
                listOfValues.append("&");
            }
        }
        return listOfValues.toString();
    }

}

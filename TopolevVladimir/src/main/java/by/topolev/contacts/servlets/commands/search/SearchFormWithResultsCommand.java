package by.topolev.contacts.servlets.commands.search;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.frontcontroller.Command;
import by.topolev.contacts.servlets.utils.PageAttributes;
import by.topolev.contacts.servlets.utils.PageAttributes.SearchForm;
import by.topolev.contacts.servlets.utils.ServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static by.topolev.contacts.servlets.utils.PageNames.PAGE_SEARCH_FORM_WITH_RESULT;
import static by.topolev.contacts.servlets.utils.ServletUtil.createPaginator;
import static by.topolev.contacts.servlets.utils.ServletUtil.getPostParameter;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class SearchFormWithResultsCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(SearchFormWithResultsCommand.class);

    private List<String> listFields = new ArrayList<String>(Arrays.asList("first_name", "last_name", "middle_name", "sex", "marital_status", "nationality", "country", "city", "birthdaymore", "birthdayless"));

    private ContactService contactService = ContactServiceFactory.getContactService();


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String, String> valueFields = getValueFields(req);
        LOG.debug(valueFields.toString());


        int numberOfSearchedContacts = contactService.getCountSearchContact(valueFields);
        int numberOfContactsOnPage = ServletUtil.getRequestParameter(req, SearchForm.PARAM_COUNT_ROW, int.class, 10);
        int page = ServletUtil.getRequestParameter(req, SearchForm.PARAM_PAGE, int.class, 0);
        int numberOfPages = (int) Math.ceil((double) numberOfSearchedContacts / numberOfContactsOnPage);

        List<Contact> contactList = contactService.getSearchContact(valueFields, page, numberOfContactsOnPage);

        populateSearchContactPaginator(req, page, numberOfPages, numberOfSearchedContacts, numberOfContactsOnPage);
        populateSearchContact(req, contactList, valueFields);

        return PAGE_SEARCH_FORM_WITH_RESULT;
    }

    private void populateSearchContactPaginator(HttpServletRequest req, Integer page, Integer numberOfPages, Integer numberOfSearchedContacts, Integer numberOfContactsOnPage) {
        req.setAttribute(SearchForm.ATTR_PAGE, page);
        req.setAttribute(SearchForm.ATTR_PAGINATOR, createPaginator(page, numberOfPages));
        req.setAttribute(SearchForm.ATTR_COUNT, numberOfSearchedContacts);
        req.setAttribute(SearchForm.ATTR_COUNT_ROW, numberOfContactsOnPage);
    }

    private void populateSearchContact(HttpServletRequest req, List<Contact> contactList, Map<String, String> valueFields){
        req.setAttribute(SearchForm.ATTR_CONTACT_LIST, contactList);
        req.setAttribute(SearchForm.ATTR_LIST_FIELDS, getListOfValuesForFormFields(valueFields));
    }

    private Map<String, String> getValueFields(HttpServletRequest req) {
        Map<String, String> valueFields = new HashMap<>();
        for (String nameField : listFields) {
            String value = getPostParameter(nameField, req);
            if (value != null) {
                valueFields.put(nameField, value.trim());
                req.setAttribute(nameField, value.trim());
            }
        }
        return valueFields;
    }

    private String getListOfValuesForFormFields(Map<String, String> valueFields) {
        return valueFields.entrySet().stream()
                .map(entry -> new StringBuilder().append(entry.getKey()).append("=").append(entry.getValue()).toString())
                .collect(Collectors.joining("&"));
    }

}

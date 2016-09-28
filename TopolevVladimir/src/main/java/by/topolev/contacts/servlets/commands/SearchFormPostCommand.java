package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.formdata.Paginator;
import by.topolev.contacts.servlets.frontcontroller.Command;
import by.topolev.contacts.servlets.utils.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class SearchFormPostCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(SearchFormPostCommand.class);
    public static final String ISO_8859_1 = "ISO-8859-1";

    private List<String> listFields = new ArrayList<String>(Arrays.asList("first_name","last_name","middle_name","sex","marital_status","nationality", "country", "city"));

    private ContactService contactService = ContactServiceFactory.getContactService();


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String, String> valueFields = new HashMap<>();
        StringBuilder listFieldsForGet = new StringBuilder();

        for (String field : listFields){
            String value = getPostParameter(field, req);
            if (value != null){
                //value = new String(value.getBytes(ISO_8859_1), StandardCharsets.UTF_8);
                valueFields.put(field,value);
                req.setAttribute(field, value);
                listFieldsForGet.append(field).append("=").append(value/*new String(value.getBytes("UTF-8"), StandardCharsets.ISO_8859_1)*/).append("&");
            }
        }
        if (listFieldsForGet.length() >=1) {
            listFieldsForGet.delete(listFieldsForGet.length() - 1, listFieldsForGet.length());
        }
        LOG.debug(listFieldsForGet.toString());

        int count = contactService.getCountSearchContact(valueFields);
        int countRow = ServletUtil.getRequestParameter(req, "countRow", int.class, 10);
        int page = ServletUtil.getRequestParameter(req, "page", int.class, 0);
        int countPage = (int) Math.ceil((double)count/countRow);

        List<Contact> contactList = contactService.getSearchContact(valueFields, page, countRow);


        Paginator paginator = ServletUtil.createPaginator(page,countPage);


        req.setAttribute("paginator",paginator);
        req.setAttribute("countRow", countRow);
        req.setAttribute("page",page);
        req.setAttribute("count", count);
        req.setAttribute("contactList", contactList);
        req.setAttribute("listFields", listFieldsForGet.toString());





        /*req.setAttribute("count", count);
        req.setAttribute("page", page);
        req.setAttribute("countRow", countRow);
        req.setAttribute("sortType", sortType);
        req.setAttribute("paginator", paginator);
        */


        return "/searchformwithresult.jsp";
    }

    private String getPostParameter(String nameField, HttpServletRequest req){
        String value = req.getParameter(nameField);
        if (value==null || "".equals(value)) return null;
        return value;
    }
}

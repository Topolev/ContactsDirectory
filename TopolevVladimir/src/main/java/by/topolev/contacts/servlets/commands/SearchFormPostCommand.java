package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.frontcontroller.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.nio.charset.StandardCharsets.*;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class SearchFormPostCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(SearchFormPostCommand.class);

    private List<String> listFields = new ArrayList<String>(Arrays.asList("first_name","last_name","middle_name","sex","marital_status","nationality", "country", "city"));

    private ContactService contactService = ContactServiceFactory.getContactService();


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String, String> valueFields = new HashMap<>();
        for (String field : listFields){
            String value = getPostParameter(field, req);
            if (value != null){
                value = new String(value.getBytes("ISO-8859-1"), StandardCharsets.UTF_8);
                valueFields.put(field,value);
                req.setAttribute(field, value);
            }
        }

        LOG.debug(valueFields.toString());

        List<Contact> contactList = contactService.getSearchContact(valueFields);

        LOG.debug(contactList.toString());

        req.setAttribute("contactList", contactList);



        /*req.setAttribute("count", count);
        req.setAttribute("page", page);
        req.setAttribute("countRow", countRow);
        req.setAttribute("sortField", sortField);
        req.setAttribute("sortType", sortType);
        req.setAttribute("paginator", paginator);
        req.setAttribute("sortFields", sortFields);*/


        return "/searchformwithresult.jsp";
    }

    private String getPostParameter(String nameField, HttpServletRequest req){
        String value = req.getParameter(nameField);
        if (value==null || "".equals(value)) return null;
        return value;
    }
}

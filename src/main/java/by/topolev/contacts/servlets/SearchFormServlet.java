package by.topolev.contacts.servlets;



import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Vladimir on 15.09.2016.
 */
public class SearchFormServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SearchFormServlet.class);

    private List<String> listFields = new ArrayList<String>(Arrays.asList("first_name","last_name","middle_name","sex","marital_status","nationality", "country", "city"));

    private ContactService contactService = ContactServiceFactory.getContactService();


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("SERACH FORM");



        req.getRequestDispatcher("/searchform.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Map<String, String> valueFields = new HashMap<>();
        for (String field : listFields){
            String value = getPostParameter(field,req);
            if (value != null){
                valueFields.put(field,value);
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

        req.getRequestDispatcher("/contact_list.jsp").forward(req, resp);
    }

    private String getPostParameter(String nameField, HttpServletRequest req){
        String value = req.getParameter(nameField);
        if (value==null || "".equals(value)) return null;
        return value;
    }
}

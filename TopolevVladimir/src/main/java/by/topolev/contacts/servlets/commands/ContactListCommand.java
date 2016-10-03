package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.formdata.InfoSortField;
import by.topolev.contacts.servlets.formdata.Paginator;
import by.topolev.contacts.servlets.frontcontroller.Command;
import by.topolev.contacts.servlets.utils.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static by.topolev.contacts.servlets.utils.ServletUtil.createPaginator;
import static by.topolev.contacts.servlets.utils.ServletUtil.getRequestParameter;

/**
 * Created by Vladimir on 17.09.2016.
 */
public class ContactListCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(ContactListCommand.class);
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    private ContactService contactService = ContactServiceFactory.getContactService();


    public ContactListCommand(){};

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        Integer count = contactService.getCountContacts();
        Integer page = getRequestParameter(req, "page", Integer.class, 0);
        Integer countRow = getRequestParameter(req, "countRow", Integer.class, 10);
        Integer sortField = getRequestParameter(req, "sortField", Integer.class, null);
        String sortType = getValidSortType(req);
        Integer countPage = (int) Math.ceil((double)count/countRow);

        List<InfoSortField> sortFields = getDefaultSortFields();

        req.setAttribute("contactList", getSortedContactList(sortField, sortType, page,countRow, sortFields));
        req.setAttribute("count", count);
        req.setAttribute("page", page);
        req.setAttribute("countRow", countRow);
        req.setAttribute("sortField", sortField);
        req.setAttribute("sortType", sortType);
        req.setAttribute("paginator", createPaginator(page, countPage));
        req.setAttribute("sortFields", sortFields);

        return "contact_list.jsp";
    }

    private String getValidSortType(HttpServletRequest req) {
        String sortType = getRequestParameter(req, "sortType", String.class, null);
        boolean isNotValid = !ASC.equalsIgnoreCase(sortType) && !DESC.equalsIgnoreCase(sortType);
        return isNotValid ? ASC : sortType;
    }

    private List<Contact> getSortedContactList(Integer sortField, String sortType, Integer page,Integer countRow,  List<InfoSortField> sortFields){
        List<Contact> contactList;
        if ((sortField == null) || (sortType == null)){
            contactList = contactService.getLimitContactList(page, countRow);
        } else {
            sortFields.get(sortField).setChoosenField(true);
            if (sortType.equals(ASC)) {
                sortFields.get(sortField).setSortType(DESC);
            }
            else {
                sortFields.get(sortField).setSortType(ASC);
            }
            contactList = contactService.getLimitContactList(page, countRow, sortFields.get(sortField).getNameSortField(), sortType);
        }
        return contactList;
    }

    private List<InfoSortField> getDefaultSortFields(){
        List<InfoSortField> sortFields = new ArrayList<>();
        sortFields.add(0, new InfoSortField("first_name"));
        sortFields.add(1, new InfoSortField("birthday"));
        sortFields.add(2, new InfoSortField("address"));
        sortFields.add(3, new InfoSortField("work_place"));
        return sortFields;
    }

}

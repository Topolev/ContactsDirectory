package by.topolev.contacts.servlets.commands.contact;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.formdata.InfoSortField;
import by.topolev.contacts.servlets.frontcontroller.Command;

import by.topolev.contacts.servlets.utils.PageAttributes.PageContactList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static by.topolev.contacts.servlets.utils.PageNames.PAGE_CONTACT_LIST;
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

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        LOG.debug("Show list of contacts");

        Integer numberOfContacts = contactService.getCountContacts();
        Integer page = getRequestParameter(req,  PageContactList.PARAM_PAGE, Integer.class, 0);
        Integer numberOfContactsOnPage = getRequestParameter(req, PageContactList.PARAM_COUNT_ROW, Integer.class, 10);
        Integer numberOfPages = (int) Math.ceil((double) numberOfContacts / numberOfContactsOnPage);

        Integer sortField = getRequestParameter(req, PageContactList.PARAM_SORT_FIELD, Integer.class, null);
        String sortType = getValidSortType(req);

        populateContactPaginator(req, numberOfContacts, page, numberOfContactsOnPage, numberOfPages);
        populateContactTable(req, sortField, sortType, page, numberOfContactsOnPage);

        return PAGE_CONTACT_LIST;
    }

    private void populateContactPaginator(HttpServletRequest req, Integer numberOfContacts, Integer page, Integer numberOfContactsOnPage, Integer numberOfPages) {
        req.setAttribute(PageContactList.ATTR_COUNT, numberOfContacts);
        req.setAttribute(PageContactList.ATTR_PAGE, page);
        req.setAttribute(PageContactList.ATTR_COUNT_ROW, numberOfContactsOnPage);
        req.setAttribute(PageContactList.ATTR_PAGINATOR, createPaginator(page, numberOfPages));
    }

    private void populateContactTable(HttpServletRequest req, Integer sortField, String sortType, Integer page, Integer numberOfContactsOnPage){
        req.setAttribute(PageContactList.ATTR_SORT_FIELD, sortField);
        req.setAttribute(PageContactList.ATTR_SORT_TYPE, sortType);

        List<InfoSortField> sortFields = getDefaultSortFields();
        req.setAttribute(PageContactList.ATTR_CONTACT_LIST, getSortedContactList(sortField, sortType, page, numberOfContactsOnPage, sortFields));
        req.setAttribute(PageContactList.ATTR_SORT_FIELDS, sortFields);
    }

    private String getValidSortType(HttpServletRequest req) {
        String sortType = getRequestParameter(req, PageContactList.PARAM_SORT_TYPE, String.class, null);
        boolean isNotValid = !ASC.equalsIgnoreCase(sortType) && !DESC.equalsIgnoreCase(sortType);
        return isNotValid ? ASC : sortType;
    }

    private List<Contact> getSortedContactList(Integer sortField, String sortType, Integer page, Integer countRow, List<InfoSortField> sortFields) {
        List<Contact> contactList;
        if ((sortField == null) || (sortType == null)) {
            contactList = contactService.getLimitContactList(page, countRow);
        } else {
            sortFields.get(sortField).setChoosenField(true);
            if (sortType.equals(ASC)) {
                sortFields.get(sortField).setSortType(DESC);
            } else {
                sortFields.get(sortField).setSortType(ASC);
            }
            contactList = contactService.getLimitContactList(page, countRow, sortFields.get(sortField).getNameSortField(), sortType);
        }
        return contactList;
    }

    private List<InfoSortField> getDefaultSortFields() {
        List<InfoSortField> sortFields = new ArrayList<>();
        sortFields.add(0, new InfoSortField("first_name"));
        sortFields.add(1, new InfoSortField("birthday"));
        sortFields.add(2, new InfoSortField("address"));
        sortFields.add(3, new InfoSortField("work_place"));
        return sortFields;
    }

}

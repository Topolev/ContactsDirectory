package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.ContactListServlet;
import by.topolev.contacts.servlets.formdata.InfoSortField;
import by.topolev.contacts.servlets.formdata.Paginator;
import by.topolev.contacts.servlets.frontcontroller.Command;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static by.topolev.contacts.servlets.utils.ServletUtil.*;

/**
 * Created by Vladimir on 17.09.2016.
 */
public class ContactListCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(ContactListCommand.class);

    private ContactService contactService = ContactServiceFactory.getContactService();

    private static ObjectMapper map = new ObjectMapper();

    public ContactListCommand(){};

    @Override
    public String excute(HttpServletRequest req, HttpServletResponse resp) {
        int count = contactService.getCountContacts();

        int page = getRequestParametr(req, "page", int.class, 0);
        int countRow = getRequestParametr(req, "countRow", int.class, 10);
        Integer sortField = getRequestParametr(req, "sortField", Integer.class, null);
        String sortType = getRequestParametr(req, "sortType", String.class, null);

        List<InfoSortField> sortFields = getDefaultSortFields();

        List<Contact> contactList;
        if ((sortField == null) || (sortType == null)){
            contactList = contactService.getLimitContactList(page, countRow);
        } else {
            sortFields.get(sortField).setChoosenField(true);
            if (sortType.equals("ASC")) sortFields.get(sortField).setSortType("DESC");
            else sortFields.get(sortField).setSortType("ASC");

            contactList = contactService.getLimitContactList(page, countRow, sortFields.get(sortField).getNameSortField(), sortType);
        }

        int countPage = (int) Math.ceil((double)count/countRow);

        Paginator paginator = createPaginator(page, countPage);

        req.setAttribute("contactList", contactList);
        req.setAttribute("count", count);
        req.setAttribute("page", page);
        req.setAttribute("countRow", countRow);
        req.setAttribute("sortField", sortField);
        req.setAttribute("sortType", sortType);
        req.setAttribute("paginator", paginator);
        req.setAttribute("sortFields", sortFields);

        //req.getRequestDispatcher("/contact_list.jsp").forward(req, resp);
        return "contact_list.jsp";
    }


    private List<InfoSortField> getDefaultSortFields(){
        List<InfoSortField> sortFields = new ArrayList<>();
        sortFields.add(0, new InfoSortField("first_name"));
        sortFields.add(1, new InfoSortField("birthday"));
        sortFields.add(2, new InfoSortField("address"));
        sortFields.add(3, new InfoSortField("work_place"));
        return sortFields;
    }

    private Paginator createPaginator(int page, int countPage){
        Paginator paginator = new Paginator();
        paginator.setButtonNext((page+1) < countPage);
        paginator.setButtonPrev(page != 0);


        if (page < 2) {
            addSequencyNumberInCollection(paginator.getListPages(), 0, Math.min(5, countPage-1));
        } else if (page < countPage - 2){
            addSequencyNumberInCollection(paginator.getListPages(), page-1, page+1);
        } else {
            addSequencyNumberInCollection(paginator.getListPages(), Math.max(0, countPage - 5), countPage-1);
        }

        paginator.setSkipLeft(paginator.getListPages().get(0) != 0);
        paginator.setSkipRight((paginator.getListPages().get(paginator.getListPages().size()-1)) != (countPage-1));

        return paginator;
    }

    private void addSequencyNumberInCollection(List<Integer> list, int begin, int end){
        for (int i = begin; i <= end; i++){
            list.add(i);
        }
    }
}

package by.topolev.contacts.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.formdata.InfoSortField;
import by.topolev.contacts.servlets.formdata.Paginator;

public class ContactListServlet extends HttpServlet {
	
	private static final Logger LOG = LoggerFactory.getLogger(ContactListServlet.class);
	
	private ContactService contactService = ContactServiceFactory.getContactService();
	
	private static ObjectMapper map = new ObjectMapper();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		
		req.getRequestDispatcher("/contact_list.jsp").forward(req, resp);
	}
	
	private List<InfoSortField> getDefaultSortFields(){
		List<InfoSortField> sortFields = new ArrayList<>();
		sortFields.add(0, new InfoSortField("first_name"));
		sortFields.add(1, new InfoSortField("birthday"));
		sortFields.add(2, new InfoSortField("address"));
		sortFields.add(3, new InfoSortField("work_place"));
		return sortFields;
	}
	
	private <T> T getRequestParametr(HttpServletRequest req, String nameParametr, Class<T> clazz, T defaultValue){
		try {
			if (clazz == String.class) {
				if (!req.getParameter(nameParametr).equals("")) return (T) req.getParameter(nameParametr);
				else return null;
			} 
			return map.readValue(req.getParameter(nameParametr), clazz);
		} catch (IOException | NullPointerException e) {
			LOG.debug("Set default value for request parametr {}",nameParametr, e);
			return defaultValue;
		}
	}
	
	private void addSequencyNumberInCollection(List<Integer> list, int begin, int end){
		for (int i = begin; i <= end; i++){
			list.add(i);
		}	
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
	
	private String getValueCookie(HttpServletRequest req, String nameCookie) {
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(nameCookie))
				return cookie.getValue();
		}
		return null;
	}

	private void setValueCookie(HttpServletResponse resp, String nameCookie, String value) {
		Cookie cookie = new Cookie(nameCookie, value);
		resp.addCookie(cookie);
	}
}

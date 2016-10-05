package by.topolev.contacts.services;

import by.topolev.contacts.dao.ContactDao;
import by.topolev.contacts.dao.ContactDaoFactory;
import by.topolev.contacts.entity.Contact;

import javax.servlet.ServletException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ContactServiceImpl implements ContactService{
	
	private ContactDao contactDao = ContactDaoFactory.getContactDao();
	
	@Override
	public List<Contact> getContactList() {
		return contactDao.getContactList();
	}
	
	public int getCountContacts(){
		return contactDao.getCountContacts();
	}
	
	@Override
	public Contact getContactById(int id) {
		return contactDao.getContactById(id);
	}

	@Override
	public List<Contact> getContactById(Integer... idList) {
		return contactDao.getContactById(idList);
	}

	@Override
	public List<Contact> getLimitContactList(int page, int countRow) {
		return contactDao.getLimitContactList(page*countRow, countRow);
	}
	
	@Override
	public List<Contact> getLimitContactList(int page, int countRow, String sortField, String sortType){
		return contactDao.getLimitContactList(page*countRow, countRow, sortField, sortType);
	}

	@Override
	public List<Contact> getSearchContact(Map<String, String> valueFields) {
		return contactDao.getSearchContact(valueFields);
	}

	@Override
	public List<Contact> getSearchContact(Map<String, String> valueFields, int page, int countRow) {
		return contactDao.getSearchContact(valueFields,page*countRow, countRow);
	}

	@Override
	public int getCountSearchContact(Map<String, String> valueFields){
		return contactDao.getCountSearchContact(valueFields);
	}

	@Override
	public void deleteContact(Integer... idList) {
		contactDao.deleteContacts(idList);
	}

	@Override
	public Integer updateContact(Contact contact, boolean lazyLoad) {
		return contactDao.updateContact(contact, lazyLoad);
		
	}



	@Override
	public List<Contact> getContactListWhoTodayCelebrateBirthday() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM contact WHERE DAY(birthday) = ").append(day).append(" AND MONTH(birthday) = ").append(month);

		return contactDao.getContactListAccordingQuery(query.toString(), null);
	}


}

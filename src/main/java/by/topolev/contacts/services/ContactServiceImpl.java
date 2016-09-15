package by.topolev.contacts.services;

import java.util.List;

import by.topolev.contacts.dao.ContactDao;
import by.topolev.contacts.dao.ContactDaoFactory;
import by.topolev.contacts.entity.Contact;

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
	public List<Contact> getLimitContactList(int page, int countRow) {
		return contactDao.getLimitContactList(page*countRow, countRow);
	}
	
	@Override
	public List<Contact> getLimitContactList(int page, int countRow, String sortField, String sortType){
		return contactDao.getLimitContactList(page*countRow, countRow, sortField, sortType);
	}

	@Override
	public void deleteContact(Integer... idList) {
		contactDao.deleteContacts(idList);
	}

	@Override
	public void updateContact(Contact contact) {
		contactDao.updateContact(contact);
		
	}
	
	
}

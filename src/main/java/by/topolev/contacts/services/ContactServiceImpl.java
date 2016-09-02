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

	@Override
	public Contact getContactById(int id) {
		return contactDao.getContactById(id);
	}

}

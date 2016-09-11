package by.topolev.contacts.services;

import java.util.List;

import by.topolev.contacts.entity.Contact;

public interface ContactService {
	List<Contact> getContactList();
	int getCountContacts();
	List<Contact> getLimitContactList(int beginRow, int countRow);
	List<Contact> getLimitContactList(int page, int countRow, String sortField, String sortType);
	Contact getContactById(int id);
	void deleteContact(int...idList);
	void createContact(Contact contact);
}

package by.topolev.contacts.dao;

import java.util.List;

import by.topolev.contacts.entity.Contact;

public interface ContactDao {
	List<Contact> getContactList();
	List<Contact> getLimitContactList(int beginRow, int countRow);
	List<Contact> getLimitContactList(int beginRow, int countRow, String sortField, String sortType);
	Contact getContactById(int id);
	int getCountContacts();
	void deleteContacts(int...id);
}

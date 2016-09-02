package by.topolev.contacts.dao;

import java.util.List;

import by.topolev.contacts.entity.Contact;

public interface ContactDao {
	List<Contact> getContactList();
	Contact getContactById(int id);
	int getCountContacts();
}

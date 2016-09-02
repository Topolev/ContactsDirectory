package by.topolev.contacts.services;

import java.util.List;

import by.topolev.contacts.entity.Contact;

public interface ContactService {
	List<Contact> getContactList();
	Contact getContactById(int id);
}

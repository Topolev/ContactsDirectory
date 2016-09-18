package by.topolev.contacts.services;

import java.util.List;
import java.util.Map;

import by.topolev.contacts.entity.Contact;

public interface ContactService {
	List<Contact> getContactList();
	int getCountContacts();
	List<Contact> getLimitContactList(int beginRow, int countRow);
	List<Contact> getLimitContactList(int page, int countRow, String sortField, String sortType);
	List<Contact> getSearchContact(Map<String, String> valueFields);
	Contact getContactById(int id);
	List<Contact> getContactById(Integer... idList);
	void deleteContact(Integer...idList);
	void updateContact(Contact contact);

}

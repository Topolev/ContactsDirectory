package by.topolev.contacts.dao;

import java.util.List;
import java.util.Map;

import by.topolev.contacts.entity.Contact;

public interface ContactDao {
	List<Contact> getContactList();
	List<Contact> getContactListAccordingQuery(String query);
	List<Contact> getLimitContactList(int beginRow, int countRow);
	List<Contact> getLimitContactList(int beginRow, int countRow, String sortField, String sortType);
	List<Contact> getSearchContact(Map<String, String> valueFields);
	List<Contact> getSearchContact(Map<String, String> valueFields, int beginRow, int countRow);
	void updateContact(Contact contact);
	Contact getContactById(int id);
	List<Contact> getContactById(Integer... idList);
	int getCountContacts();
	int getCountSearchContact(Map<String, String> valueFields);
	void deleteContacts(Integer...id);
}

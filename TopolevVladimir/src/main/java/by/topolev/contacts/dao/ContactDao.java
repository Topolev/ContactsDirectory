package by.topolev.contacts.dao;

import java.util.List;
import java.util.Map;

import by.topolev.contacts.entity.Contact;

public interface ContactDao {
	List<Contact> getContactList();
	List<Contact> getLimitContactList(int beginRow, int countRow);
	List<Contact> getLimitContactList(int beginRow, int countRow, String sortField, String sortType);
	List<Contact> getSearchContact(Map<String, String> valueFields);
	void updateContact(Contact contact);
	Contact getContactById(int id);
	public List<Contact> getContactById(Integer... idList);
	int getCountContacts();
	void deleteContacts(Integer...id);
}

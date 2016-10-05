package by.topolev.contacts.services;

import java.util.List;
import java.util.Map;

import by.topolev.contacts.entity.Contact;

public interface ContactService {
	List<Contact> getContactList();
	int getCountContacts();
	int getCountSearchContact(Map<String, String> valueFields);
	List<Contact> getLimitContactList(int beginRow, int countRow);
	List<Contact> getLimitContactList(int page, int countRow, String sortField, String sortType);
	List<Contact> getSearchContact(Map<String, String> valueFields);
	List<Contact> getSearchContact(Map<String, String> valueFields, int page, int countRow);
	Contact getContactById(int id);
	List<Contact> getContactById(Integer... idList);
	void deleteContact(Integer...idList);
	Integer updateContact(Contact contact, boolean lazyLoad);
	List<Contact> getContactListWhoTodayCelebrateBirthday();

}

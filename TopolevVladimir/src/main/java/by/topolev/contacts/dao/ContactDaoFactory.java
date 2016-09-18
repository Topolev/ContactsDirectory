package by.topolev.contacts.dao;

public class ContactDaoFactory {
	public static ContactDao getContactDao(){
		return new ContactDaoJDBC();
	}
}

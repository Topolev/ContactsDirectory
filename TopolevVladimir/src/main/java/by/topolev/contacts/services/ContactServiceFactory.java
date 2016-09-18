package by.topolev.contacts.services;

public class ContactServiceFactory {
	public static ContactService getContactService(){
		return new ContactServiceImpl();
	}
}

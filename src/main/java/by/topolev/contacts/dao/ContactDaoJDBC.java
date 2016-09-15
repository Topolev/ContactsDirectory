package by.topolev.contacts.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static by.topolev.contacts.config.ConfigUtil.*;

import by.topolev.contacts.entity.Address;
import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.orm.tools.EntityManager;

public class ContactDaoJDBC extends AbstractDaoJDBC implements ContactDao {
	private static final Logger LOG = LoggerFactory.getLogger(ContactDaoJDBC.class);

	private EntityManager em = new EntityManager();

	private AddressDao addressDao = new AddressDaoJdbc();

	@Override
	public List<Contact> getContactList() {
		return em.getListEntity("SELECT * FROM contact", Contact.class);
	}

	@Override
	public List<Contact> getLimitContactList(int beginRow, int countRow) {
		return em.getListEntity(String.format("SELECT * FROM contact LIMIT %d, %d", beginRow, countRow), Contact.class);
	}
	
	@Override
	public List<Contact> getLimitContactList(int beginRow, int countRow, String sortField, String sortType) {
		
		String query;
		if ("address".equals(sortField)){
			query = String.format("SELECT contact.* FROM contact, address addr "
					            + "WHERE contact.id=addr.contact_id "
					            + "ORDER BY country %s,city %s,street %s LIMIT %d,%d", sortType,sortType, sortType, beginRow, countRow); 
		} else{
			query = String.format("SELECT * FROM contact ORDER BY %s %s LIMIT %d, %d", sortField, sortType, beginRow, countRow);
		}
		System.out.println(query);
		return em.getListEntity(query, Contact.class);
	}

	@Override
	public Contact getContactById(int id) {
		return em.getEntity(String.format("SELECT * FROM contact WHERE id=%d", id), Contact.class);
	}

	@Override
	public int getCountContacts() {
		return em.getCountAllEntity(Contact.class);
	}
	
	@Override
	public void deleteContacts(Integer... idList) {
		em.deleteEntity(Contact.class, idList);
		
	}

	@Override
	public void updateContact(Contact contact) {
		em.updateEntity(contact);
		
	}
	public static void main(String[] args) {
		ContactDao contactDao = new ContactDaoJDBC();
		for (Contact contact : contactDao.getContactList()) {
			System.out.println(contact);
		}
		System.out.println(contactDao.getCountContacts());
	}





}

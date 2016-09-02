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

public class ContactDaoJDBC extends AbstractDaoJDBC implements ContactDao {
	private static final Logger LOG = LoggerFactory.getLogger(ContactDaoJDBC.class);
	
	private AddressDao addressDao = new AddressDaoJdbc();
	
	private Contact createContactFromResultSetEntity(ResultSet resultSet) throws SQLException{
		Contact contact = new Contact();
		contact.setId(resultSet.getInt("id"));
		contact.setFirstname(resultSet.getString("first_name"));
		contact.setLastname(resultSet.getString("last_name"));
		contact.setWorkplace(resultSet.getString("work_place"));
		contact.setAddress(addressDao.getAddressByIdContact(contact.getId()));
		return contact;
	}
	
	@Override
	public List<Contact> getContactList() {
		List<Contact> contactList = new ArrayList<Contact>();
		Connection connection = null;
		try {
			connection = getConnection();
			PreparedStatement statmentContact = connection.prepareStatement("select id, first_name, last_name, work_place from contact");
			ResultSet resultSet = statmentContact.executeQuery();
			while (resultSet.next()){
				contactList.add(createContactFromResultSetEntity(resultSet));
			}
		} catch (SQLException e) {
			LOG.info("Problem with database connection");
			LOG.debug("Problem with database connection");
		} finally {
			closeConnection(connection);
		}
		return contactList;
	}
	
	public static void main(String[] args){
		ContactDao contactDao = new ContactDaoJDBC();
		for (Contact contact : contactDao.getContactList()){
			System.out.println(contact);
		}
		
		
	}
}

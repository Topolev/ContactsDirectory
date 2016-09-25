package by.topolev.contacts.dao;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.orm.tools.EntityManager;
import by.topolev.contacts.orm.tools.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class ContactDaoJDBC implements ContactDao {
	private static final Logger LOG = LoggerFactory.getLogger(ContactDaoJDBC.class);

	private EntityManager em = EntityManagerFactory.getEntityManager();

	//private AddressDao addressDao = new AddressDaoJdbc();

	@Override
	public List<Contact> getContactList() {
		return em.getListEntity("SELECT * FROM contact", Contact.class);
	}

	@Override
	public List<Contact> getContactListAccordingQuery(String query){
		return em.getListEntity(query, Contact.class);
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
	public List<Contact> getSearchContact(Map<String, String> valueFields) {

		if (valueFields != null && !valueFields.isEmpty()){
			LOG.debug("Map of search fields: {}", valueFields.toString());
			StringBuilder query = new StringBuilder();
			query.append("SELECT contact.* FROM contact LEFT JOIN address ON contact.id=address.contact_id WHERE ");
			for (Map.Entry<String,String> entry : valueFields.entrySet()){
				query.append(entry.getKey()).append("='").append(entry.getValue()).append("' AND ");
			}
			query.delete(query.length()-5, query.length());

			LOG.debug("Search query: {}", query.toString());

			return em.getListEntity(query.toString(), Contact.class);
		} else{
			LOG.debug("Search query: SELECT * FROM contact");
			return em.getListEntity("SELECT * FROM contact", Contact.class);
		}

	}

	@Override
	public Contact getContactById(int id) {
		return em.getEntity(String.format("SELECT * FROM contact WHERE id=%d", id), Contact.class);
	}

	public List<Contact> getContactById(Integer... idList){
		return em.getEntitiesById(Contact.class, idList);
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

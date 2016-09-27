package by.topolev.contacts.dao;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.orm.tools.EntityManager;
import by.topolev.contacts.orm.tools.EntityManagerFactory;
import org.apache.commons.collections4.CollectionUtils;
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
		return em.getListEntity("SELECT * FROM contact", Contact.class, true);
	}

	@Override
	public List<Contact> getContactListAccordingQuery(String query){
		return em.getListEntity(query, Contact.class, true);
	}

	@Override
	public List<Contact> getLimitContactList(int beginRow, int countRow) {
		return em.getListEntity(String.format("SELECT * FROM contact LIMIT %d, %d", beginRow, countRow), Contact.class, true);
	}
	
	@Override
	public List<Contact> getLimitContactList(int beginRow, int countRow, String sortField, String sortType) {
		
		String query;
		if ("address".equals(sortField)){
			query = String.format("SELECT contact.* FROM contact LEFT JOIN address ON contact.id=address.contact_id "
					            + "ORDER BY country %s,city %s,street %s LIMIT %d,%d", sortType,sortType, sortType, beginRow, countRow); 
		} else{
			query = String.format("SELECT * FROM contact ORDER BY %s %s LIMIT %d, %d", sortField, sortType, beginRow, countRow);
		}
		System.out.println(query);
		return em.getListEntity(query, Contact.class,true);
	}

	@Override
	public int getCountSearchContact(Map<String, String> valueFields){
		if (CollectionUtils.isNotEmpty(valueFields.entrySet())) {
			StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(*) FROM contact LEFT JOIN address ON contact.id=address.contact_id WHERE ");
			for (Map.Entry<String,String> entry : valueFields.entrySet()){
				query.append(entry.getKey()).append("='").append(entry.getValue()).append("' AND ");
			}
			query.delete(query.length()-5, query.length());
			return em.getCountRows(query.toString(), Contact.class);
		} else {
			return em.getCountRows("SELECT COUNT(*) FROM contact", Contact.class);
		}
	}

	@Override
	public List<Contact> getSearchContact(Map<String, String> valueFields, int beginRow, int countRow){
		if (CollectionUtils.isNotEmpty(valueFields.entrySet())) {
			StringBuilder query = new StringBuilder();
			query.append(createQueryForSearchContact(valueFields))
					.append(" LIMIT ")
					.append(beginRow)
					.append(",")
					.append(countRow);
			LOG.debug("Search query: {}", query);
			return em.getListEntity(query.toString(), Contact.class,true);
		} else{
			LOG.debug(String.format("Search query: SELECT * FROM contact LIMIT %d, %d", beginRow, countRow));
			return em.getListEntity(String.format("SELECT * FROM contact LIMIT %d, %d", beginRow, countRow), Contact.class,true);
		}
	}




	@Override
	public List<Contact> getSearchContact(Map<String, String> valueFields) {
		if (CollectionUtils.isNotEmpty(valueFields.entrySet())){
			String query = createQueryForSearchContact(valueFields);
			LOG.debug("Search query: {}", query);
			return em.getListEntity(query, Contact.class,true);
		} else{
			LOG.debug("Search query: SELECT * FROM contact");
			return em.getListEntity("SELECT * FROM contact", Contact.class,true);
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

	private String createQueryForSearchContact(Map<String, String> valueFields){
		StringBuilder query = new StringBuilder();
		query.append("SELECT contact.* FROM contact LEFT JOIN address ON contact.id=address.contact_id WHERE ");
		for (Map.Entry<String,String> entry : valueFields.entrySet()){
			query.append(entry.getKey()).append("='").append(entry.getValue()).append("' AND ");
		}
		query.delete(query.length()-5, query.length());
		return query.toString();
	}
	public static void main(String[] args) {
		ContactDao contactDao = new ContactDaoJDBC();
		for (Contact contact : contactDao.getContactList()) {
			System.out.println(contact);
		}
		System.out.println(contactDao.getCountContacts());
	}





}

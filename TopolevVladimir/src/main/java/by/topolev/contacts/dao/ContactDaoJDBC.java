package by.topolev.contacts.dao;

import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.orm.tools.EntityManager;
import by.topolev.contacts.orm.tools.EntityManagerFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.iterators.ObjectArrayIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ContactDaoJDBC implements ContactDao {
    private static final Logger LOG = LoggerFactory.getLogger(ContactDaoJDBC.class);

    private EntityManager em = EntityManagerFactory.getEntityManager();

    //private AddressDao addressDao = new AddressDaoJdbc();

    @Override
    public List<Contact> getContactList() {
        return em.getListEntity("SELECT * FROM contact",null, Contact.class, true);
    }

    @Override
    public List<Contact> getContactListAccordingQuery(String templateQuery, Map<String, Object> map) {
        return em.getListEntity(templateQuery, map, Contact.class, true);
    }

    @Override
    public List<Contact> getLimitContactList(int beginRow, int countRow) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("begin", beginRow);
        map.put("count", countRow);

        return em.getListEntity("SELECT * FROM contact LIMIT ?, ?", map, Contact.class, true);
    }

    @Override
    public List<Contact> getLimitContactList(int beginRow, int countRow, String sortField, String sortType) {
        String templateQuery;
        Map<String, Object> map = new LinkedHashMap<>();
        if ("address".equals(sortField)) {
            templateQuery = String.format("SELECT contact.* FROM contact LEFT JOIN address ON contact.id=address.contact_id "
                    + "ORDER BY country %s,city %s,street %s LIMIT ?,?", sortType, sortType, sortType);
        } else {
            templateQuery = String.format("SELECT * FROM contact ORDER BY %s %s LIMIT ?, ?", sortField, sortType);
        }
        map.put("begin", beginRow);
        map.put("count", countRow);
        return em.getListEntity(templateQuery, map, Contact.class, true);
    }

    @Override
    public int getCountSearchContact(Map<String, String> valueFields) {
        if (CollectionUtils.isNotEmpty(valueFields.entrySet())) {
            StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(*) FROM contact LEFT JOIN address ON contact.id=address.contact_id WHERE ")
                    .append(createQuerySectionForSearchContact(valueFields));
            Map<String, Object> map = createMapForSearchContact(valueFields);

            return em.getCountRows(query.toString(), map, Contact.class);
        } else {
            return em.getCountRows("SELECT COUNT(*) FROM contact", null, Contact.class);
        }
    }

    @Override
    public List<Contact> getSearchContact(Map<String, String> valueFields, int beginRow, int countRow) {
        if (CollectionUtils.isNotEmpty(valueFields.entrySet())) {
            StringBuilder templateQuery = new StringBuilder();

            templateQuery.append("SELECT contact.* FROM contact LEFT JOIN address ON contact.id=address.contact_id WHERE ")
                    .append(createQuerySectionForSearchContact(valueFields))
                    .append(" LIMIT ?, ?");
            Map<String, Object> map = createMapForSearchContact(valueFields);
            map.put("begin", beginRow);
            map.put("count", countRow);

            return em.getListEntity(templateQuery.toString(), map,  Contact.class, true);
        } else {
            //LOG.debug(String.format("Search query: SELECT * FROM contact LIMIT %d, %d", beginRow, countRow));
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("begin", beginRow);
            map.put("count", countRow);
            return em.getListEntity("SELECT * FROM contact LIMIT ?, ?", map, Contact.class, true);
        }
    }


    @Override
    public List<Contact> getSearchContact(Map<String, String> valueFields) {
        if (CollectionUtils.isNotEmpty(valueFields.entrySet())) {
            StringBuilder templateQuery = new StringBuilder();

            templateQuery.append("SELECT contact.* FROM contact LEFT JOIN address ON contact.id=address.contact_id WHERE ")
                    .append(createQuerySectionForSearchContact(valueFields));
            Map<String, Object> map = createMapForSearchContact(valueFields);

            return em.getListEntity(templateQuery.toString(), map, Contact.class, true);
        } else {
            return em.getListEntity("SELECT * FROM contact", null, Contact.class, true);
        }
    }

    @Override
    public Contact getContactById(int id) {
        String templateQuery = "SELECT * FROM contact WHERE id = ?";

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);

        return em.getEntity(templateQuery, map, Contact.class);
    }

    public List<Contact> getContactById(Integer... idList) {
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

    private String createQuerySectionForSearchContact(Map<String, String> valueFields) {
        StringBuilder query = new StringBuilder();

        Iterator<Map.Entry<String, String>> iterator = valueFields.entrySet().iterator();

        while(iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            if ("birthdaymore".equals(entry.getKey())) {
                query.append("birthday >= ? ");
            } else if ("birthdayless".equals(entry.getKey())) {
                query.append("birthday <= ? ");
            } else {
                query.append(entry.getKey()).append(" LIKE ? ");
            }
            if (iterator.hasNext()){
                query.append(" AND ");
            }
        }

        return query.toString();
    }

    private Map<String, Object> createMapForSearchContact(Map<String, String> valueFields){
        Map<String, Object> map = new LinkedHashMap<>();
        for(Map.Entry<String, String> entry : valueFields.entrySet()){
            if ("birthdaymore".equals(entry.getKey()) || "birthdayless".equals(entry.getKey())) {
                map.put(entry.getKey(), getDate(entry.getValue()));
            } else{
                map.put(entry.getKey(), "%" + entry.getValue() + "%");
            }
        }
        return map;
    }

    private Date getDate(String value){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(value);
        } catch (ParseException e) {
            LOG.debug("Can't parse the date", e);
            return null;
        }
    }

    public static void main(String[] args) {
        ContactDao contactDao = new ContactDaoJDBC();
        for (Contact contact : contactDao.getContactList()) {
            System.out.println(contact);
        }
        System.out.println(contactDao.getCountContacts());
    }


}

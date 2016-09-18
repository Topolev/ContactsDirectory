package by.topolev.contacts.dao;

/**
 * Created by Vladimir on 15.09.2016.
 */
public class PhoneDaoFactory {
    public static PhoneDao getPhoneDao(){
        return new PhoneDaoJDBC();
    }
}

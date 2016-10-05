package by.topolev.contacts;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Vladimir on 20.09.2016.
 */
public class Test {
    public static void main(String[] str){
        Contact contact1 = new Contact("Topolev", "Vladimir");
        Contact contact2 = new Contact("Kalenchuk", "Eugeniy");
        List<Contact> teams = Arrays.asList(contact1, contact2);

        STGroup group = new STGroupFile("template/template.stg");
        ST birthday = group.getInstanceOf("birthday");
        birthday.add("contacts", teams);
        birthday.add("date", new Date());

        System.out.print(birthday.render());

        ST s = new ST( "<teams :{team | <team.i> + <team.name> }>");
        s.add("teams", teams);
        System.out.println(s.render());


    }

}
class Contact{
    private String firstname;
    private String lastname;

    public Contact(String firstname, String lastname){
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}

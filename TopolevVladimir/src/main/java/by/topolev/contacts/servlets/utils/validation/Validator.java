package by.topolev.contacts.servlets.utils.validation;

/**
 * Created by Vladimir on 02.10.2016.
 */
public interface Validator {
    boolean test(String value);
    String getMessageError();
}

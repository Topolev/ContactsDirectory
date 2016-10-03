package by.topolev.contacts.servlets.utils.validation.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vladimir on 02.10.2016.
 */
public class Test {
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

    public static void main(String[] arg) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        System.out.print(pattern.matcher("i.topolev.vladi@gmail.com").matches());

    }
}

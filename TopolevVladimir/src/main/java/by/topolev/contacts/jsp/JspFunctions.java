package by.topolev.contacts.jsp;

import java.util.Map;

/**
 * Created by Vladimir on 02.10.2016.
 */
public class JspFunctions {

    public static boolean hasError(Map<String, String> errors, String key) {
        return errors != null && errors.containsKey(key);
    }

    public static String getMessage(Map<String, String> errors, String key){
        if (errors != null && errors.containsKey(key)) {
            return errors.get(key);
        } else return null;
    }
}

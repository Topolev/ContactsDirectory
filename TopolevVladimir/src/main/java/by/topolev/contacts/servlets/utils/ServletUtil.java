package by.topolev.contacts.servlets.utils;

import by.topolev.contacts.servlets.commands.ContactListCommand;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ServletUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ServletUtil.class);

    private static ObjectMapper map = new ObjectMapper();


    public static <T> T getRequestParametr(HttpServletRequest req, String nameParametr, Class<T> clazz, T defaultValue){
        try {
            if (clazz == String.class) {
                if (!req.getParameter(nameParametr).equals("")) return (T) req.getParameter(nameParametr);
                else return null;
            }
            return map.readValue(req.getParameter(nameParametr), clazz);
        } catch (IOException | NullPointerException e) {
            LOG.debug("Set default value for request parametr {}",nameParametr);
            return defaultValue;
        }
    }
}

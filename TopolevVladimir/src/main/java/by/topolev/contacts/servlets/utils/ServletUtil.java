package by.topolev.contacts.servlets.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ServletUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ServletUtil.class);

    private static ObjectMapper map = new ObjectMapper();

    public static <T> T getRequestParameter(HttpServletRequest req, String parameter, Class<T> clazz, T defaultValue) {
        try {
            String value = req.getParameter(parameter);
            if (isEmpty(value)) {
                LOG.debug("Set default value for request parameter {}", parameter);
                return defaultValue;
            }
            return (clazz == String.class) ? (T) value : map.readValue(value, clazz);
        } catch (IOException e) {
            LOG.debug("Set default value for request parameter {}", parameter);
            return defaultValue;
        }
    }
}

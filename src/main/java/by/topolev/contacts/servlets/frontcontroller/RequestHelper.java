package by.topolev.contacts.servlets.frontcontroller;

import by.topolev.contacts.servlets.commands.ContactListCommand;
import by.topolev.contacts.servlets.commands.JSPCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Vladimir on 17.09.2016.
 */
public class RequestHelper {

    private static final Logger LOG =LoggerFactory.getLogger(RequestHelper.class);

    private static Map<String, Command> mapResources = new HashMap<String, Command>();
    static{
        mapResources.put("/contactlist", new ContactListCommand());
    }

    private HttpServletRequest request;

    public RequestHelper(HttpServletRequest request){
        this.request = request;
    }

    public Command getCommand(){
        String currentUrl = this.request.getRequestURI();
        LOG.debug(currentUrl);
/*
        Pattern regExIdPattern = Pattern.compile(".jsp");
		Matcher matcher = regExIdPattern.matcher(currentUrl);*/


        return mapResources.get(currentUrl);





    }
}

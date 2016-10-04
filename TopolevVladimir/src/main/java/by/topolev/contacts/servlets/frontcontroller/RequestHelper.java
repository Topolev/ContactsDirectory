package by.topolev.contacts.servlets.frontcontroller;

import by.topolev.contacts.servlets.commands.ChangeLanguageCommand;
import by.topolev.contacts.servlets.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Vladimir on 17.09.2016.
 */
public class RequestHelper {
    private static final Logger LOG =LoggerFactory.getLogger(RequestHelper.class);
    public static final String GET = "get";
    public static final String POST = "post";

    private static Map<MetaRequest, Command> mapResources = new HashMap<MetaRequest, Command>();

    public static void init(ServletContext servletContext){
        mapResources.put(new MetaRequest("/", GET), new IndexCommand());
        mapResources.put(new MetaRequest("/contactlist", GET), new ContactListCommand());
        mapResources.put(new MetaRequest("/contactdelete", GET), new ContactDeleteCommand());
        mapResources.put(new MetaRequest("/contact", GET), new ContactShowCommand());
        mapResources.put(new MetaRequest("/contactnew", GET), new ContactGetFormCommand());
        mapResources.put(new MetaRequest("/contactnew", POST), new ContactGetFormCommand());
        mapResources.put(new MetaRequest("/contactsave", POST), new ContactCreateUpdateCommand(servletContext));
        mapResources.put(new MetaRequest("/showimage", GET), new ShowImageCommand());
        mapResources.put(new MetaRequest("/showsearchform", GET), new SearchFormGetCommand());
        mapResources.put(new MetaRequest("/searchform", GET), new SearchFormPostCommand());
        mapResources.put(new MetaRequest("/searchform", POST), new SearchFormPostCommand());
        mapResources.put(new MetaRequest("/uploadfile", GET), new UploadFileCommand());
        mapResources.put(new MetaRequest("/sendmessage", GET), new ShowSendMessageFormCommand(servletContext));
        mapResources.put(new MetaRequest("/sendmessage", POST), new SendMessagesCommand());
        mapResources.put(new MetaRequest("/changelan", GET), new ChangeLanguageCommand());
    }

    private HttpServletRequest request;

    public RequestHelper(HttpServletRequest request){
        this.request = request;
    }

    public Command getCommand(){
        String currentURI = this.request.getRequestURI().toLowerCase();
        String currentMethod = this.request.getMethod().toLowerCase();

        //LOG.debug("Current URI: {} ; Current method: {}",currentURI, currentMethod);
        MetaRequest metaRequest = new MetaRequest(currentURI,currentMethod);

        return mapResources.get(metaRequest);
    }
}

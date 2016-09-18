package by.topolev.contacts.servlets.frontcontroller;

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

    private static Map<MetaRequest, Command> mapResources = new HashMap<MetaRequest, Command>();

    public static void init(ServletContext servletContext){
        mapResources.put(new MetaRequest("/","get"), new IndexCommand());
        mapResources.put(new MetaRequest("/contactlist","get"), new ContactListCommand());
        mapResources.put(new MetaRequest("/contactdelete","get"), new ContactDeleteCommand());
        mapResources.put(new MetaRequest("/contact","get"), new ContactShowCommand());
        mapResources.put(new MetaRequest("/contactnew","get"), new ContactGetFormCommand());
        mapResources.put(new MetaRequest("/contactnew","post"), new ContactCreateUpdateCommand(servletContext));
        mapResources.put(new MetaRequest("/showimage","get"), new ShowImageCommand());
        mapResources.put(new MetaRequest("/searchform","get"), new SearchFormGetCommand());
        mapResources.put(new MetaRequest("/searchform","post"), new SearchFormPostCommand());
        mapResources.put(new MetaRequest("/uploadfile","get"), new UploadFileCommand());
        mapResources.put(new MetaRequest("/sendmessage","get"), new SendMessageCommand());
    }

    private HttpServletRequest request;

    public RequestHelper(HttpServletRequest request){
        this.request = request;
    }

    public Command getCommand(){
        String currentURI = this.request.getRequestURI().toLowerCase();
        String currentMethod = this.request.getMethod().toLowerCase();

        LOG.debug("Current URI: {} ; Current methos: {}",currentURI, currentMethod);
        MetaRequest metaRequest = new MetaRequest(currentURI,currentMethod);

        return mapResources.get(metaRequest);
    }
}

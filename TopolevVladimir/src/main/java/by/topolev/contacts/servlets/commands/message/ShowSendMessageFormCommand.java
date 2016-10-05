package by.topolev.contacts.servlets.commands.message;

import by.topolev.contacts.config.ConfigUtil;
import by.topolev.contacts.entity.Contact;
import by.topolev.contacts.services.ContactService;
import by.topolev.contacts.services.ContactServiceFactory;
import by.topolev.contacts.servlets.frontcontroller.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static by.topolev.contacts.servlets.utils.ServletUtil.convertStringInIntArray;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class ShowSendMessageFormCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(ShowSendMessageFormCommand.class);

    private static File pathTemplates;

    private ContactService contactService = ContactServiceFactory.getContactService();

    public ShowSendMessageFormCommand(ServletContext servletContext) {
        pathTemplates = new File(servletContext.getRealPath(ConfigUtil.getPathEmailTemplate()));
    }


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<Contact> contacts = contactService.getContactById(convertStringInIntArray(req.getParameter("sendto")));

        req.setAttribute("contacts", contacts);
        req.setAttribute("sendto", req.getParameter("sendto"));
        req.setAttribute("templates", getMapTemplates());

        return "/send_message.jsp";
    }


    private Map<String,String> getMapTemplates(){
        Map<String,String> listTemplates = new TreeMap<>();

        if (pathTemplates.exists() && pathTemplates.isDirectory()){
            File[] files = pathTemplates.listFiles();
            for (File file : files){
                String txtTemplate = getTextTemplate(file);
                if (txtTemplate != null){
                    listTemplates.put(file.getName(), txtTemplate);
                }
            }
        }

        return listTemplates;
    }

    private String getTextTemplate(File file){
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder txtTemplate = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                txtTemplate.append(line).append("&#13;&#10;");
            }
            return txtTemplate.toString();
        } catch (IOException e){
            LOG.debug("Problem with upload template from '{}' file", file.getName(), e);
            return null;
        }
    }




}

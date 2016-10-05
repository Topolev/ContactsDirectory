package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.config.ConfigUtil;
import by.topolev.contacts.servlets.formdata.Error;
import by.topolev.contacts.servlets.frontcontroller.Command;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static by.topolev.contacts.servlets.utils.PageNames.PAGE_ERROR;


/**
 * Created by Vladimir on 18.09.2016.
 */
public class UploadFileCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(UploadFileCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String rootDirectory = ConfigUtil.getPathUploadProfileFiles();
        OutputStream out = resp.getOutputStream();

        if (StringUtils.isEmpty(req.getParameter("file"))){
            LOG.debug("Invalid file name.");
            out.write("Invalid file name.".getBytes());
            return null;
        }

        File file = new File(rootDirectory + req.getParameter("file"));

        if (!file.exists()){
            LOG.info("File with path '{}' isn't excited.", file.getAbsolutePath());
            out.write("File isn't exited.".getBytes());
        } else{
            InputStream in = new FileInputStream(file);
            byte[] image = IOUtils.toByteArray(in);
            out.write(image);
        }

        return null;
    }

}

package by.topolev.contacts.servlets;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static by.topolev.contacts.config.ConfigUtil.*;

/**
 * Created by Vladimir on 14.09.2016.
 */
public class ShowImageServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ShowImageServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String rootDirectory = PATH_UPLOAD_PROFILE_IMAGE;
        File file = new File(rootDirectory + req.getParameter("file"));
        if (!file.exists()){
            LOG.info(String.format("File with path '%s' isn't excited.", file.getAbsolutePath()));
        } else{
            InputStream in = new FileInputStream(file);
            byte[] image = IOUtils.toByteArray(in);
            OutputStream out = resp.getOutputStream();
            out.write(image);
        }
    }
}

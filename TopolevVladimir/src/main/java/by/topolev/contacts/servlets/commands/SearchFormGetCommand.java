package by.topolev.contacts.servlets.commands;

import by.topolev.contacts.config.ConfigUtil;
import by.topolev.contacts.servlets.frontcontroller.Command;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class SearchFormGetCommand implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(SearchFormGetCommand.class);




    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return "/searchform.jsp";
    }
}

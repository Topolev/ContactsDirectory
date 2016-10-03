package by.topolev.contacts.servlets.frontcontroller;

import by.topolev.contacts.config.ConfigUtil;
import by.topolev.contacts.orm.tools.EntityManagerFactory;
import by.topolev.contacts.servlets.utils.ServletUtil;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Vladimir on 18.09.2016.
 */
public class FrontControllerFilter implements Filter{

    private static final Logger LOG = LoggerFactory.getLogger(FrontControllerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();

        ConfigUtil.init(servletContext);

        EntityManagerFactory.getEntityManager();
        RequestHelper.init(servletContext);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        dispatch((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    protected void dispatch(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {

        if (req.getRequestURI().endsWith("\\.jsp")){
            chain.doFilter(req, resp);
        } else{
            req.setCharacterEncoding("UTF-8");
            RequestHelper helper = new RequestHelper(req);
            Command command = helper.getCommand();

            req.setAttribute("resourceBundle", getResourceBundle(req));


            if (command != null){
                String page = command.execute(req, resp);
                if (page != null) {
                    RequestDispatcher dispatcher = req.getRequestDispatcher(page);
                    dispatcher.forward(req, resp);
                }
            } else{
                chain.doFilter(req, resp);
            }
        }
    }

    private ResourceBundle getResourceBundle(HttpServletRequest req){
        String lan = null;
        lan = ServletUtil.getRequestParameter(req,"lan",String.class,null);
        if (lan == null){
            lan = ServletUtil.getCookieValue(req);
        }

        Locale locale = null;
        if (lan == null){
            locale = req.getLocale();
        } else{
            if ("ru".equals(lan)){
                locale = new Locale("ru", "RU");
            }
            if ("en".equals(lan)){
                locale = new Locale("en", "EN");
            }
        }
        ResourceBundle.getBundle("i18n.test",locale);
        return ResourceBundle.getBundle("i18n.test",locale);
    }


    private void setLocal(HttpServletRequest req){
        Locale locale = req.getLocale();
    }

    @Override
    public void destroy() {

    }
}

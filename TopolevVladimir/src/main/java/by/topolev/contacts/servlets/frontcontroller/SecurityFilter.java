package by.topolev.contacts.servlets.frontcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(FrontControllerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean isSecurityViolation = doSecurityFilter((HttpServletRequest) request, (HttpServletResponse) response);
        if (!isSecurityViolation) {
            chain.doFilter(request, response);
        }
    }

    private boolean doSecurityFilter(HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith(".properties")) {
            try {
                response.sendRedirect("/contactlist");
                return true;
            } catch (IOException e) {
                LOG.error("Unexpected exception happens", e);
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}

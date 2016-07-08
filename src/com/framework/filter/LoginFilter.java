package com.framework.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException{
        HttpServletRequest servletRequest = (HttpServletRequest)request;
        HttpServletResponse servletResponse = (HttpServletResponse)response;
        HttpSession session = servletRequest.getSession();

        String path = servletRequest.getRequestURI();
        if(path.indexOf("/css") > -1
                || path.indexOf("/fonts") > -1
                || path.indexOf("/img") > -1
                || path.indexOf("/bootstrap") > -1
                || path.indexOf("/js") > -1
                || path.indexOf("/skin") > -1
                || path.indexOf("/welcome") > -1
                || path.indexOf("/login.jsp") > -1) {
            chain.doFilter(servletRequest, servletResponse);
        }
        else  if (session.getAttribute("uid") != null) {
            chain.doFilter(servletRequest, servletResponse);
        }
        else {
            servletResponse.sendRedirect("./login.jsp");
        }
    }

    public void destroy() {

    }
}
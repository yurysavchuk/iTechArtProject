package com.yurysavchuk.controller;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by HP on 09.12.2015.
 */
public class EncodinFilter implements Filter {

    private String encoding = "UTF-8";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        String contentType = req.getContentType();
        if (contentType != null && (contentType.startsWith("multipart/form-data")||
                contentType.startsWith("application/")))
            req.setCharacterEncoding(encoding);

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

        String encodingParam = config.getInitParameter("encoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }

    }

}

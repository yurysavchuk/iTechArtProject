package com.yurysavchuk.utiles;

import org.apache.log4j.Logger;


public class MailUtils {

    final static Logger log = Logger.getLogger(MailUtils.class);

    public static String getTemplateName(String str) {

        log.info("Method getTemplateName start: parameter - " + str);
        try {
            int ind1 = str.indexOf('=');
            int ind2 = str.indexOf('&');
            return str.substring(ind1 + 1, ind2);
        } catch (Exception e) {
            log.error(e);
            return "";
        }
    }
}

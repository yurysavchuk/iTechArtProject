package com.yurysavchuk.utiles;

import com.yurysavchuk.domain.PhoneNumber;
import org.apache.log4j.Logger;


public class PhoneNumberParser {

    final static Logger log = Logger.getLogger(PhoneNumberParser.class);

    public static PhoneNumber getPhoneNumber(String str) {

        log.info("Method getPhoneNumber start: parameter - " + str);

        PhoneNumber number = new PhoneNumber();
        try {
            int index = str.indexOf(',');
            String buf = str.substring(0, index);
            str = str.substring(index + 1);
            number.setId(Integer.valueOf(buf));

            index = str.indexOf(',');
            buf = str.substring(0, index);
            str = str.substring(index + 1);
            number.setCountryCode(Integer.valueOf(buf));

            index = str.indexOf(',');
            buf = str.substring(0, index);
            str = str.substring(index + 1);
            number.setOperCode(Integer.valueOf(buf));

            index = str.indexOf(',');
            buf = str.substring(0, index);
            str = str.substring(index + 1);
            number.setNumber(Integer.valueOf(buf));

            index = str.indexOf(',');
            buf = str.substring(0, index);
            str = str.substring(index + 1);
            number.setType(buf);

            number.setComment(str);
        } catch (Exception e) {
            log.error(e);
        }
        return number;

    }
}

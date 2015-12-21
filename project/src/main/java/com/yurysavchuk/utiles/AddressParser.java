package com.yurysavchuk.utiles;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class AddressParser {

    final static Logger log = Logger.getLogger(AddressParser.class);

    public static String getStreet(String address) {
        String street = null;

        log.info("Method getStreet start: address - " + address);


        if (StringUtils.isNotBlank(address)) {
            try {
                address = address.substring(3);
                int ind = address.indexOf(",");
                street = address.substring(0, ind);
            } catch (Exception e) {
                log.error(e);
            }
        } else {
            street = "";
        }
        return street;
    }

    public static String getHouse(String address) {

        log.info("Method getHouse start: address - " + address);

        String house = null;

        if (StringUtils.isNotBlank(address)) {
            try {
                int ind = address.indexOf(",");
                address = address.substring(ind + 1);
                ind = address.indexOf(",");
                house = address.substring(0, ind);
            } catch (Exception e) {
                log.error(e);
            }
        } else {
            house = "";
        }
        return house;
    }

    public static Integer getFlat(String address) {

        log.info("Method getFlat start: address - " + address);

        Integer flat = 0;
        if (StringUtils.isNotBlank(address)) {
            try {
                int ind = address.indexOf(",");
                address = address.substring(ind + 1);
                ind = address.indexOf(",");
                flat = Integer.valueOf(address.substring(ind + 1));
            } catch (Exception e) {
                log.error(e);
            }
        }
        return flat;
    }
}

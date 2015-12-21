package com.yurysavchuk.utiles;

import com.yurysavchuk.domain.File;
import org.apache.log4j.Logger;


public class FileParser {

    final static Logger log = Logger.getLogger(FileParser.class);

    public static File getFile(String str) {

        log.info("Method getFile start: parameter - " + str);

        File file = new File();

        try {
            int index = str.indexOf(',');
            String buf = str.substring(0, index);
            str = str.substring(index + 1);
            file.setId(Integer.valueOf(buf));
            index = str.indexOf(',');
            buf = str.substring(0, index);
            str = str.substring(index + 1);
            file.setPath(buf);
            index = str.indexOf(',');
            buf = str.substring(0, index);
            str = str.substring(index + 1);
            file.setFilename(buf);
            index = str.indexOf(',');
            buf = str.substring(0, index);
            str = str.substring(index + 1);
            file.setDateLoad(buf);
            file.setComment(str);
        } catch (Exception e) {
            log.error(e);
        }

        return file;
    }
}

package com.zeze.springmvcgetallurl.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileToMap {


    private static Log log = LogFactory.getLog(FileToMap.class);
    
    static String FILE_PAHT = "/Users/chenze/urlMapping.conf";


    private HashMap<String, String> fileMapping = new HashMap<>();

    /**
     * 文件转为map
     *
     * @return maping
     */
    private HashMap<String, String> fileToMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        File file = new File(FILE_PAHT);
        BufferedReader reader = null;
        try {
            log.info("start read :" + FILE_PAHT);
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                log.info("line " + line + ": " + tempString);

                if (!tempString.startsWith("#")) {
                    String[] strArray = tempString.split("=");
                    map.put(strArray[0], strArray[1]);
                }
                line++;
            }
            reader.close();
        } catch (Exception e) {
            log.warn(String.format("read %s failed", FILE_PAHT));
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return map;
    }

    public HashMap<String, String> getFileMapping() {

        if (fileMapping.isEmpty()) {
            fileMapping = fileToMap();
        }
        return fileMapping;
    }
}

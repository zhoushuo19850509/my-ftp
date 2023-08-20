package com.nbcb.athena.ftpdemo.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcb.athena.ftpdemo.model.FileInfo;

import java.io.File;

public class JacksonTest {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        /**
         * FileInfo object -> json
         */
        File file = new File("/Users/zhoushuo/Documents/tmp/aa.txt");
        FileInfo fileInfo = new FileInfo(file);
        String json = "";
        try {
            json = objectMapper.writeValueAsString(fileInfo);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        /**
         * json -> object
         */
        try {
            FileInfo fileInfo1 = objectMapper.readValue(json, FileInfo.class);
            System.out.println(fileInfo1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}

package com.nbcb.athena.ftpdemo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcb.athena.ftpdemo.model.FileInfo;

import java.io.File;

public class ByteUtil {

    /**
     * 将int数值转换为占四个字节的byte数组
     * @param value
     * @return
     */
    public static byte[] int2byte(int value){
        byte[] src = new byte[4];
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }

    /**
     * byte数组中取int数值
     * @param src
     * @return
     */
    public static int byte2int(byte[] src){
        int offset = 0;
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset+1] & 0xFF)<<8)
                | ((src[offset+2] & 0xFF)<<16)
                | ((src[offset+3] & 0xFF)<<24));
        return value;
    }


    public static byte[] string2byte(String str){
        return str.getBytes();
    }

    public static String byte2String(byte[] bytes){
        return new String(bytes);
    }


    public static void main(String[] args) {
        byte[] bytes = ByteUtil.int2byte(33);
        System.out.println(bytes);

        int value = ByteUtil.byte2int(bytes);
        System.out.println(value);



        byte[] bytes1 = ByteUtil.string2byte("hello");
        System.out.println(bytes1);
        String result = ByteUtil.byte2String(bytes1);
        System.out.println(result);


        String filePath = "/Users/zhoushuo/Documents/tmp/aa.txt";
        ObjectMapper objectMapper = new ObjectMapper();
        FileInfo fileInfo = new FileInfo(new File(filePath));
        String json = "";
        try {
            json = objectMapper.writeValueAsString(fileInfo);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        byte[] bytes2 = ByteUtil.string2byte(json);
        System.out.println(bytes2);
        String json2 = ByteUtil.byte2String(bytes2);
        System.out.println(json2);
    }



}

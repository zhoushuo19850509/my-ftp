package com.nbcb.athena.ftpdemo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcb.athena.ftpdemo.model.FileInfo;
import com.nbcb.athena.ftpdemo.util.ByteUtil;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class FTPClient {

    /**
     * 发送一个目录
     *
     * @param host
     * @param port
     * @param dirPath /Users/zhoushuo/Downloads/genlib9
     */
    public static void sendDir(String host, int port, String dirPath){
        File dir = new File(dirPath);
        if(!dir.isDirectory()){
            System.out.println("illegal directory ...");
            return;
        }

        File[] files = dir.listFiles();
        for(File file : files){
            sendFile(host, port, file.getAbsolutePath());
            System.out.println("finish send file: " + file.getName());
        }
    }


    public static void sendFile(String host, int port, String filePath){
        /**
         * FileInfo object -> json
         * json代表这个文件的信息，
         * 包括filename/filesize/md5之类的
         */
        ObjectMapper objectMapper = new ObjectMapper();
        FileInfo fileInfo = new FileInfo(new File(filePath));
        String json = "";
        try {
            json = objectMapper.writeValueAsString(fileInfo);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        /**
         * json长度 后续优先传输
         */
        int length = json.length();
        System.out.println("json length: " + length);

        Socket socket = null;
        OutputStream os = null;

        byte[] buffer = new byte[2048];
        InputStream in = null;
        int n;
        try {
            /**
             * 创建连接
             */
            socket = new Socket(host, port);
            os = socket.getOutputStream();

            /**
             * (1)传输byte of (json) length
             */
            os.write(ByteUtil.int2byte(length));

            /**
             * (2)传输byte of json
             */
            os.write(ByteUtil.string2byte(json));

            /**
             * (3)传输byte of file
             */
            in = new FileInputStream(filePath);
            while( (n = in.read(buffer , 0, 2048)) != -1){
                os.write(buffer, 0, n);
            }
            os.flush();




        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != os){
                    os.close();
                }
                if(null != socket){
                    socket.close();
                }
                if(null != in){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        System.out.println("finish sending the file ...");
    }

    public static void main(String[] args) {
        System.out.println("start ftp client ...");

        /**
         * 输入要访问的服务端的host地址
         */
        System.out.println("please input the server host(default localhost): ");
        Scanner in = new Scanner(System.in);
        String host = in.nextLine();
        if(null == host || host.equals("")){
            host = "localhost";
        }
        System.out.println("server host : " + host);

        /**
         * 输入要访问的服务端的host地址
         */
        System.out.println("please input the server port(default 9999)");
        String port = in.nextLine();
        if(null == port || port.equals("")){
            port = "9999";
        }
        System.out.println("server port: " + port);

        /**
         * 输入要传输的文件路径或者目录路径
         */
        System.out.println("please input the transfered filepath or dir:");
        String path = in.nextLine();

        File file = new File(path);

        if(file.isDirectory()){
            System.out.println("transfered filepath : " + path);
            sendDir(host, Integer.parseInt(port), path);
        }else if(file.isFile()){
            System.out.println("transfered dir : " + path);
            sendFile(host, Integer.parseInt(port), path);
        }
    }
}

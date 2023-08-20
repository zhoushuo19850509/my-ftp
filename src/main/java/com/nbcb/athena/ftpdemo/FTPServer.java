package com.nbcb.athena.ftpdemo;

import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcb.athena.ftpdemo.model.FileInfo;
import com.nbcb.athena.ftpdemo.util.ByteUtil;
import com.nbcb.athena.ftpdemo.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Scanner;

public class FTPServer {

    public static void main(String[] args) {

        /**
         * 读取服务端监听的端口
         */
        System.out.println("please input the server port(default 9999)");
        Scanner in = new Scanner(System.in);
        String port = in.nextLine();
        if(null == port || port.equals("")){
            port = "9999";
        }
        System.out.println("server will listen on port: " + port);

        /**
         * 读取ftp server的root dir
         */
        System.out.println("please enter the server root dir");
        String rootDir = in.nextLine();
        System.out.println("server root dir: " + rootDir);

        System.out.println("start ftp server ...");

        /**
         * 服务端监听的端口
         */
        ServerSocket serverSocket = null;
        Socket connection = null;
        InputStream is = null;

        /**
         * 服务端把这个文件保存到如下目录下
         */
//        String rootDir = "/Users/zhoushuo/Documents/tmp/target";

        try {
            serverSocket = new ServerSocket(Integer.parseInt(port));
            System.out.println("server start listening on port : " + port);

            while(true){
                connection = serverSocket.accept();
                System.out.println("server get connection from client ...");

                is = connection.getInputStream();

                /**
                 * (1)读取byte of (json) length
                 */
                byte[] byteInt = new byte[4];
                is.read(byteInt);
                int length = ByteUtil.byte2int(byteInt);
                System.out.println("length of json: " + length);

                /**
                 * (2)读取byte of json
                 */
                byte[] byteJson = new byte[length];
                int n = is.read(byteJson);
                String json = ByteUtil.byte2String(byteJson);
                System.out.println("json from client: " + json);

                // json -> object
                ObjectMapper objectMapper = new ObjectMapper();
                FileInfo fileInfo = null;
                try {
                    fileInfo = objectMapper.readValue(json, FileInfo.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                // 从FileInfo中获取文件信息
                String filenameOrigin = fileInfo.getFileName();
                String filename = URLDecoder.decode(filenameOrigin,"UTF-8");
                String md5 = fileInfo.getMd5();
                long filesize = fileInfo.getFilesize();

                /**
                 * (3)读取文件
                 */
                String targetFilePath = rootDir + File.separator + filename;
                System.out.println("targetFilePath : " + targetFilePath);
                FileUtil.streamToFile(is, targetFilePath);
                System.out.println("ftp server finish save file to local ...");

                /**
                 * (4)校验文件
                 */
                File targetFile = new File(targetFilePath);
                String targetMd5 = SecureUtil.md5(targetFile);
                if(targetMd5.equals(md5)){
                    System.out.println("md5 verity passed ...");
                }else{
                    System.out.println("md5 verify failed ...");
                }
                if(targetFile.length() == filesize){
                    System.out.println("filesize verity passed ...");
                }else{
                    System.out.println("filesize verity failed ...");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

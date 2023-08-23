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

        if(!(new File(rootDir)).exists()){
            System.out.println("server root dir not extists!");
            return;
        }

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
        try {
            serverSocket = new ServerSocket(Integer.parseInt(port));
            System.out.println("server start listening on port : " + port);

            while(true){

                try{
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
                    is.read(byteJson);
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
                     * (3)如果是按照dir形式传输，那么要尝试创建一下该文件的父目录
                     * 比如从客户端传过来的parentDir是genlib16
                     * 服务端的rootPath为： C:\document\root
                     * 那么服务端要尝试先创建这个目录： C:\document\root\genlib16
                     */
                    String parentDirName = fileInfo.getParentDir();
                    String rootDirPath = rootDir;
                    if(null != parentDirName && !parentDirName.equals("") && parentDirName.length() >0){
                        // 如果有parentDirName，那么服务端的rootPath要加上这个parentDirName
                        rootDirPath = rootDir + File.separator + parentDirName;
                        File parentDir = new File(rootDirPath);
                        if(!parentDir.exists()){
                            parentDir.mkdir();
                        }
                    }


                    /**
                     * (4)读取文件之前，先判断一下这个文件是否存在
                     */
                    String targetFilePath = rootDirPath + File.separator + filename;
                    System.out.println("targetFilePath : " + targetFilePath);

                    File preCheckFile = new File(targetFilePath);
                    if( preCheckFile.exists()){
                        String targetMD5 = SecureUtil.md5(preCheckFile);
                        if(targetMD5.equals(md5)){
                            System.out.println("file exists! targetFilePath : " + targetFilePath);
                            continue;
                        }else{
                            System.out.println("file exists but md5 verify failed ! retranfer... " +
                                    "targetFilePath：" + targetFilePath);
                            preCheckFile.delete();
                        }
                    }

                    /**
                     * (5)正式读取文件
                     */
                    FileUtil.streamToFile(is, targetFilePath);
                    System.out.println("ftp server finish save file to local ...");

                    /**
                     * (6)校验文件
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
                }finally{
                    if(null != is){
                        is.close();
                    }
                    if(null != connection){
                        connection.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

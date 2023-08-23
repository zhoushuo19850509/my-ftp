package com.nbcb.athena.ftpdemo.test.cmdline;

import java.util.Scanner;

public class CmdClientTest {
    public static void main(String[] args) {
        while(true){
            System.out.print("ftp-demo>");
            Scanner in = new Scanner(System.in);
            String cmd = in.nextLine();
            if(!validateCommand(cmd)){
                System.out.print("invalid command! \n");
            }

            /**
             * 列出命令
             */
            if(cmd.equals("list")){
                System.out.println("list the commands");
            }

            /**
             * 连接
             * 连接到ftp server
             * connect 192.168.0.105:9999
             */
            if(cmd.startsWith("connect")){
                System.out.println("connect to host:port");
                System.out.println("host: ");
                System.out.println("port: ");
            }

            /**
             * 发送
             * 发送文件或者目录
             * send /home/athena/Document/tmp/source
             */
            if(cmd.startsWith("send")){
                System.out.println("send file/dir");

            }

            /**
             * 校验
             * 校验文件或者目录
             */
            if(cmd.startsWith("verify")){
                System.out.println("verify file/dir");
            }

            /**
             * 断开连接
             */
            if(cmd.equals("by")){
                System.out.println("close the connection and exit...");
            }



        }
    }

    public static boolean validateCommand(String command){
        return true;
    }

}

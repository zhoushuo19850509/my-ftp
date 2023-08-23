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
             * �г�����
             */
            if(cmd.equals("list")){
                System.out.println("list the commands");
            }

            /**
             * ����
             * ���ӵ�ftp server
             * connect 192.168.0.105:9999
             */
            if(cmd.startsWith("connect")){
                System.out.println("connect to host:port");
                System.out.println("host: ");
                System.out.println("port: ");
            }

            /**
             * ����
             * �����ļ�����Ŀ¼
             * send /home/athena/Document/tmp/source
             */
            if(cmd.startsWith("send")){
                System.out.println("send file/dir");

            }

            /**
             * У��
             * У���ļ�����Ŀ¼
             */
            if(cmd.startsWith("verify")){
                System.out.println("verify file/dir");
            }

            /**
             * �Ͽ�����
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

package com.nbcb.athena.ftpdemo.test;

import java.util.Scanner;

public class SystemInputTest {
    public static void main(String[] args) {


        /**
         * ��ȡ����˼����Ķ˿�
         */
        System.out.println("please input the server port(default 9999)");
        Scanner in = new Scanner(System.in);
        String port = in.nextLine();
        if(null == port || port.equals("")){
            port = "9999";
        }
        System.out.println("server will listen on port: " + port);

        /**
         * ��ȡftp server��root dir
         */
        System.out.println("please enter the server root dir");
        String rootDir = in.nextLine();
        System.out.println("server root dir: " + rootDir);



    }
}

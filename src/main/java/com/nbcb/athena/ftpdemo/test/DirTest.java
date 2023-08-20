package com.nbcb.athena.ftpdemo.test;

import java.io.File;

public class DirTest {

    public static void main(String[] args) {
//        File dir = new File("/Users/zhoushuo/Documents/tmp/source");
//        File[] files = dir.listFiles();
//        for(File file : files){
//            System.out.println(file.getName());
//            System.out.println(file.getAbsolutePath());
//        }

        File dir1 = new File("/Users/zhoushuo/Documents/tmp/gogogo");
        System.out.println(dir1.exists());
        dir1.mkdir();
        System.out.println(dir1.exists());

    }

}

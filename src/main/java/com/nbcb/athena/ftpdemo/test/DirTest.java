package com.nbcb.athena.ftpdemo.test;

import java.io.File;

public class DirTest {

    public static void main(String[] args) {
        File dir = new File("/Users/zhoushuo/Documents/tmp/source");
        File[] files = dir.listFiles();
        for(File file : files){
            System.out.println(file.getName());
            System.out.println(file.getAbsolutePath());
        }
    }

}

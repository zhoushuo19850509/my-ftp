package com.nbcb.athena.ftpdemo.test;


import com.nbcb.athena.ftpdemo.model.FileInfo;

import java.io.File;

public class FileTest {
    public static void main(String[] args) {
        File file = new File("/Users/zhoushuo/Documents/tmp/aa.txt");
        System.out.println(file.length());
        System.out.println(file.getParentFile().getName());

        FileInfo fileInfo = new FileInfo(file);
        System.out.println(fileInfo);


    }
}

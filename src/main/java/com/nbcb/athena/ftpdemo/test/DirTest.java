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

//        File dir1 = new File("/Users/zhoushuo/Documents/tmp/gogogo");
//        System.out.println(dir1.exists());
//        dir1.mkdir();
//        System.out.println(dir1.exists());

        /**
         * 验证父目录
         */
        String dirPath = "/Users/zhoushuo/Documents/tmp/source";
        File dir = new File(dirPath);
        if(!dir.isDirectory()){
            System.out.println("illegal directory ...");
            return;
        }

        File[] files = dir.listFiles();
        for(File file : files){
            /**
             * 当前文件的父目录名称
             * 比如当前文件是： /Users/zhoushuo/Documents/tmp/aa.txt
             * 那么父目录为： tmp
             */
            String parentDirName = file.getParentFile().getName();
            System.out.println("parentDirName: " + parentDirName);
        }
    }

}

package com.nbcb.athena.ftpdemo.model;

import cn.hutool.crypto.SecureUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 文件元数据
 */
public class FileInfo {

    public FileInfo(File file) {

        try {
            String currentName = file.getName();

            /**
             * filename处理一： 关键字过滤
             */
            currentName = currentName.replace("\\","-");
            currentName = currentName.replace("/","-");
            currentName = currentName.replace(":","-");

            /**
             * filename处理二： URL编码，防止中文在传输过程中异常
             */
            currentName = URLEncoder.encode(currentName, "UTF-8");
            this.fileName = currentName;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.filesize = file.length();
        this.md5 = SecureUtil.md5(file);
    }

    public FileInfo() {
    }

    private String fileName;
    private long filesize;
    private String md5;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileName='" + fileName + '\'' +
                ", filesize=" + filesize +
                ", md5='" + md5 + '\'' +
                '}';
    }
}

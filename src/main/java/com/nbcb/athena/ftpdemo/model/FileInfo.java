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

    /**
     * fileName
     */
    private String fileName;
    /**
     * filesize
     */
    private long filesize;
    /**
     * 根据file content生成的md5
     */
    private String md5;
    /**
     * 文件所属的目录
     * 在目录传输模式下，
     * 比如我们这次要传输的dir为：
     * /Users/zhoushuo/Downloads/genlib14
     *
     * 这个目录下有一个文件：
     * /Users/zhoushuo/Downloads/genlib14/a.txt
     * 那么a.txt的dir就是genlib14
     *
     * 如果这个目录下有一个(嵌套目录的)文件：
     * Users/zhoushuo/Downloads/genlib14/mydir/a.txt
     * 那么a.txt的dir就是genlib14/mydir
     *
     * 在服务端，在保存a.txt之前，需要先创建a.txt所属的dir
     */
    private String dir;

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

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileName='" + fileName + '\'' +
                ", filesize=" + filesize +
                ", md5='" + md5 + '\'' +
                ", dir='" + dir + '\'' +
                '}';
    }
}

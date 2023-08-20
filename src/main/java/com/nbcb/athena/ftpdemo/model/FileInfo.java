package com.nbcb.athena.ftpdemo.model;

import cn.hutool.crypto.SecureUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * �ļ�Ԫ����
 */
public class FileInfo {

    public FileInfo(File file) {
        try {
            String currentName = file.getName();
            /**
             * filename����һ�� �ؼ��ֹ���
             */
            currentName = currentName.replace("\\","-");
            currentName = currentName.replace("/","-");
            currentName = currentName.replace(":","-");

            /**
             * filename������� URL���룬��ֹ�����ڴ���������쳣
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
     * ����file content���ɵ�md5
     */
    private String md5;
    /**
     * �ļ�������Ŀ¼
     * ��Ŀ¼����ģʽ�£�
     * �����������Ҫ�����dirΪ��
     * /Users/zhoushuo/Downloads/genlib14
     *
     * ���Ŀ¼����һ���ļ���
     * /Users/zhoushuo/Downloads/genlib14/a.txt
     * ��ôa.txt��dir����genlib14
     *
     * ������Ŀ¼����һ��(Ƕ��Ŀ¼��)�ļ���
     * Users/zhoushuo/Downloads/genlib14/mydir/a.txt
     * ��ôa.txt��dir����genlib14/mydir
     *
     * �ڷ���ˣ��ڱ���a.txt֮ǰ����Ҫ�ȴ���a.txt������dir
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

package com.nbcb.athena.ftpdemo.util;

import java.io.*;

public class FileUtil {


    /**
     * InputStream -> File
     * @param filePath
     */
    public static void streamToFile(InputStream in, String filePath){
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {

            bis = new BufferedInputStream(in);
            bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            int bytesRead = 0;
            byte[] buffer = new byte[2048];
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (bos != null) {
                    bos.close();
                }

                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据文件路径，返回该文件的二进制流
     * @param filePath
     * @return
     */
    public static byte[] toByte(String filePath){
        byte[] content = null;

        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(filePath);
            out = new ByteArrayOutputStream();

            int b = 0;
            while((b = in.read()) != -1){
                out.write(b);
            }
            content = out.toByteArray();


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return content;
    }

}

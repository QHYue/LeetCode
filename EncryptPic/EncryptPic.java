package com.arcsoft;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EncryptPic {
    /**
     *遍历指定目录中的所有jpg文件
     */
    public static List<File> searchFiles(File folder, final String keyword) {
        List<File> result = new ArrayList<File>();
        if (folder.isFile()){
            result.add(folder);
        }
        File[] subFolders = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                if (file.getName().toLowerCase().endsWith(keyword)) {  //指定后缀为jpg
                    System.out.println(file.getName());
                    return true;
                }
                return false;
            }
        });
        if (subFolders != null) {
            for (File file : subFolders) {
                if (file.isFile()) {
                    // 如果是文件则将文件添加到结果列表中
                    result.add(file);
                } else {
                    // 如果是文件夹，则递归调用本方法，然后把所有的jpg文件加到结果列表中
                    result.addAll(searchFiles(file, keyword));
                }
            }
        }
        return result;
    }

    /**
     * 将文件转换成16进制输出
     * @param fileName
     * @return
     */
    public static String readFileByBytesHex(String fileName) {
        File file = new File(fileName);
        InputStream in = null;
        try {
            // 一次读一个字节
            StringBuilder buffer = new StringBuilder();
            in = new FileInputStream(file);
            int tempbyte;
            while ((tempbyte = in.read()) != -1) {
                String s = Integer.toHexString(tempbyte);
                buffer.append(s);
            }
            in.close();
            System.out.println(buffer.toString());
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将文件转换成byte数组
     * @param filePath
     * @return
     */
    public static byte[] File2byte(String filePath){
        File tradeFile = new File(filePath);
        byte[] buffer = null;
        try
        {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 检测首部是否含有76、97字符
     * @param fileName
     * @return
     */
    public static boolean detectBytes(String fileName){
        String hexStr = readFileByBytesHex(fileName);
        if (hexStr == null || hexStr.length() <4){
            return false;
        }
        String subA = hexStr.substring(0, 2);
        String subB = hexStr.substring(2, 4);

        if ("76".equals(subA)&&"97".equals(subB)){
            return true;
        }
        return false;
    }


    /**
     * 将首部两个字节追加到文件尾部
     * @param fileName
     */
    public static void copyBytes(String fileName){
        byte[] bytes = File2byte(fileName);
        byte[] newbytes = new byte[bytes.length+2];
        int i = 0;
        for (; i < newbytes.length-2; i++) {
            newbytes[i]=bytes[i];
        }
        newbytes[i]=bytes[0];
        newbytes[i+1]=bytes[1];
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(fileName);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(newbytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     *替换首部字节为76、97
     * @param fileName
     */
    public static void replaceByte(String fileName){
        byte[] bytes = File2byte(fileName);
        bytes[0]=(byte)0x76;
        bytes[1]=(byte)0x97;
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(fileName);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     *  删除尾部字节、恢复首部字节，图片正常显示
     * @param fileName
     */
    public static void recoverBytes(String fileName){
        byte[] bytes = File2byte(fileName);
        int length = bytes.length;
        bytes[0]=bytes[length-2];
        bytes[1]=bytes[length-1];
        byte[] newbytes = new byte[length-2];
        for (int i = 0; i < newbytes.length; i++) {
            newbytes[i]=bytes[i];
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(fileName);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(newbytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }



    public static void main(String[] args) {
        String fileName = "D:\\arcsoft\\pic\\3.jpg";
        EncryptPic.recoverBytes(fileName);
        //EncryptPic.readFileByBytesHex(fileName);

    }
}

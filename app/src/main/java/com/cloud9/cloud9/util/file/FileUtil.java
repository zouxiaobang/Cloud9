package com.cloud9.cloud9.util.file;

import android.os.Environment;


import com.cloud9.cloud9.app.Cloud9;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.http.DELETE;
import retrofit2.http.PUT;
/**
 * author: xb.Zou
 * date: 2018/9/4 0004
 **/
public class FileUtil {
    private static final String SDCARD_DIR = Environment.getExternalStorageDirectory().getPath();
    private static final String TIME_FORMAT = "_yyyyMMdd_HHmmss";
    private static final String DEFAULT_DB_PATH = "latte_db_file";

    /**
     * 将数据写入到对应的文件
     *
     * @param is
     * @param file
     * @return
     */
    public static final File write2Disk(InputStream is, File file) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(new FileOutputStream(file));

            byte[] data = new byte[1024 * 4];
            int count = 0;
            while ((count = bis.read(data)) != -1) {
                bos.write(data, 0, count);
            }

            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    public static final File write2Disk(InputStream is, String dir, String name) {
        final File file = FileUtil.createFile(dir, name);

        return write2Disk(is, file);
    }

    public static final File write2Disk(InputStream is, String dir, String prefix, String extension) {
        final File file = FileUtil.createFileByTime(dir, prefix, extension);

        return write2Disk(is, file);
    }

    public static final File write2DbFile(String str, String fileName, boolean tailing){
        if (!isExternalMounted()){
            return null;
        }

        File dir = new File(getExternalPath(), DEFAULT_DB_PATH);
        if (!dir.exists()){
            dir.mkdirs();
        }

        File file = new File(dir, fileName);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, tailing));
            bw.write(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        }
    }

    public static final String readByDbFile(String fileName){
        if (!isExternalMounted()){
            return null;
        }

        File dir = new File(getExternalPath(), DEFAULT_DB_PATH);
        if (!dir.exists()){
            dir.mkdirs();
        }

        File file = new File(dir, fileName);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return sb.toString();
        }
    }

    public static final ArrayList<String> readByDbFileList(String fileName){
        if (!isExternalMounted()){
            return null;
        }

        File dir = new File(getExternalPath(), DEFAULT_DB_PATH);
        if (!dir.exists()){
            dir.mkdirs();
        }

        File file = new File(dir, fileName);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BufferedReader reader = null;
        ArrayList<String> userList = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = reader.readLine()) != null){
                userList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return userList;
        }
    }




    public static final boolean isExternalMounted(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private static final String getExternalPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }


    /**
     * 获取raw文件中的内容。
     *
     * @param rawId
     * @return
     */
    public static final String getRawString(int rawId) {
        final InputStream is = Cloud9.getApplicationContext().getResources().openRawResource(rawId);
        final BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
        final StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null){
                    is.close();
                }
                if (br != null){
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }


    private static File createFileByTime(String dir, String timeFormatHeader, String extension) {
        final String fileName = getFileNameByTime(timeFormatHeader, extension);
        return createFile(dir, fileName);
    }


    public static File createFile(String sdcardDirName, String name) {
        return new File(createDir(sdcardDirName), name);
    }

    public static String getExtension(String filePath) {
        String suffix = "";

        final File file = new File(filePath);
        final String name = file.getName();
        final int index = name.lastIndexOf(".");
        if (index > 0) {
            suffix = name.substring(index + 1);
        }
        return suffix;
    }


    private static File createDir(String sdcardDirName) {
        //拼接sd卡的完整路径
        final String dir = SDCARD_DIR + "/" + sdcardDirName + "/";
        final File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        return fileDir;
    }

    private static String getFileNameByTime(String timeFormatHeader, String extension) {
        return getTimeFormatName(timeFormatHeader) + "." + extension;
    }

    private static String getTimeFormatName(String timeFormatHeader) {
        final Date date = new Date(System.currentTimeMillis());

        final SimpleDateFormat formatter
                = new SimpleDateFormat("'" + timeFormatHeader + "'" + TIME_FORMAT
                , Locale.getDefault());

        return formatter.format(date);
    }
}

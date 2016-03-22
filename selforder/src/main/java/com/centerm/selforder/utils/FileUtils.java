package com.centerm.selforder.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

public class FileUtils {

    private final static Log4d logger = Log4d.getInstance();

    public final static long KB = 1024;
    public final static long MB = 1024 * KB;
    public final static long GB = 1024 * MB;

    /**
     * 格式化文件大小，格式化成为易读字符串
     *
     * @param fileSize
     * @return
     */
    public static String formatSize(long fileSize) {
        if (fileSize < 0) {
            return "0B";
        }
        String formatSize = "";
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileSize < KB) {
            formatSize = df.format((double) fileSize) + "B";
        } else if (fileSize < MB) {
            formatSize = df.format((double) fileSize / KB) + "KB";
        } else if (fileSize < GB) {
            formatSize = df.format((double) fileSize / MB) + "MB";
        } else {
            formatSize = df.format((double) fileSize / GB) + "GB";
        }
        return formatSize;
    }

    /**
     * @return 判断手机是否有SD卡
     */
    public static boolean hasSDCard() {
        String sDCardStatus = Environment.getExternalStorageState();
        boolean status;
        if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
            status = true;
        } else
            status = false;
        return status;
    }

    /**
     * 获取SD卡根目录的绝对路径
     *
     * @return
     */
    public static String getSDCardRootPath() {
        if (hasSDCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }

    /**
     * 获取内置存储根目录的绝对路径
     *
     * @return
     */
    public static String getPhoneStorageRootPath() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    /**
     * 根据文件绝对路径获取文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (filePath == null || "".equals(filePath))
            return "";
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    /**
     * 获取文件大小
     *
     * @param filePath
     * @return
     */
    public static long getFileSize(String filePath) {
        long size = 0;

        File file = new File(filePath);
        if (file != null && file.exists()) {
            size = file.length();
        }
        return size;
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    public static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace = 0;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (freeSpace);
    }

    /**
     * 计算SD卡的已用空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    public static long getUsedDiskSpace() {
        String status = Environment.getExternalStorageState();
        long usedSpace = 0;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                long totalBlocks = stat.getBlockCount();
                usedSpace = blockSize * (totalBlocks - availableBlocks);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (usedSpace);
    }

    /**
     * 新建目录
     *
     * @param directoryName
     * @return
     */
    public static boolean createDirectory(String directoryName) {
        boolean status;
        if (!directoryName.equals("")) {
            File newPath = new File(directoryName);
            status = newPath.mkdirs();
            status = true;
        } else
            status = false;
        return status;
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();

        if (!fileName.equals("")) {
            File newPath = new File(fileName);
            checker.checkDelete(newPath.toString());
            if (newPath.isFile()) {
                try {
                    newPath.delete();
                    status = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                    status = false;
                }
            } else
                status = false;
        } else
            status = false;
        return status;
    }

    /**
     * 重命名
     *
     * @param oldName
     * @param newName
     * @return
     */
    public static boolean reNamePath(String oldName, String newName) {
        File f = new File(oldName);
        return f.renameTo(new File(newName));
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static boolean deleteFileWithPath(String filePath) {
        SecurityManager checker = new SecurityManager();
        File f = new File(filePath);
        checker.checkDelete(filePath);
        if (f.isFile()) {
            f.delete();
            return true;
        }
        return false;
    }

    /**
     * 清空指定文件夹下的所有文件，不包括子目录
     *
     * @param dir
     */
    public synchronized static void deleteAllFiles(String dir) {
        File file = new File(dir);
        if (file.exists() && file.isDirectory()) {
            File[] childFiles = file.listFiles();
            for (int i = 0; i < childFiles.length; i++) {
                File f = childFiles[i];
                if (f.isFile()) {
                    f.delete();
                }
            }
        }
    }

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
     *
     * @param context
     * @param msg
     */
    public static void write(Context context, String fileName, String content) {
        if (content == null)
            content = "";
        try {
            FileOutputStream fos = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            fos.write(content.getBytes());

            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test(Context context) {
        logger.info(FileUtils.class, "getDataDirectory== " + Environment.getDataDirectory());
        logger.info(FileUtils.class, "getExternalStorageState== " + Environment.getExternalStorageState());
        logger.info(FileUtils.class, "getDownloadCacheDirectory== " + Environment.getDownloadCacheDirectory());
        logger.info(FileUtils.class, "context.getCacheDir()== " + context.getCacheDir());

      /*  String cachePath = context.getCacheDir().getAbsolutePath();
        String fileName = cachePath + File.separator + "dssdlwl";
        File file = new File(fileName);
        if (!file.exists()){
            try {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write("上点击发送的距离圣诞节收到了开发技术的".getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        logger.info(FileUtils.class, "getRootDirectory== " + Environment.getRootDirectory());
        logger.info(FileUtils.class, "getExternalStorageDirectory== " + Environment.getExternalStorageDirectory());


        String sdPath = getSDCardRootPath();
        logger.info(FileUtils.class, "SD路径== " + sdPath);
        String sdUsedSize = formatSize(getUsedDiskSpace());
        logger.info(FileUtils.class, "SD卡已用空间== " + sdUsedSize);
        String sdFreeSize = formatSize(getFreeDiskSpace());
        logger.info(FileUtils.class, "SD卡剩余空间== " + sdFreeSize);
        String phonePath = getPhoneStorageRootPath();
        logger.info(FileUtils.class, "内置存储路径== " + phonePath);
    }

}

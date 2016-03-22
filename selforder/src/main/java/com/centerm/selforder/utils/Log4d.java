package com.centerm.selforder.utils;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 日志打印辅助类，可以设置写入文件。
 */
public class Log4d {
    /**
     * Tag之间分隔符
     */
    private final static String TAG_SEPERATION = "_";
    /**
     * 全局Tag，用来标识应用
     */
    private static String globalTag = "Log4d";
    /**
     * Log工具实例
     */
    private static Log4d instance = new Log4d();
    /**
     * Log总开关
     */
    private static boolean toggleFlag = true;

    /**
     * 标签过滤器
     */
    private static Set<String> tagFilter;

    private static String fileDirectory;

    private Log4d() {
    }

    /**
     * 设置全局标签，用来标识应用，默认为"Log4d";
     *
     * @param globalTag
     */
    public static void setGlobalTag(String globalTag) {
        Log4d.globalTag = globalTag;
    }

    public static void setLogDirectory(String directory) {
        Log4d.fileDirectory = directory;
    }

    public static String getLogDirectory() {
        return Log4d.fileDirectory;
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static Log4d getInstance() {
        return instance;
    }

    /**
     * 设置标签过滤器。只有符合数组中的标签Log才会被打印出来。 每一次设置过滤器都会覆盖掉之前的过滤器。
     * 默认不设置标签过滤器，那么所有的日志信息都会被打印出来。
     *
     * @param tags 需要打印出来的标签数组。
     */
    public static void setTagFilter(String[] tags) {
        if (null == tags || tags.length == 0) {
            tagFilter = null;
            return;
        }
        int len = tags.length;
        tagFilter = new HashSet<String>();
        for (int i = 0; i < len; i++) {
            String tag = tags[i];
            if (tag == null || tag.trim().isEmpty()) {
                continue;
            }
            tagFilter.add(tag);
        }
        if (tagFilter.size() == 0) {
            tagFilter = null;
        }
    }

    private String combination(String localTag) {
        localTag = localTag == null ? "" : localTag;
        return globalTag + TAG_SEPERATION + localTag;

    }

    public static void toggle(boolean b) {
        Log4d.toggleFlag = b;
    }

    public static boolean isDebug() {
        return toggleFlag;
    }

    public void verbose(String localTag, String content) {
        log(Level.VERBOSE, localTag, content);
    }

    public void verbose(Class clss, String content) {
        String localTag = clss.getSimpleName();
        log(Level.VERBOSE, localTag, content);
    }

    public void debug(String localTag, String content) {
        log(Level.DEBUG, localTag, content);
    }

    public void debug(Class clss, String content) {
        String localTag = clss.getSimpleName();
        log(Level.DEBUG, localTag, content);
    }

    public void info(String localTag, String content) {
        log(Level.INFO, localTag, content);
    }

    public void info(Class clss, String content) {
        String localTag = clss.getSimpleName();
        log(Level.INFO, localTag, content);
    }

    public void warn(String localTag, String content) {
        log(Level.WARN, localTag, content);
    }

    public void warn(Class clss, String content) {
        String localTag = clss.getSimpleName();
        log(Level.WARN, localTag, content);
    }

    public void error(String localTag, String content) {
        log(Level.ERROR, localTag, content);
    }

    public void error(Class clss, String content) {
        String localTag = clss.getSimpleName();
        log(Level.ERROR, localTag, content);
    }

    public void error(String localTag, Throwable throwable) {
        if (throwable == null) {
            return;
        }
        String stackTrace = printStackTraceToString(throwable);
        log(Level.ERROR, localTag, stackTrace);
    }

    public boolean log(final Level level, final String localTag, final String content) {
        final String tag = combination(localTag);
        if (tag == null || !toggleFlag) {
            return false;
        }
        if (tagFilter != null) {
            if (!tagFilter.contains(localTag)) {
                return false;
            }
        }
        switch (level) {
            case VERBOSE:
                Log.v(tag, content);
                break;

            case DEBUG:
                Log.d(tag, content);
                break;

            case INFO:
                Log.i(tag, content);
                break;

            case WARN:
                Log.w(tag, content);
                break;

            case ERROR:
                Log.e(tag, content);
                break;
        }
        if (fileDirectory != null) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    writeLog(level, tag, content);
                }
            }).start();
        }

        return true;
    }

    private final static String DIVIDER = "  ";

    private synchronized void writeLog(Level level, String tag, String content) {
        if (fileDirectory == null) {
            return;
        }
        File dic = new File(fileDirectory);
        if (!dic.exists()) {
            if (!dic.mkdirs()) {
                return;
            }
        }
        SimpleDateFormat spd = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = spd.format(new Date()) + ".txt";
        File file = new File(fileDirectory + File.separator + fileName);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
        StringBuilder sb = new StringBuilder();
        sb.append(level.getAlignedLabel()).append(DIVIDER).append(formater.format(new Date())).append(DIVIDER).append(tag)
                .append(DIVIDER).append(content);
        BufferedWriter bufw = null;
        try {
            bufw = new BufferedWriter(new FileWriter(file, true));
            if (file.length() != 0) {
                bufw.newLine();
            }
            bufw.write(new String(sb.toString().getBytes(), "UTF-8"));
            bufw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufw != null) {
                try {
                    bufw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String printStackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        pw.println();
        t.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public enum Level {
        VERBOSE("verbose"), DEBUG("debug"), INFO("info"), WARN("warn"), ERROR("error");

        private String descp;

        private Level(String descp) {
            this.descp = descp;
        }

        public String getDescp() {
            return descp;
        }

        public String getAlignedLabel() {
            int len = VERBOSE.getDescp().length();
            if (descp.length() < len) {
                int a = len - descp.length();
                for (int i = 0; i < a; i++) {
                    descp = descp + " ";
                }
            }
            return descp;
        }
    }
}

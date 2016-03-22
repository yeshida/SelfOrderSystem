package com.centerm.selforder.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * MD5工具类。
 */
public class MD5Utils {
    public static String getMD5Str(String str) {

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString().toUpperCase().trim();
    }

    public static String getMD5Str(Map<String, String> map, String MD5_key) {

        String str = getSortHashString(map, MD5_key);
        return getMD5Str(str);
    }

    //将HashMap排序并在末尾添加MD5key
    private static String getSortHashString(Map<String, String> hashMap, String MD5_key) {

        if (hashMap == null) {
            return MD5_key;
        }
        String string = "";
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(
                hashMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        for (int a = 0; a < list.size(); a++) {
            if (a == list.size() - 1)
                string += list.get(a).toString();
            else {
                string += list.get(a).toString() + "&";
            }
        }
        return string += MD5_key;
    }
}

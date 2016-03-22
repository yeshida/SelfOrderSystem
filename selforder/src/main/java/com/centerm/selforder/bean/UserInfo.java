package com.centerm.selforder.bean;

/**
 * Created by caiqingyuan on 16/3/15.
 */
public class UserInfo {



    private static String Manager_Phone;//手机号码
    private static String Manager_NAME;//主管姓名



    private static String Shop_Name;//店铺名称

    private static String USER_ID;//账号
    private static String USER_NAME;//用户名
    private static String EMAIL; // 邮箱
    private static String ID_CARD;//身份证
    private static String MOBILE;//手机号
    private static String MCHNT_NAME;// 商户名
    private static String MCHNT_ADDR;//商户地址
    private static String MCHNT_CD;//商户号

    private static String  TERMINAL_SN;//终端sn

    private static UserInfo info = new UserInfo();

    public static void setUserId(String userId) {
        USER_ID = userId;
    }

    public static void setMchntCd(String mchntCd) {
        MCHNT_CD = mchntCd;
    }

    public static UserInfo getInstance() {
        return info;
    }

    public String getMCHNT_CD() {

        return MCHNT_CD;
    }

    public String getUSER_ID() {

        return USER_ID;
    }

    public static String getTerminalSn() {
        return TERMINAL_SN;
    }

    public static void setTerminalSn(String terminalSn) {
        TERMINAL_SN = terminalSn;
    }
    public static String getUserName() {
        return USER_NAME;
    }

    public static String getEMAIL() {
        return EMAIL;
    }

    public static String getIdCard() {
        return ID_CARD;
    }

    public static String getMOBILE() {
        return MOBILE;
    }

    public static String getMchntName() {
        return MCHNT_NAME;
    }

    public static String getMchntAddr() {
        return MCHNT_ADDR;
    }
    public static void setUserName(String userName) {
        USER_NAME = userName;
    }

    public static void setEMAIL(String EMAIL) {
        UserInfo.EMAIL = EMAIL;
    }

    public static void setIdCard(String idCard) {
        ID_CARD = idCard;
    }

    public static void setMOBILE(String MOBILE) {
        UserInfo.MOBILE = MOBILE;
    }

    public static void setMchntName(String mchntName) {
        MCHNT_NAME = mchntName;
    }

    public static void setMchntAddr(String mchntAddr) {
        MCHNT_ADDR = mchntAddr;
    }
    public static String getManager_NAME() {
        return Manager_NAME;
    }

    public static void setManager_NAME(String manager_NAME) {
        Manager_NAME = manager_NAME;
    }
    public static String getManager_Phone() {
        return Manager_Phone;
    }

    public static void setManager_Phone(String manager_Phone) {
        Manager_Phone = manager_Phone;
    }
    public static String getShop_Name() {
        return Shop_Name;
    }

    public static void setShop_Name(String shop_Name) {
        Shop_Name = shop_Name;
    }
}

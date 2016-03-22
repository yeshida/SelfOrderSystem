package com.centerm.selforder.bean;

/**
 * Created by linwanliang on 2016/3/14.
 * 菜品实体。
 */
public class Dish {

    //所属类别ID
    private String typeId;
    //菜品ID
    private String id;
    //菜品名
    private String name;
    //菜品价格
    private double price;
    //菜品描述
    private String detail;
    //菜品图片地址
    private String picUrl;
    //菜品口味
    private String tastes;
    //菜品口味数组
    private String[] tasteArray;
    //菜品库存
    private int inventory;

    public Dish(String id) {
        this.id = id;
    }

    public Dish(String id, String typeId, String detail, String name, String picUrl, double price, String tastes) {
        this.id = id;
        this.typeId = typeId;
        this.detail = detail;
        this.name = name;
        this.picUrl = picUrl;
        this.price = price;
        this.tastes = tastes;
        if (tastes != null && (!tastes.equals("null")) && (!tastes.equals(""))) {
            tasteArray = tastes.split("|");
        }
    }

    public String getDetail() {
        return detail;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public double getPrice() {
        return price;
    }

    public String getTastes() {
        return tastes;
    }

    public int getInventory() {
        return inventory;
    }

    public String getTypeId() {
        return typeId;
    }

    public String[] getTasteArray() {
        return tasteArray;
    }

    protected void setDetail(String detail) {
        this.detail = detail;
    }

    protected void setId(String id) {
        this.id = id;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    protected void setPrice(double price) {
        this.price = price;
    }

    protected void setTastes(String tastes) {
        this.tastes = tastes;
    }

    protected void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}

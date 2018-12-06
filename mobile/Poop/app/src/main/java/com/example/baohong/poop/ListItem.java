package com.example.baohong.poop;

public class ListItem {
    private String head;
    private String desc;
    private String imgURL;
    private String userName;

    public ListItem(String head, String desc, String imgURL, String userName) {
        this.head = head;
        this.desc = desc;
        this.imgURL = imgURL;
        this.userName = userName;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getUserName(){ return userName; }
}

package com.example.baohong.poop;

public class ListItem {
    private String head;
    private String desc;
    private String imgURL;

    public ListItem(String head, String desc, String imgURL) {
        this.head = head;
        this.desc = desc;
        this.imgURL = imgURL;
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
}

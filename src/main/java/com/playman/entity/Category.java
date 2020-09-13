package com.playman.entity;

public class Category implements Comparable<Category>{
    private int cid;//分类id
    private String cname;//分类名称

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                '}';
    }

    @Override
    public int compareTo(Category o) {
        return this.getCid()-o.getCid();
    }
}

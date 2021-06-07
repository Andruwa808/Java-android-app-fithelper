package com.techit.fithelper;

import java.io.Serializable;

public class FormList implements Serializable {

    private String sName;
    private int kType;

    private boolean active;

    public FormList(String sName, int kType) {
        this.sName = sName;
        this.kType = kType;
        this.active = true;
    }

    public FormList(String sName, int kType, boolean active) {
        this.sName = sName;
        this.kType = kType;
        this.active = active;
    }

    public int getkType(){
        return kType;
    }

    public void setkType(int kType){
        this.kType=kType;
    }

    public String getsName(){
        return sName;
    }

    public void setsName(String sName){
        this.sName=sName;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active=active;
    }

    @Override
    public String toString(){
        return this.sName+"("+this.kType+")";
    }

}

package com.michaelt.databasetesting;

/**
 * Created by Michael on 4/6/2015.
 */
public class Malts {
    private String myName;
    private String myType;

    public Malts(String theName, String theType) {
        this.myName = theName;
        this.myType = theType;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String theName) {
        this.myName = theName;
    }

    public String getMyType() {
        return myType;
    }

    public void setMyType(String theType) {
        this.myType = theType;
    }
}

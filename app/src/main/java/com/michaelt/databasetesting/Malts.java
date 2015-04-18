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

    public String getName() {
        return myName;
    }

    public void setName(String theName) {
        this.myName = theName;
    }

    public String getType() {
        return myType;
    }

    public void setType(String theType) {
        this.myType = theType;
    }

}

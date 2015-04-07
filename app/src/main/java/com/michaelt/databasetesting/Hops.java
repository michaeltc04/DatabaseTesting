package com.michaelt.databasetesting;

/**
 * Created by Michael on 4/6/2015.
 */
public class Hops {

    private String myName;
    private String myAlphaAcid;

    public Hops(String theName, String theAlphaAcid) {
        this.myName = theName;
        this.myAlphaAcid = theAlphaAcid;
    }

    public void setName(String theName) {
        this.myName = theName;
    }

    public void setAlphaAcid(String theAlphaAcid) {
        this.myAlphaAcid = theAlphaAcid;
    }

    public String getName() {
        return myName;
    }

    public String getAlphaAcid() {
        return myAlphaAcid;
    }
}

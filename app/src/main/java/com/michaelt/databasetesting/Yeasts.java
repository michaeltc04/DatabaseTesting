package com.michaelt.databasetesting;

/**
 * Created by Michael on 4/6/2015.
 */
public class Yeasts {
    private String myName;
    private String myAttenuation;
    private String myFlocculation;
    private String myFermentingTemperature;

    public Yeasts(String myName, String theAttenuation, String theFlocculation, String theFermentingTemperature) {
        this.myName = myName;
        this.myAttenuation = theAttenuation;
        this.myFlocculation = theFlocculation;
        this.myFermentingTemperature = theFermentingTemperature;
    }

    public String getFermentingTemperature() {
        return myFermentingTemperature;
    }

    public void setFermentingTemperature(String theFermentingTemperature) {
        this.myFermentingTemperature = theFermentingTemperature;
    }

    public String getFlocculation() {
        return myFlocculation;
    }

    public void setFlocculation(String theFlocculation) {
        this.myFlocculation = theFlocculation;
    }

    public String getAttenuation() {
        return myAttenuation;
    }

    public void setAttenuation(String theAttenuation) {
        this.myAttenuation = theAttenuation;
    }

    public String getName() {
        return myName;
    }

    public void setName(String theName) {
        this.myName = theName;
    }
}

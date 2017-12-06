package com.codingfuns.blog.controller.bean;

import java.io.Serializable;

public class Rating implements Serializable{
    private static final long serialVersionUID = -3544210449050647632L;
    private int min;
    private int max;
    private int numRaters;
    private double average;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getNumRaters() {
        return numRaters;
    }

    public void setNumRaters(int numRaters) {
        this.numRaters = numRaters;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
package org.example.model;

import java.io.Serial;
import java.io.Serializable;

public class Location implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private double x;
    private float floatY;
    private int intY;
    private Integer intZ;
    private long longZ;
    private String name;

    public Location(double x, float y, Integer z){
        this.x = x;
        this.floatY = y;
        this.intZ = z;
    }
    public Location(double x, int y, long z, String name){
        this.x = x;
        this.intY = y;
        this.longZ = z;
        this.name = name;
    }

    public void setX(double x) {
        this.x = x;
    }
    public double getX() {
        return x;
    }

    public void setIntY(int intY) {
        this.intY = intY;
    }
    public int getIntY() {
        return intY;
    }

    public void setIntZ(Integer intZ) {
        this.intZ = intZ;
    }
    public Integer getIntZ() {
        return intZ;
    }

    public void setFloatY(float floatY) {
        this.floatY = floatY;
    }
    public float getFloatY() {
        return floatY;
    }

    public void setLongZ(long longZ) {
        this.longZ = longZ;
    }
    public long getLongZ() {
        return longZ;
    }

    public void setName(String name) {
            this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", floatY=" + floatY +
                ", intY=" + intY +
                ", intZ=" + intZ +
                ", longZ=" + longZ +
                ", name='" + name + '\'' +
                '}';
    }
}

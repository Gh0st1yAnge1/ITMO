package org.example.model;

public class Coordinates {
    private Float x;
    private float y;

    public Coordinates(Float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setX(Float x) {
            this.x = x;
    }
    public Float getX() {
        return x;
    }

    public void setY(float y) {
            this.y = y;
    }
    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{\n" +
                "        x=" + x +
                ",\n        y=" + y + '\n' +
                "    }";
    }
}

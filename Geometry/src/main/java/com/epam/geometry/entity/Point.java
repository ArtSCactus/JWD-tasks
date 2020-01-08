package com.epam.geometry.entity;

import java.util.Objects;

public class Point {
    private final double x;
    private final double y;
    private final double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        return Double.compare(((Point) o).getX(), getX()) == 0 &
                Double.compare(((Point) o).getY(), getY()) == 0 &
                Double.compare(((Point) o).getZ(), getZ()) == 0;
    }

    @Override
    public int hashCode() {
        int hash =1;
        hash= (int) (31*hash+x);
        hash= (int) (31*hash+y);
        hash= (int) (31*hash+z);
        return hash;
    }

    @Override
    public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
    }
}

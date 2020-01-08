package com.epam.geometry.idgenerator;

public class IdGenerator {
    private static long id;

    static {
        id = 0;
    }

    public static long getId() {
        return id++;
    }

    public static void setZero(){
        id=0;
    }
}

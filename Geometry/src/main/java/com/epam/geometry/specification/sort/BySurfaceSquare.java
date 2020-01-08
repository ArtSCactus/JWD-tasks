package com.epam.geometry.specification.sort;

import com.epam.geometry.entity.Cube;
import com.epam.geometry.specification.SortSpecification;

import java.util.Comparator;

public class BySurfaceSquare extends SortSpecification implements Comparator<Cube>  {

    @Override
    public int compare(Cube o1, Cube o2) {
        double o1Square =calculator.calculateCubeSurfaceSquare(o1);
        double o2Square = calculator.calculateCubeSurfaceSquare(o2);
        return Double.compare(o1Square, o2Square);
    }

}

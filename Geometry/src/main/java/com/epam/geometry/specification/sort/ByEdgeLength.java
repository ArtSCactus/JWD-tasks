package com.epam.geometry.specification.sort;

import com.epam.geometry.entity.Cube;
import com.epam.geometry.specification.SortSpecification;

import java.util.Comparator;

public class ByEdgeLength extends SortSpecification implements Comparator<Cube> {
    @Override
    public int compare(Cube o1, Cube o2) {
        double o1EdgeLength = calculator.calculateCubeEdge(o1);
        double o2EdgeLength = calculator.calculateCubeEdge(o2);
        return Double.compare(o1EdgeLength,o2EdgeLength);
    }
}

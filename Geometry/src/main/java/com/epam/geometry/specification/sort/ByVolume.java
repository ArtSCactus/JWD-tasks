package com.epam.geometry.specification.sort;

import com.epam.geometry.entity.Cube;
import com.epam.geometry.specification.SortSpecification;

import java.util.Comparator;

public class ByVolume extends SortSpecification implements Comparator<Cube> {
    @Override
    public int compare(Cube o1, Cube o2) {
        double o1Volume = calculator.calculateCubeVolume(o1);
        double o2Volume = calculator.calculateCubeVolume(o2);
        return Double.compare(o1Volume, o2Volume);
    }
}

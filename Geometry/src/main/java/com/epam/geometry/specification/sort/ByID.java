package com.epam.geometry.specification.sort;

import com.epam.geometry.entity.Cube;
import com.epam.geometry.specification.SortSpecification;

import java.util.Comparator;

public class ByID extends SortSpecification implements Comparator<Cube> {
    @Override
    public int compare(Cube o1, Cube o2) {
        return Long.compare(o1.getID(), o2.getID());
    }
}

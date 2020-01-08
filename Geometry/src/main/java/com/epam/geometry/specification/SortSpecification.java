package com.epam.geometry.specification;

import com.epam.geometry.entity.Cube;
import com.epam.geometry.util.CubeCalculator;

import java.util.Comparator;

public abstract class SortSpecification implements Comparator<Cube> {
protected final CubeCalculator calculator = new CubeCalculator();
}

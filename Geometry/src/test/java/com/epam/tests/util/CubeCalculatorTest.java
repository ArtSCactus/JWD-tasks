package com.epam.tests.util;


import com.epam.geometry.entity.Cube;
import com.epam.geometry.entity.Point;
import com.epam.geometry.idgenerator.IdGenerator;
import com.epam.geometry.util.CubeCalculator;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CubeCalculatorTest {
    private final CubeCalculator calculator = new CubeCalculator();
    private final double DELTA = 0.0000001;
    private Cube correctCubeExample;

    @Before
    public void loadDataPresets(){
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0, 0));
        points.add(new Point(1, 0, 0));
        points.add(new Point(1, 1, 0));
        points.add(new Point(0, 1, 0));
        points.add(new Point(0, 1, 1));
        points.add(new Point(0, 0, 1));
        points.add(new Point(1, 0, 1));
        points.add(new Point(1, 1, 1));
        correctCubeExample = new Cube(points);
    }

    @Test
    public void shouldReturnCorrectCubeEdgeLength() {
        final double trueResult = 1.0;
        double methodResult = calculator.calculateCubeEdge(correctCubeExample);
        Assert.assertEquals(trueResult, methodResult, DELTA);
    }

    @Test
    public void shouldReturnCorrectCubeVolume() {
        final double correctResult = 1;
        final double methodResult = calculator.calculateCubeVolume(correctCubeExample);
        Assert.assertEquals(correctResult, methodResult, DELTA);
    }

    @Test
    public void calculateVolumeRelation(){
        final double correctResult=1;
        final double methodResult = calculator.calculateCubeVolumeRelation(correctCubeExample, 0.5);
        Assert.assertEquals(correctResult, methodResult, DELTA);
    }

    @Test
    public void calculateSurfaceSquare(){
        final double correctResult=6;
        final double methodResult = calculator.calculateCubeSurfaceSquare(correctCubeExample);
        Assert.assertEquals(correctResult, methodResult, DELTA);
    }

    @AfterClass
    public static void endTest(){
        // setting IdGenerator to zero so as not to interfere with other tests
        IdGenerator.setZero();
    }
}

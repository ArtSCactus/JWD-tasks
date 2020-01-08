package com.epam.tests.util;

import com.epam.geometry.entity.Cube;
import com.epam.geometry.entity.Point;
import com.epam.geometry.idgenerator.IdGenerator;
import com.epam.geometry.util.CubeConstructor;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CubeConstructorTest {
    private final String filePath = "src/test/resources/construction test.txt";
    private Cube referenceCube;

    @Before
    public void loadCube() {
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0, 0));
        points.add(new Point(1, 0, 0));
        points.add(new Point(1, 1, 0));
        points.add(new Point(0, 1, 0));
        points.add(new Point(0, 1, 1));
        points.add(new Point(0, 0, 1));
        points.add(new Point(1, 0, 1));
        points.add(new Point(1, 1, 1));
        // resets id idgenerator to make cubes equals.
        IdGenerator.setZero();
        referenceCube = new Cube(points);
        IdGenerator.setZero();
    }

    @Test
    public void shouldSuccessfullyConstructListWithOneCube() {
        CubeConstructor constructor = new CubeConstructor();
        List<Cube> cubes = constructor.constructCubesFromFile(filePath);
        Assert.assertEquals(referenceCube, cubes.get(0));
    }


    @Test
    public void shouldRecognizeCube() {
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0, 0));
        points.add(new Point(1, 0, 0));
        points.add(new Point(1, 1, 0));
        points.add(new Point(0, 1, 0));
        points.add(new Point(0, 1, 1));
        points.add(new Point(0, 0, 1));
        points.add(new Point(1, 0, 1));
        points.add(new Point(1, 1, 1));
        Assert.assertTrue(new CubeConstructor().isCube(new Cube(points)));
        // setting IdGenerator to zero so as not to interfere with other tests
        IdGenerator.setZero();
    }

    @Test
    public void shouldNotRecognizeCube() {
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0, 0));
        points.add(new Point(5, 0, 0));
        points.add(new Point(1, 1, 0));
        points.add(new Point(0, 1, 0));
        points.add(new Point(0, 3, 1));
        points.add(new Point(0, 0, 1));
        points.add(new Point(1, 0, 1));
        points.add(new Point(1, 1, 1));
        Assert.assertFalse(new CubeConstructor().isCube(new Cube(points)));
    }

    @AfterClass
    public static void endTest() {
        // setting IdGenerator to zero so as not to interfere with other tests
        IdGenerator.setZero();
    }
}

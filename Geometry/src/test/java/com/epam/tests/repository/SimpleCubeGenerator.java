package com.epam.tests.repository;

import com.epam.geometry.entity.Cube;
import com.epam.geometry.entity.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Class, that generates very simple cubes.
 * Created to simplify correct cube generation in tests.
 */
public class SimpleCubeGenerator {
    /**
     * Generates an ascending sequence of simple cubes.
     * <p>
     * The generated cubes have extremely simple vertex coordinates (like [0.0,1.0,1.0] or [1.0, 0.0, 0.0]).
     * The faces of these cubes are parallel to the coordinate planes.
     * The points of these cubes differs by 1 and is generated in ascending order.
     *
     * @param amount amount of cubes
     * @return {@code List<Cube>} list with generated cubes
     */
    public List<Cube> generateSimpleCubes(int amount) {
        List<Cube> generatedCubes = new ArrayList<>();
        double number = 1;
        for (int index = 0; index < amount; index++, number++) {
            List<Point> points = new ArrayList<Point>();
            points.add(new Point(0d, 0d, 0d));
            points.add(new Point(number, 0d, 0d));
            points.add(new Point(number, number, 0d));
            points.add(new Point(0d, number, 0d));
            points.add(new Point(0d, number, number));
            points.add(new Point(0d, 0d, number));
            points.add(new Point(number, 0d, number));
            points.add(new Point(number, number, number));
            generatedCubes.add(new Cube(points));
        }
        return generatedCubes;
    }

    /**
     * Generates an ascending sequence of simple cubes.
     * This cube with different sizes in ascending order.
     *
     * @param amount amount of cubes
     * @return {@code List<Cube>} list with generated cubes
     */
    public List<Cube> generateSimpleCubes(int amount, boolean shuffleOrder) {
        List<Cube> generatedCubes = new ArrayList<>();
        double number = 1;
        for (int index = 0; index < amount; index++, number++) {
            List<Point> points = new ArrayList<Point>();
            points.add(new Point(0d, 0d, 0d));
            points.add(new Point(number, 0d, 0d));
            points.add(new Point(number, number, 0d));
            points.add(new Point(0d, number, 0d));
            points.add(new Point(0d, number, number));
            points.add(new Point(0d, 0d, number));
            points.add(new Point(number, 0d, number));
            points.add(new Point(number, number, number));
            generatedCubes.add(new Cube(points));
        }
        if (shuffleOrder) {
            shuffleOrder(generatedCubes);
        }
        return generatedCubes;
    }

    /**
     * Shuffles cube list with given Random parameter.
     *
     * @param cubesList list with cubes
     * @param rnd       random parameter to shuffle
     * @see Collections#shuffle(List, Random)
     */
    public void shuffleOrder(List<Cube> cubesList, Random rnd) {
        Collections.shuffle(cubesList, rnd);
    }

    /**
     * Shuffles order in given Cube list.
     *
     * @param cubesList list with cubes
     * @see Collections#shuffle(List)
     */
    public void shuffleOrder(List<Cube> cubesList) {
        Collections.shuffle(cubesList);
    }

}

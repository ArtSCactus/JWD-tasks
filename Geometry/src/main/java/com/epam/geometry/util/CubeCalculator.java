package com.epam.geometry.util;

import com.epam.geometry.entity.Cube;
import com.epam.geometry.entity.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CubeCalculator {
    private static final Logger LOGGER = LogManager.getLogger(CubeCalculator.class);

    public double calculateCubeEdge(Cube cube) {
        Point pointA = cube.getVertex(0);
        Point pointB = cube.getVertex(1);
        double differenceByX = pointB.getX() - pointA.getX();
        double differenceByY = pointB.getY() - pointA.getY();
        double differenceByZ = pointB.getZ() - pointA.getZ();
        LOGGER.info("Calculating cube edge...");
        return Math.sqrt(Math.pow(differenceByX, 2) + Math.pow(differenceByY, 2) + Math.pow(differenceByZ, 2));
    }

    public double calculatePointDistance(Point pointA, Point pointB) {
        double differenceByX = pointB.getX() - pointA.getX();
        double differenceByY = pointB.getY() - pointA.getY();
        double differenceByZ = pointB.getZ() - pointA.getZ();
        LOGGER.info("Calculating cube edge...");
        return Math.sqrt(Math.pow(differenceByX, 2) + Math.pow(differenceByY, 2) + Math.pow(differenceByZ, 2));
    }

    public double calculateCubeVolume(Cube cube) {
        LOGGER.info("Calculating cube volume...");
        return Math.pow(calculateCubeEdge(cube), 3);
    }

    public double calculateCubeSurfaceSquare(Cube cube) {
        LOGGER.info("Calculating cube surface square...");
        return 6 * Math.pow(calculateCubeEdge(cube), 2);
    }

    /**
     * Calculates volume relation of cube with secant plane.
     * <p>
     * This method is calculating only the simplest case, which suppose, that
     * means that secant plane is parallel to cube base and one of coordinate plane.
     *
     * @param cube   cube, that parallel one of the coordinate plane
     * @param height height, on which secant plane (which is parallel to cube base) placed
     *               regarding to cube base.
     * @return (double) relation of volumes in cube, that divided by secant plane.
     */
    public double calculateCubeVolumeRelation(Cube cube, double height) {
        double edgeLength = calculateCubeEdge(cube);
        LOGGER.info("Calculating cube volume relation");
        return height / (edgeLength - height);
    }

    public boolean isCubeBaseOnCoordinatePlane(Cube cube) {
        return isPointsOnXPlane(cube.getVertex(0), cube.getVertex(1), cube.getVertex(2),
                cube.getVertex(3)) ||
                isPointsOnYPlane(cube.getVertex(0), cube.getVertex(1), cube.getVertex(2),
                        cube.getVertex(3)) ||
                isPointsOnZPlane(cube.getVertex(0), cube.getVertex(1), cube.getVertex(2),
                        cube.getVertex(3));
    }

    private boolean isPointsOnXPlane(Point... points) {
        for (Point point : points) {
            if (point.getX() != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isPointsOnYPlane(Point... points) {
        for (Point point : points) {
            if (point.getY() != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isPointsOnZPlane(Point... points) {
        for (Point point : points) {
            if (point.getZ() != 0) {
                return false;
            }
        }
        return true;
    }
}

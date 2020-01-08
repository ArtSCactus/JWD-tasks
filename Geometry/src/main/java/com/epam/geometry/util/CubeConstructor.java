package com.epam.geometry.util;

import com.epam.geometry.entity.Cube;
import com.epam.geometry.entity.Point;
import com.epam.geometry.exception.FileAccessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CubeConstructor {
    private static final Logger LOGGER = LogManager.getLogger(CubeConstructor.class);

    public Optional<Cube> constructCube(List<Point> points) {
        if (points == null) {
            LOGGER.error("Cannot run single cube construction. CAUSE: points is null.");
            return Optional.empty();
        } else {
            if (points.size() > 8) {
                LOGGER.warn("Points size is more than 8. Was taken only first 8 points.");
            }else{
                LOGGER.info("Constructing single cube.");
            }
        }
        List<Point> cubePoints = new ArrayList<Point>();
        for (int index = 0; index < 8; index++) {
            cubePoints.add(points.get(index));
        }
        Cube cube = new Cube(cubePoints);
        if (!isCube(cube)) {
            cube = null;
            return Optional.ofNullable(cube);
        } else {
            return Optional.ofNullable(cube);
        }
    }

    public List<Cube> constructCubes(List<Point> points) {
        if (points == null) {
            LOGGER.error("Cannot run cubes construction. CAUSE: points is null. Returning null.");
        } else {
            LOGGER.info("Constructing cube objects in process.");
        }
        CubeConstructor constructor = new CubeConstructor();
        List<Point> currentCubePoints = new ArrayList<>();
        List<Cube> constructedCubes = new ArrayList<>();
        for (int index = 0; index < points.size(); index++) {
            currentCubePoints.add(points.get(index));
            if (index % 8 == 0) {
                Optional<Cube> cube = constructor.constructCube(currentCubePoints);
                if (cube.isPresent()) {
                    constructedCubes.add(cube.get());
                }
                currentCubePoints = new ArrayList<>();
            }
        }
        return constructedCubes;
    }

    public boolean isCube(Cube cube) {
        CubeCalculator calculator = new CubeCalculator();
        double referenceEdgeLength = calculator.calculateCubeEdge(cube);
        for (int index = 0; index < 7; index++) { // 7 here is amount of cube vertexes, excluding №0 vertex.
            if (calculator.calculatePointDistance(cube.getVertex(index), cube.getVertex(index+1)) != referenceEdgeLength) {
                return false;
            }
        }
        return true;
    }

    public List<Cube> constructCubesFromFile(String filePath){
        DataLoader reader = new DataLoader();
        PointConverter pointConverter = new PointConverter();
        List<Cube> cubes = new ArrayList<>();
        List<String> rows;
        try {
            rows = reader.readFile(filePath);
            LOGGER.debug("File was successfully rad.");
        }catch (FileAccessException e){
            LOGGER.error("Cannot open or read file by path: "+filePath+" Stopping process and returning null.");
            return null;
        }
        for (String row : rows){
            Optional<Cube> cube = constructCube(pointConverter.stringToPoints(row));
            if (cube.isPresent()){
                LOGGER.debug("Cube successfully built. Adding to list.");
                cubes.add(cube.get());
            } else {
                LOGGER.debug("Cube wasn't built.");
            }
        }
        return cubes;
    }
}

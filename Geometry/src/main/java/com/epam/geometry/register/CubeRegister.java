package com.epam.geometry.register;

import com.epam.geometry.entity.Cube;
import com.epam.geometry.util.CubeCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CubeRegister implements Observer<Cube> {
    private static Logger LOGGER = LogManager.getLogger(CubeRegister.class);
    private static CubeRegister register;
    private final CubeCalculator CALCULATOR = new CubeCalculator();
    private Map<Long, Double> surfaceSquares;
    private Map<Long, Double> volumes;

    private CubeRegister() {
        surfaceSquares = new HashMap<>();
        volumes = new HashMap<>();
    }

    public Map<Long, Double> getSurfaceSquares() {
        return surfaceSquares;
    }

    public Map<Long, Double> getVolumes() {
        return volumes;
    }

    public double getSquare(long id){
        return surfaceSquares.get(id);
    }

    public double getVolume(long id){
        return volumes.get(id);
    }

    public static CubeRegister getInstance() {
        if (register != null) {
            return register;
        } else {
            register = new CubeRegister();
            LOGGER.info("Register has been successfully created.");
            return register;
        }
    }

    @Override
    public void update(Cube cube) {
        double newVolume =CALCULATOR.calculateCubeVolume(cube);
        double newSquare =CALCULATOR.calculateCubeSurfaceSquare(cube);
        surfaceSquares.put(cube.getID(), newSquare);
        volumes.put(cube.getID(), newVolume);
        LOGGER.debug("At cube ID["+cube.getID()+"] was set new volume: "+newSquare
        +" and new surface square: "+newSquare);
        LOGGER.info("Cube register has been updated.");
    }
}

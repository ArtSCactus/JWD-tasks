package com.epam.geometry.specification;

import com.epam.geometry.entity.Cube;
import com.epam.geometry.repository.Specification;
import com.epam.geometry.util.CubeCalculator;

public class SearchSpecification {

    private static final CubeCalculator CALCULATOR = new CubeCalculator();

    public static Specification<Cube> searchById(long id) {
        return new Specification<Cube>() {
            @Override
            public boolean specified(Cube obj) {
                return obj.getID() == id;
            }
        };
    }

    public static Specification<Cube> searchByIdInRange(long min, long max) {
        return new Specification<Cube>() {
            @Override
            public boolean specified(Cube obj) {
                return obj.getID() >= min & obj.getID() <= max;
            }
        };
    }

    public static Specification<Cube> searchBySurfaceSquare(double value) {
        return new Specification<Cube>() {
            @Override
            public boolean specified(Cube obj) {
                return CALCULATOR.calculateCubeSurfaceSquare(obj) == value;
            }
        };
    }

    public static Specification<Cube> searchBySurfaceSquareInRange(double min, double max) {
        return new Specification<Cube>() {
            @Override
            public boolean specified(Cube obj) {
                double cubeSurfaceSquare = CALCULATOR.calculateCubeSurfaceSquare(obj);
                return cubeSurfaceSquare >= min & cubeSurfaceSquare <= max;
            }
        };
    }

    public static Specification<Cube> searchByVolume(double volume) {
        return new Specification<Cube>() {
            @Override
            public boolean specified(Cube obj) {
                return CALCULATOR.calculateCubeVolume(obj) == volume;
            }
        };
    }

    public static Specification<Cube> searchByVolumeInRange(double min, double max) {
        return new Specification<Cube>() {
            @Override
            public boolean specified(Cube obj) {
                double cubeVolume = CALCULATOR.calculateCubeVolume(obj);
                return cubeVolume >= min & cubeVolume <= max;
            }
        };
    }
}

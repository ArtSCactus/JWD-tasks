package com.epam.tests.register;

import com.epam.geometry.entity.Point;
import com.epam.geometry.idgenerator.IdGenerator;
import com.epam.geometry.register.CubeRegister;
import com.epam.geometry.register.ObservableCube;
import com.epam.geometry.util.CubeCalculator;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

public class RegisterTest {
    static CubeRegister register;
    private final double delta = 0.00000001;
    public static final ObservableCube TEST_OBJECT = new ObservableCube(
            Arrays.asList(
                    new Point(0, 0, 0),
                    new Point(1, 0, 0),
                    new Point(1, 1, 0),
                    new Point(0, 1, 0),
                    new Point(0, 1, 1),
                    new Point(0, 0, 1),
                    new Point(1, 0, 1),
                    new Point(1, 1, 1)
            )
    );


    @BeforeClass
    public static void init() {
        register = CubeRegister.getInstance();
        TEST_OBJECT.attach(register);
    }

    @Test
    public void shouldUpdateRegisterSquare() {
        TEST_OBJECT.setVertexes(Arrays.asList(
                new Point(0, 0, 0),
                new Point(2, 0, 0),
                new Point(2, 2, 0),
                new Point(0, 2, 0),
                new Point(0, 2, 2),
                new Point(0, 0, 2),
                new Point(2, 0, 2),
                new Point(2, 2, 2)
        ));
        double correctResult = new CubeCalculator().calculateCubeSurfaceSquare(TEST_OBJECT);
        double methodResult = register.getSquare(TEST_OBJECT.getID());
        Assert.assertEquals(correctResult, methodResult, delta);
    }

    @Test
    public void shouldUpdateRegisterVolume() {
        TEST_OBJECT.setVertexes(Arrays.asList(
                new Point(0, 0, 0),
                new Point(3, 0, 0),
                new Point(3, 3, 0),
                new Point(0, 3, 0),
                new Point(0, 3, 3),
                new Point(0, 0, 3),
                new Point(3, 0, 3),
                new Point(3, 3, 3)
        ));
        double correctResult = new CubeCalculator().calculateCubeVolume(TEST_OBJECT);
        double methodResult = register.getVolume(TEST_OBJECT.getID());
        Assert.assertEquals(correctResult, methodResult, delta);
    }
    @AfterClass
    public static void endTest() {
        // setting IdGenerator to zero so as not to interfere with other tests
        IdGenerator.setZero();
    }
}

package com.epam.tests.util;

import com.epam.geometry.entity.Point;
import com.epam.geometry.util.PointConverter;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PointConverterTest {
  private final  PointConverter converter = new PointConverter();

    @Test
    public void shouldSuccessfullyConvertToPointsTest() {
        String numbersRow = "1.0 2.0 1.0";
        List<Point> correctResult = new ArrayList<>();
        correctResult.add(new Point(1d,2d,1d));
        List<Point> methodResult = converter.stringToPoints(numbersRow);
        Assert.assertEquals(correctResult, methodResult);
    }

}

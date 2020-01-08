package com.epam.geometry.util;

import com.epam.geometry.entity.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PointConverter {
    private final Pattern POINT_TEMPLATE = Pattern.compile("(\\s*(\\d+\\.\\d+|\\d+)\\s*){3}");

    public List<Point> stringToPoints(String row) {
        List<Point> points = new ArrayList<>();
        Matcher matcher = POINT_TEMPLATE.matcher(row);
        while (matcher.find()) {
            String currentPoint = matcher.group();
            List<Double> xyzCoordinates = stringToDoubles(currentPoint);
            points.add(new Point(xyzCoordinates.get(0),xyzCoordinates.get(1),
                    xyzCoordinates.get(2)));
        }
        return points;
    }

    private List<Double> stringToDoubles(String point) {
        List<String> stringNumbers = Arrays.asList(point.split("\\s"));
        List<Double> numbers = new ArrayList<>();
        for (int index=0; index<stringNumbers.size(); index++){
            numbers.add(Double.parseDouble(stringNumbers.get(index)));
        }
        return numbers;
    }

}

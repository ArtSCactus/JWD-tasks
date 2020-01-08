package com.epam.xml.parsers;

import java.io.File;
import java.util.List;

public interface Parser<T> {
    List<T> parse(String path);

    List<T> parse(File xmlFile);
}

package com.epam.geometry.repository;

import java.util.Comparator;
import java.util.List;

public interface Repository<T> {
    void add(T obj);
    void remove(T obj);
    List<T> query(Specification<T> specification);
    List<T> sort(Comparator<T> comparator);
}

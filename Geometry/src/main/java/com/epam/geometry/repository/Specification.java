package com.epam.geometry.repository;

public interface Specification<T> {
    boolean specified(T obj);
}

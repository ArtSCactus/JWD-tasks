package com.epam.geometry.repository;

import com.epam.geometry.entity.Cube;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CubeRepository implements Repository<Cube>{
private final List<Cube> CUBES = new ArrayList<>();

    public List<Cube> getCUBES() {
        return CUBES;
    }

    @Override
    public void add(Cube obj) {
        CUBES.add(obj);
    }

    public void addAll(List<Cube> cubes){
        CUBES.addAll(cubes);
    }

    @Override
    public void remove(Cube obj) {
        CUBES.remove(obj);
    }

    public void clear(){
        CUBES.clear();
    }

    @Override
    public List<Cube> query(Specification<Cube> specification) {
        List<Cube> queryList = new ArrayList<>();
        for (Cube cube : CUBES){
            if (specification.specified(cube)){
                queryList.add(cube);
            }
        }
        return queryList;
    }

    @Override
    public List<Cube> sort(Comparator<Cube> comparator) {
       CUBES.sort(comparator);
       return CUBES;
    }

}

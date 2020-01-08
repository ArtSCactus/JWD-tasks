package com.epam.geometry.entity;

import com.epam.geometry.idgenerator.IdGenerator;

import java.util.List;

public class Cube {
    private List<Point> vertexes;
    private final long ID;

    public Cube(List<Point> vertexes) {
        this.vertexes = vertexes;
        ID = IdGenerator.getId();
    }

    public List<Point> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Point> vertexes) {
        this.vertexes = vertexes;
    }

    public Point getVertex(int index) {
        return vertexes.get(index);
    }

    public void addVertex(Point point) {
        if (vertexes.size() == 6 | vertexes.size() > 6) {
            return;
        }
        vertexes.add(point);
    }

    public long getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Cube)) {
            return false;
        } else {
            if (((Cube) o).getVertexes().size() != this.vertexes.size()) {
                return false;
            }
        }
        if (this.ID != ((Cube) o).getID()) {
            return false;
        }
        for (int index = 0; index < vertexes.size(); index++) {
            if (!this.vertexes.get(index).equals(((Cube) o).getVertex(index))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        for(Point vertex : vertexes){
            hash = 31*hash+vertex.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        return "Cube{" +
                "vertexes=" + vertexes.toString() +
                ", ID=" + ID +
                '}';
    }
}

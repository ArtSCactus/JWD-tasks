package com.epam.geometry.register;

import com.epam.geometry.entity.Cube;
import com.epam.geometry.entity.Point;

import java.util.ArrayList;
import java.util.List;

public class ObservableCube extends Cube implements Observable<Observer<Cube>>{
    private List<Observer<Cube>> observers = new ArrayList<>();

    public ObservableCube(List<Point> vertexes){
        super(vertexes);
    }

    @Override
    public void attach(Observer<Cube> observer) {
        observers.add(observer);
        observer.update(this);
    }

    @Override
    public void detach(Observer<Cube> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update(this);
        }
    }

    @Override
    public void setVertexes(List<Point> vertexes) {
        super.setVertexes(vertexes);
        notifyObservers();
    }

}

package com.epam.geometry.register;

public interface Observable<T extends Observer> {
    void attach(T observer);
    void detach(T observer);
    void notifyObservers();
}

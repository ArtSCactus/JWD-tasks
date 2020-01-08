package entity;

public interface Observable<T> {
    void attach(T observer);
    void detach(T observer);
    void notifyObservers();
}

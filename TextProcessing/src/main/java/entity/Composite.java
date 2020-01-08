package entity;

import entity.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Composite implements Component {
    private List<Component> components = new ArrayList<>();
    private ComponentType type;

    public Composite(ComponentType type) {
        this.type = type;
    }

    public Composite() {

    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }

    public Object getChild(int index) {
        return components.get(index);
    }

    @Override
    public int getChildNodeListSize() {
        return components.size();
    }

    @Override
    public List<Component> getChildNodes() {
        return components;
    }

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Composite)) return false;
        Composite composite = (Composite) o;
        return Objects.equals(components, composite.components) &&
                getType() == composite.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(components, getType());
    }

    @Override
    public String toString() {
        return "Composite{" +
                "components=" + components +
                ", type=" + type +
                '}';
    }
}

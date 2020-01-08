package entity;

import java.util.List;

public interface Component {
    void addComponent(Component component);

    void removeComponent(Component component);

    List<Component> getChildNodes();

    int getChildNodeListSize();

    Object getChild(int index);

    ComponentType getType();
}

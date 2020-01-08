package entity;

import java.util.List;

public class Leaf implements Component {
    private String value;
    private ComponentType type;
    private String sourceExpression;

    public Leaf(String value) {
        this.value = value;
        type = ComponentType.WORD;
    }

    public Leaf(String value, ComponentType type){
        this.value = value;
        this.type = type;
    }

    public Leaf(String value, ComponentType type, String sourceExpression) {
        this.value = value;
        this.type = type;
        this.sourceExpression = sourceExpression;
    }

    public ComponentType getType() {
        return type;
    }

    public String getSourceExpression() {
        return sourceExpression;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int countAmountOfEntries(char orderSymbol){
        int count = 0;
        for(int i = 0; i < value.length(); i++){
            if(value.charAt(i)==orderSymbol){
                count++;
            }
        }
        return count;
    }

    public void setSourceExpression(String sourceExpression) {
        this.sourceExpression = sourceExpression;
    }

    @Override
    public List<Component> getChildNodes() {
        throw new UnsupportedOperationException("No child nodes in leaf");
    }

    @Override
    public int getChildNodeListSize() {
        return 0;
    }

    @Override
    public void addComponent(Component component) {
        throw new UnsupportedOperationException("No child nodes in leaf ");
    }

    @Override
    public void removeComponent(Component component) {
        throw new UnsupportedOperationException("No child nodes in leaf ");
    }

    public String getValue() {
        return value;
    }

    @Override
    public Object getChild(int index) {
        throw new UnsupportedOperationException("No child nodes in leaf ");
    }
}

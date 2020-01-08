package representation;

import entity.Component;
import entity.Leaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ExpressionHandler;

import java.util.List;

public class TextBuilder {
    private static final Logger LOGGER = LogManager.getLogger(TextBuilder.class);

    public String represent(Component component) {
        return buildText(component);
    }

    private String buildText(Component component) {
        if (component == null) {
            LOGGER.error("Null component was found while text building.");
            throw new NullPointerException("Component cannot be null");
        }
        StringBuilder stringBuilder = new StringBuilder();
        switch (component.getType()) {
            case TEXT: {
                List<Component> childComponents = component.getChildNodes();
                childComponents.forEach(o -> {
                    String paragraph = represent(o);
                    stringBuilder.append(paragraph);
                    stringBuilder.append("\n\t");
                });
                break;
            }
            case EXPRESSION: {
                Leaf lexeme = (Leaf) component;
                String expression = lexeme.getValue();
                ExpressionHandler solver = new ExpressionHandler();
                String convertExpression = solver.solveExpression(expression);
                stringBuilder.append(convertExpression + " ");
                break;
            }

            case WORD: {
                Leaf lexeme = (Leaf) component;
                String word = lexeme.getValue();
                stringBuilder.append(word + " ");
                break;
            }

            default: {
                List<Component> childComponents = component.getChildNodes();
                childComponents.forEach(o -> {
                    String paragraph = represent(o);
                    stringBuilder.append(paragraph);
                });
            }
        }
        LOGGER.info("Finished representing a component: "+component.getType().name());
        return stringBuilder.toString();
    }
}

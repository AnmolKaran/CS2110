package cs2110;

/**
 * Thrown when a math expression is expected but a malformed
 * one is encountered when evaluating.
 * This is a checked exception.
 */
public class MalformedExpressionException extends Exception {

    /**
     * Constructs a MalformedExpressionException with the specified detail message.
     */
    public MalformedExpressionException(String message) {
        super(message);
    }
}

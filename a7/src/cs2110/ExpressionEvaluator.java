package cs2110;
import java.util.Scanner;

public class ExpressionEvaluator {

     // TODO: Update these specs as you build out the functionality of the `evaluate()` method.
    /**
     * Evaluates the given well-formed mathematical expression `expr` and returns its value.
     * Currently, the `evaluate()` method supports:
     * - Non-negative int literals, including multiple digits.
     * - Addition
     * - Subtraction
     * - Multiplication
     * - Parentheses
     * - Unary negation
     * - Malformed expression exceptions with invalid expressions, including when:
     *      - There is an empty string
     *      - The expression has characters other than digits, operators, parenthesis, and whitespace.
     *      - There are mismatched parenthesis
     *      - There is an operator in the place of an operand
     *      - The expression ends with an operator instead of an operand.
     */
    public static int evaluate(String expr) throws MalformedExpressionException {

        if (expr == null || expr.isEmpty()){
            throw new MalformedExpressionException("Expression cannot be empty.");
        }

        Stack<Integer> operands = new LinkedStack<>();
        Stack<Character> operators = new LinkedStack<>(); // invariant: contains only '(', '+', and '*'

        boolean expectingOperator = false; // in infix notation, the first operand comes before an operator
        boolean wasClosingParenthesis = false;
        char[] exprArray = expr.toCharArray();
        for (int i = 0; i < expr.length(); i++) {

            char c = exprArray[i];
            if (Character.isWhitespace(c)){
                continue;
            }
            if (c == '(') {
                if (expectingOperator){
                    while (!operators.isEmpty() && operators.peek() == '*') {
                        oneStepSimplify(operands, operators);
                    }
                    operators.push('*');
                }
                operators.push('(');
                wasClosingParenthesis = false;
            } else if (c == '*') {
                if (!expectingOperator){
                    throw new MalformedExpressionException("'*' must follow an operand, not an operator");
                }
                while (!operators.isEmpty() && operators.peek() == '*') {
                    oneStepSimplify(operands, operators);
                }
                operators.push('*');
                expectingOperator = false;
                wasClosingParenthesis = false;
            } else if (c == '+') {
                if(!expectingOperator){
                    throw new MalformedExpressionException("'+' must follow an operand, not an operator");
                }
                while (!operators.isEmpty() && (operators.peek() == '*'
                        || operators.peek() == '+' || operators.peek() == '-')) {
                    oneStepSimplify(operands, operators);
                }
                operators.push('+');
                expectingOperator = false;
                wasClosingParenthesis = false;
            } else if (c == '-') {
                if (expectingOperator){
                    while (!operators.isEmpty() && (operators.peek() == '*'
                            || operators.peek() == '+'
                            || operators.peek() == '-')){
                        oneStepSimplify(operands, operators);
                    }
                    operators.push('-');
                    expectingOperator = false;
                    wasClosingParenthesis = false;
                } else{
                    operators.push('~'); // we are using ~ to represent unary negation and distinguish from binary subtraction
                    wasClosingParenthesis = false;
                }

            } else if (c == ')') {
                if (!expectingOperator){
                    throw new MalformedExpressionException("')' must follow an operand, not an operator");
                }
                if (operators.isEmpty()){
                    throw new MalformedExpressionException("mismatched parentheses, extra ')'");
                }
                while (operators.peek() != '(') {
                    oneStepSimplify(operands, operators);
                    if (operators.isEmpty()){
                        throw new MalformedExpressionException("mismatched parentheses, extra ')' found (internal error)");
                    }

                    //assert !operators.isEmpty() : "mismatched parentheses, extra ')'";
                }
                operators.pop(); // remove '('
                expectingOperator = true;
                wasClosingParenthesis = true;
            } else { // c is a digit
                if (!( c >= '0' && c <= '9')){
                    throw new MalformedExpressionException("Invalid character '" + c + "' found in expression.");
                }

                if (expectingOperator){
                    if (!wasClosingParenthesis){
                        throw new MalformedExpressionException("A number/digit cannot follow another number without an operator.");
                    }

                    while (!operators.isEmpty() && operators.peek() == '*') {
                        oneStepSimplify(operands, operators);
                    }
                    operators.push('*');
                    expectingOperator = false;
                }
                //assert c >= '0' && c <= '9' : "expression contains an illegal character";
                //assert !expectingOperator : "digits cannot follow an operand, our evaluator only supports single-digit inputs";
                int curr = i;
                int num = 0;
                while (curr < exprArray.length && exprArray[curr]>= '0' && exprArray[curr]<= '9'){
                    int digit = exprArray[curr] - '0';
                    num = num*10 + digit;
                    curr ++;
                }
                operands.push(num); // convert c to an int and auto-box
                expectingOperator = true;
                i = curr -1;
            }

        }

        if (!expectingOperator){
            throw new MalformedExpressionException("expression must end with an operand, not an operator.");
        }

        //assert expectingOperator : "expression must end with an operand, not an operator";
        while (!operators.isEmpty()) {
            if (operators.peek() == '(') {
                throw new MalformedExpressionException("Mismatched parentheses: extra '(' found.");
            }
            oneStepSimplify(operands, operators);
        }

        // If the above assertions pass, the operands stack should include exactly one value,
        // the return value. We'll include two assertions to verify this as a sanity check.
        if (operands.isEmpty()){
            throw new MalformedExpressionException("Internal error, stack is empty after final simplification.");
        }
        //assert !operands.isEmpty();
        int result = operands.pop();
        if (!operands.isEmpty()) {
            throw new MalformedExpressionException("Too many operands remain after simplification. Expression is malformed.");
        }
        //assert operands.isEmpty();
        return result;
    }

    /**
     * Helper method that partially simplifies the expression by `pop()`ping one operator from the
     * `operators` stack, `pop()`ping its two operands from the `operands` stack, evaluating the
     * operator, and then `push()`ing its result onto the `operands` stack. Requires that
     * `operators.peek()` is '+' or '*' or '-' or '~'.
     */
    private static void oneStepSimplify(Stack<Integer> operands, Stack<Character> operators) {
        char op = operators.pop();
        assert op == '+' || op == '*'|| op == '-' || op == '~';

        if (op == '~'){ //since its unary operation
            int o1 = operands.pop();
            operands.push(-1* o1);
        }else {
            int o2 = operands.pop(); // second operand is higher on stack
            int o1 = operands.pop();
            if (op == '+') {
                operands.push(o1 + o2);
            } else if (op == '-') {
                operands.push(o1 - o2);
            } else { // op == '*'
                operands.push(o1 * o2);
            }
        }
    }


    /**
     * A very basic calculator application.
     */
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            while (true) { // repeat indefinitely
                System.out.print("Enter an expression, or enter \"q\" to quit: ");
                String expr = in.nextLine();
                if (expr.equals("q")) {
                    break; // exit loop
                }
                try {
                    System.out.println("= " + evaluate(expr));
                }catch(MalformedExpressionException e ){
                    System.out.println(e.getMessage());
                }

            }
        }
    }
}

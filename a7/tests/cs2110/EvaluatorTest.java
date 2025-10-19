package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EvaluatorTest {

    @DisplayName("WHEN we have an expression containing only a single digit, THEN that digit "
            + "is returned.")
    @Test
    public void testDigit() throws MalformedExpressionException {
        assertEquals(0, ExpressionEvaluator.evaluate("0"));

    }

    @DisplayName("WHEN we have an expression containing only a single digit in "
            + "parentheses, THEN that digit is returned.")
    @Test
    public void testParenthesizedDigit() throws MalformedExpressionException {
        assertEquals(0, ExpressionEvaluator.evaluate("(0)"));
        assertEquals(4, ExpressionEvaluator.evaluate("((4))"));
    }

    @DisplayName("WHEN we have an expression that has one addition operation applied to two "
            + "single-digit operands, THEN the correct result is returned.")
    @Test
    public void testOneAddition() throws MalformedExpressionException {
        assertEquals(3, ExpressionEvaluator.evaluate("1+2"));

    }

    @DisplayName("WHEN we have  an expression containing one multiplication operation applied to "
            + "two single-digit operands, THEN the correct result is returned.")
    @Test
    public void testOneMultiplication() throws MalformedExpressionException {
        assertEquals(2, ExpressionEvaluator.evaluate("1*2"));
    }

    @DisplayName("WHEN we have an expression containing one addition operation applied to two "
            + "single-digit operands with additional parentheses, THEN the correct result is "
            + "returned.")
    @Test
    public void testOneOperatorParentheses() throws MalformedExpressionException {
        assertEquals(3, ExpressionEvaluator.evaluate("(1+2)"));
        assertEquals(3, ExpressionEvaluator.evaluate("(1)+2"));
        assertEquals(3, ExpressionEvaluator.evaluate("1+(2)"));
        assertEquals(3, ExpressionEvaluator.evaluate("(1)+(2)"));
        assertEquals(3, ExpressionEvaluator.evaluate("((1)+(2))"));
    }

    @DisplayName("WHEN an expression contains multiple of the same operator, THEN "
            + "it is correctly evaluated")
    @Test
    public void testOneOperatorMultipleTimes() throws MalformedExpressionException {
        assertEquals(6, ExpressionEvaluator.evaluate("1+2+3"));

        assertEquals(180, ExpressionEvaluator.evaluate("5*6*2*3"));
    }

    @DisplayName("WHEN an expression contains both addition and multiplication but no "
            + "parentheses, THEN the order of operations is followed.")
    @Test
    public void testBothOperators() throws MalformedExpressionException {
        assertEquals(7, ExpressionEvaluator.evaluate("1+2*3"));
        assertEquals(5, ExpressionEvaluator.evaluate("1*2+3"));
        assertEquals(11 , ExpressionEvaluator.evaluate("1+2*3+4"));
        assertEquals(14, ExpressionEvaluator.evaluate("1*2+3*4"));
        assertEquals(25, ExpressionEvaluator.evaluate("1+2*3*4"));
        assertEquals(10, ExpressionEvaluator.evaluate("1*2*3+4"));
    }

    @DisplayName("WHEN an expression has both addition and multiplication and "
            +"non-nested parentheses, THEN the order of operations is followed.")
    @Test
    public void testBothOperatorsParentheses() throws MalformedExpressionException {
        assertEquals(14, ExpressionEvaluator.evaluate("2+(3*4)"));
        assertEquals(20, ExpressionEvaluator.evaluate("(2+3)*4"));
        assertEquals(45, ExpressionEvaluator.evaluate("(2+3)*(4+5)"));
        assertEquals(70, ExpressionEvaluator.evaluate("2*(3+4)*5"));
    }

    @DisplayName("WHEN an expression has both addition and multiplication and "
            + "nested parentheses, THEN the order of operations is followed.")
    @Test
    public void testBothOperatorsNestedParentheses() throws MalformedExpressionException {
        assertEquals(94, ExpressionEvaluator.evaluate("2*(3+4*(5+6))"));
    }



    @DisplayName("WHEN we have an expression containing only a multi-digit operand, " +"THEN that number is returned.")
    @Test
    public void testMultiDigit() throws MalformedExpressionException {
        assertEquals(10, ExpressionEvaluator.evaluate("10"));
        assertEquals(12345, ExpressionEvaluator.evaluate("12345"));
    }

    @DisplayName("WHEN we have an expression containing a multi-digit operand within "
            +"parentheses, THEN that number is returned.")
    @Test
    public void testParenthesizedMultiDigit() throws MalformedExpressionException {
        assertEquals(55, ExpressionEvaluator.evaluate("(55)"));
        assertEquals(101, ExpressionEvaluator.evaluate("((101))"));
    }

    @DisplayName("WHEN we have expressions with multi-digit operands and addition, "
            + "THEN the correct result is returned.")
    @Test
    public void testMultiDigitAddition() throws MalformedExpressionException {
        assertEquals(123, ExpressionEvaluator.evaluate("100+23"));

        assertEquals(11, ExpressionEvaluator.evaluate("1+10"));
    }

    @DisplayName("WHEN we have expressions with multi-digit operands and multiplication, "
            + "THEN the correct result is returned.")
    @Test
    public void testMultiDigitMultiplication() throws MalformedExpressionException {
        assertEquals(2000, ExpressionEvaluator.evaluate("40*50"));
        assertEquals(250, ExpressionEvaluator.evaluate("50*5"));
        assertEquals(6000, ExpressionEvaluator.evaluate("10*20*30"));
    }

    @DisplayName("WHEN we have expressions with mixed operands respecting order of operations "
            +"THEN the correct result is returned.")
    @Test
    public void testMultiDigitBefore() throws MalformedExpressionException {
        assertEquals(52, ExpressionEvaluator.evaluate("10+7*6"));
        assertEquals(106, ExpressionEvaluator.evaluate("10*6+46"));
        assertEquals(155, ExpressionEvaluator.evaluate("10+5*25+20"));
    }

    @DisplayName("WHEN we have expressions with multi-digit operands and parentheses, "
            + "THEN the correct result is returned.")
    @Test
    public void testMultiDigitParentheses() throws MalformedExpressionException {
        assertEquals(600, ExpressionEvaluator.evaluate("(10+20)*20"));
        assertEquals(1400, ExpressionEvaluator.evaluate("10*(100+(2*10)+20)"));
    }


    @DisplayName("WHEN expression is empty or only whitespace, THEN throws MalformedExpressionException")
    @Test
    public void testEmptyOrWhitespace() { //not sure if it should throw if there is just whitespace
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate(""));
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate(" "));

    }

    @DisplayName("WHEN expression contains invalid characters, THEN throws MalformedExpressionException")
    @Test
    public void testInvalidCharacters() {
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("3$4"));
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("a+b"));
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("5+2_1"));
    }

    @DisplayName("WHEN extra closing parenthesis ')' is there, THEN throws MalformedExpressionException")
    @Test
    public void testExtraClosingParenthesis() {
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("3+4)"));
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("(3+4))"));
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate(")(3+4)"));
    }

    @DisplayName("WHEN extra opening parenthesis '(' is there, THEN throws MalformedExpressionException")
    @Test
    public void testExtraOpeningParenthesis() {
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("(3+4"));
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("((3+4)"));
    }

    @DisplayName("WHEN an operator is there where an operand is expected, THEN throws MalformedExpressionException")
    @Test
    public void testOperatorMissingOperand() {
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("+6"));
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("3*(+2)"));

    }

    @DisplayName("WHEN the expression ends with an operator, THEN throws MalformedExpressionException")
    @Test
    public void testEndsWithOperator() {
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("3+"));

        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("(4+5)*"));
    }



    @DisplayName("WHEN expression contains  whitespace, THEN result is correct.")
    @Test
    public void testValidWhitespace() throws MalformedExpressionException {
        assertEquals(7, ExpressionEvaluator.evaluate(" 3+4 "));
        assertEquals(7, ExpressionEvaluator.evaluate("3+ 4"));
        assertEquals(370, ExpressionEvaluator.evaluate("120 * 3 + 10"));
        assertEquals(94, ExpressionEvaluator.evaluate("2 * (3 + 4 * (5 + 6))"));
    }


    @DisplayName("WHEN whitespace is there between digits of a number, THEN throws MalformedExpressionException.")
    @Test
    public void testWhitespaceBetweenDigits() {
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("1 0"));
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("12 + 3 4"));
    }




    @DisplayName("WHEN we evaluate an expression that has a subtraction operation applied to two "
            + "single-digit operands, THEN the correct result is returned.")
    @Test
    public void testOneSubtraction() throws MalformedExpressionException {
        assertEquals(2, ExpressionEvaluator.evaluate("5-3"));

    }

    @DisplayName("WHEN an expression has multiple subtractions, THEN it is correctly evaluated")
    @Test
    public void testMultipleSubtractions() throws MalformedExpressionException {
        assertEquals(7, ExpressionEvaluator.evaluate("10-2-1"));
    }




    @DisplayName("WHEN an expression has both addition and subtraction, THEN the precedence rule is followed.")
    @Test
    public void testMixedAdditionAndSubtraction() throws MalformedExpressionException {

        assertEquals(5, ExpressionEvaluator.evaluate("5-2+3-1"));
    }

    @DisplayName("WHEN an expression has subtraction and multiplication, THEN precedence is followed.")
    @Test
    public void testSubtractionAndMultiplication() throws MalformedExpressionException {
        assertEquals(4, ExpressionEvaluator.evaluate("0010-2*3"));
        assertEquals(17, ExpressionEvaluator.evaluate("10*2-3"));
    }

    @DisplayName("WHEN an expression has subtraction and parentheses, THEN the order of operations is respected.")
    @Test
    public void testSubtractionAndParentheses() throws MalformedExpressionException {
        assertEquals(7, ExpressionEvaluator.evaluate("10-(5-2)"));
        assertEquals(10, ExpressionEvaluator.evaluate("(15+5)-10"));
    }


    @DisplayName("WHEN unary negation is applied to a single number, THEN the result is negative.")
    @Test
    public void testSimpleUnaryNegation() throws MalformedExpressionException {
        assertEquals(-5, ExpressionEvaluator.evaluate("-5"));
        assertEquals(-5, ExpressionEvaluator.evaluate("---5"));
    }

    @DisplayName("WHEN unary negation is applied to a expression in parenthesis, THEN the result is negated.")
    @Test
    public void testUnaryNegationWithParentheses() throws MalformedExpressionException {
        assertEquals(-5, ExpressionEvaluator.evaluate("-(3+2)"));
        assertEquals(0, ExpressionEvaluator.evaluate("10+-(5*2)"));
        assertEquals(5, ExpressionEvaluator.evaluate("--(10-5)"));
    }

    @DisplayName("WHEN unary negation interacts with binary operators, THEN it is evaluated first (since it has the most precedence).")
    @Test
    public void testUnaryNegationPrecedence() throws MalformedExpressionException {
        assertEquals(-5, ExpressionEvaluator.evaluate("5*-1"));
        assertEquals(15, ExpressionEvaluator.evaluate("10--5"));
        assertEquals(6, ExpressionEvaluator.evaluate("-2*-3"));
    }

    @DisplayName("WHEN multiple unary minuses are there incorrectly, THEN throws MalformedExpressionException")
    @Test
    public void testMalformedUnary() {
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("5-"));
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("3*(-+2)"));
    }
    // TODO: Add unit testing for all of the features that you add to the ExpressionEvaluator
    //  over the course of the assignment. Be sure that your tests have descriptive method names
    //  and @DisplayNames. Your tests will be evaluated for their correctness and coverage.
}

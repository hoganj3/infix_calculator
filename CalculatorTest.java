import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Jason Hogan
 * 
 * JUnit test class to test the correctness and robustness of the calculator.
 *  
 */
public class CalculatorTest {

	/**
	 * Tests to ensure that the calculator produces the correct result for arithmetic expressions
	 */
	@Test
	public void testRegularExpressions() {
		Calculator myCalculator = new Calculator();
		
		// Test simple expressions
        assertEquals(1, myCalculator.calculate("1"));
        assertEquals(0, myCalculator.calculate("3-3"));
        assertEquals(14, myCalculator.calculate("11+3"));
        assertEquals(0, myCalculator.calculate("0+0"));
        assertEquals(0, myCalculator.calculate("0*0"));
        assertEquals(0, myCalculator.calculate("234*0"));
        assertEquals(3, myCalculator.calculate("12/4"));
        assertEquals(2, myCalculator.calculate("12/5"));
        assertEquals(3, myCalculator.calculate("1*3"));
        assertEquals(-2, myCalculator.calculate("2-4"));
        assertEquals(7, myCalculator.calculate("1+2*3"));
        assertEquals(15, myCalculator.calculate("10/2*3"));
        assertEquals(8, myCalculator.calculate("10/ 2+3"));

        // Test expressions with signed numbers
        assertEquals(7, myCalculator.calculate("3--4"));
        assertEquals(4, myCalculator.calculate("-2*-2"));
        assertEquals(-6, myCalculator.calculate("-2*+3"));

        // Test expressions with parentheses
        assertEquals(1, myCalculator.calculate("(1)"));
        assertEquals(1, myCalculator.calculate("(((1)))"));
        assertEquals(5, myCalculator.calculate("(3+2)"));
        assertEquals(9, myCalculator.calculate("(1+2)*3"));
        assertEquals(4, myCalculator.calculate("(2*8)/(3+1)"));
        assertEquals(8, myCalculator.calculate("(2*-8)/(-3+1)"));
        
        // Test expressions with bigger numbers
        assertEquals(68, myCalculator.calculate("235442/3434"));
        assertEquals(8054138, myCalculator.calculate("2342*3439"));
	}
	
	/**
	 * Tests to ensure the calculator correctly handles malformed expressions
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNonsenseExpression() {
		Calculator myCalculator = new Calculator();
		myCalculator.calculate("sdv+-/*skjd");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyExpression() {
		Calculator myCalculator = new Calculator();
		myCalculator.calculate("");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testBadOperators() {
		Calculator myCalculator = new Calculator();
		myCalculator.calculate("1*/2");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testUnbalancedRightParentheses() {
		Calculator myCalculator = new Calculator();
		myCalculator.calculate("1+2-3)");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testUnbalancedLeftParentheses() {
		Calculator myCalculator = new Calculator();
		myCalculator.calculate("1+((2-3)");
	}


	/**
	 * Tests to ensure the calculator correctly handles division-by-zero erroes
	 */
	@Test(expected = ArithmeticException.class)
	public void testMathematicalError() {
		Calculator myCalculator = new Calculator();
		myCalculator.calculate("5/0");
	}

}

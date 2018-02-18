
import java.util.*;

/**
 * @author Jason Hogan
 * 
 * Calculator class which takes mathematical expressions (in String format) and evaluates them
 * The Calculator does addition, subtraction, multiplication, and division, of integers.
 *  
 * The calculator can handle parentheses, but does not acknowledge the property of paretheses whereby 
 * paretheses can be used to denote multiplication - i.e. "(2+3)(6-4)" being equivalent to "(2+3) * (6-4)"
 *
 */
public class Calculator {
	
	/**
	 * Function to check if a given token is an operator ('+', '-', '*', or '/')
	 * 
	 * @param element - token to be checked
	 * @return - true if token is an operator, otherwise false
	 */
	private boolean isOperator(String element){
		if(element.equals("/") || element.equals("*") || element.equals("+") || element.equals("-")){
			return true;
		}
		return false;
	}
		
	/**
	 * Function to be called when an operator is reached during evaluation and needs to be resolved.
	 * 
	 * The previous two operands are popped from the values stacked, and the operator is applied to them.
	 * The result of the operation is then pushed on to the values stack.
	 * 
	 * @param values - stack containing all the values/operands that have been found so far in the expression
	 * @param nextOperator - the operator to be applied to  the previous two operands
	 * @throws ArithmeticException
	 * @throws IllegalArgumentException
	 */
	private void popCalculateAndPush(Stack<String> values, String nextOperator) throws ArithmeticException, IllegalArgumentException{
		try{
			// Ensure there are enough values on the stack to apply the operator
			if(values.size() < 2){
				throw new IllegalArgumentException();
			}
			
			// Pop two most recent values off the stack into local variables
			int operandTwo = Integer.parseInt(values.pop());
			int operandOne = Integer.parseInt(values.pop());
			
			/* Switch to find out which operator is represented by the given 'nextOperator' String
			*  and apply it to the two values that were just popped.
			*/ 
			switch(nextOperator){
				case "*":
					values.push(Integer.toString(operandOne*operandTwo));
					break;
				case "/":
					// If it is a division operation and the second value = zero, then there is a division-by-zero error
					if(operandTwo == 0){
						throw new ArithmeticException();
					}
					values.push(Integer.toString(operandOne/operandTwo));
					break;
				case "+":
					values.push(Integer.toString(operandOne+operandTwo));
					break;
				case "-":
					values.push(Integer.toString(operandOne-operandTwo));
					break;
				default:
			}
		} catch(ArithmeticException e) {	// Catch division-by-zero exception
			throw new ArithmeticException(); 
		} catch(Exception e){	// Catch all other exceptions
			throw new IllegalArgumentException(); 
		}

	}
	
	/**
	 * Function to take a mathematical expression in String format, and convert it into a 
	 * list of Strings representing the individual tokens in the expression.
	 * 
	 * @param expression - Single String object containing the entire mathematical expression
	 * @return Array of Strings containing the individual elements of the expression
	 * @throws IllegalArgumentException
	 */
	private String[] parseString(String expression) throws IllegalArgumentException{
		// Initially an ArrayList is used, since it is not known how many tokens are in the expression
		ArrayList<String> expressionList = new ArrayList<String>();
		
		String previousCharacter = ""; // String to hold value of previous character in the expression
		String previousOperator = "";  // String to hold value of previous operator in the expression
		
		for(int i = 0; i < expression.length(); i++){
			// Create new String containing the next character in the expression String.
			String currentChar = expression.substring(i, i+1);
			
			// If the next character is a space, ignore it and continue to next iteration
			if(currentChar.equals(" ")){
				continue;
			}
			
			// If next character is an operator
			if(isOperator(currentChar)){
				// If the operator is a '+' or '-', it may be intended as a positive/negative sign, not an operator
				// If this is the first token in the expression, or if the previous token was also an operator, then...
				if(i == 0 || previousOperator != ""){
					// Check that the current operator is indeed a '+' or '-'
					if((currentChar.equals("+") || currentChar.equals("-"))){
						previousCharacter += currentChar; // In which case, add it to the String representing the next token
						previousOperator = currentChar;   // And set the previous operator variable
					}
					// If operator is not '+' or '-' then it can't be a sign, so must be a error in the expression
					else { throw new IllegalArgumentException(); }
				}
				
				// If the previous token was not an operator
				else {
					expressionList.add(previousCharacter); // Then the end of the previous token has been reached, so store it
					expressionList.add(currentChar);	    // And then store the operator which follows it
					previousOperator = currentChar; // Set the previous operator variable 
					previousCharacter = ""; // Reset previousCharacter so it's ready for the next set of characters
				}
			} 
			
			// If the next token is a parenthesis...
			else if(currentChar.equals("(") || currentChar.equals(")")){
				// If it is an opening parenthesis it can be treated as a previous operator, 
				// in case the next token is a '+' or '-' sign
				if(currentChar.equals("(")){
					previousOperator = currentChar;
				}
				// If it is a closing parenthesis then the end of the previous token has been reached, so store it. 
				else {
					expressionList.add(previousCharacter);
					previousCharacter = "";  // Reset previousCharacter so it is ready for the next set of characters
				}
				expressionList.add(currentChar);   // Store the parenthesis token.
			}
			
			// If the next token is a digit...
			else if(Character.isDigit(currentChar.charAt(0))){
				previousOperator = ""; // Then reset previous operator, it won't apply on next loop iteration.
				// Append this digit to the sequence of digits that is making up this numeric value.
				// This is necessary because a number like '2365' will be parsed one digit at a time.
				previousCharacter += currentChar;
			}
			
			// If the current token meets none of the above conditions, it must be a semantic error.
			else { throw new IllegalArgumentException(); }
			
			// If we have reached the end of the list...
			if(i == expression.length() - 1){
				// Then the last token is a numeric value which is now complete, so it can be stored
				expressionList.add(previousCharacter);
			}
		}
		
		// Convert the array list of tokens into a regular array of Strings
		String[] expressionArray = new String[expressionList.size()];
		for(int i = 0; i < expressionList.size(); i++){
			expressionArray[i] = expressionList.get(i);
		}
		
		// And return it
		return expressionArray;
	}
	
	/**
	 * Function to take a list of tokens (in String format) of a mathematical expression and 
	 * solve the expression, returning an integer containing the result.
	 * 
	 * @param expression - list of Strings representing the expression's tokens 
	 * @return - the result of the expression
	 * @throws ArithmeticException
	 * @throws IllegalArgumentException
	 */
	private int evaluate(String[] expression) throws ArithmeticException, IllegalArgumentException{
		// Instanciate 2 stacks, one for operators and one for numeric values
		Stack<String> values = new Stack<String>();
		Stack<String> operators = new Stack<String>();

		// Iterate through the list of tokens
		for(int i = 0; i < expression.length; i++){
			// get next token in expression
			String thisToken = expression[i];
			
			// If this token is an opening parenthesis
			if(thisToken.equals("(")){
				operators.push(thisToken); // Opening parentheses are pushed straight on to the operators stack
			}
			
			// If this token is a closing parenthesis, then the sub-expression inside the parentheses needs 
			// to be evaluated before continuing
			else if(thisToken.equals(")")){
				// Initialise boolean to control the following loop as the parenthesized sub-expression is parsed
				boolean withinParentheses = true;
				
				// Keep working back iteratively through previously stored operators and values until the 
				// matching opening parenthesis for this closing parenthesis is reached.
				while(withinParentheses){
					// Try and retrieve the most recent operator from the operators stack
					String nextOperator;
					try {
						nextOperator = operators.pop();
					} catch(EmptyStackException e){
						// If the operator stack empties before we reach an '(', then there are unbalanced
						// parentheses, so an exception is thrown to indicate the semantic error.
						throw new IllegalArgumentException();
					}
						
					// if the matching opening parenthesis is found, break out of loop and continue on
					if(nextOperator.equals("(")){
						withinParentheses = false;
					}
					
					// Otherwise take current current operator popped from the stack and pass it to popCalculateAndPush 
					// function to evaluate result of next stage of sub-expression inside parentheses.
					else {
						popCalculateAndPush(values, nextOperator);
					}
				}
			}
				
			// If this token is an operator then the value of a sub-section of the expression may need to be calculated
			// immediately based on whether this operator has lower precedence than the preceeding operators or not.
			else if(isOperator(thisToken)) {
				// Initialise boolean to designate if the operator has high precedence ('*', '/') or not ('+', '-')
				boolean highPrecedence = false;
				if(thisToken.equals("*") || thisToken.equals("/")){
					highPrecedence = true;
				}
				
				//  The loop will continue until the next operator popped from the stack has lower precedence than
				//  the operator represented by this token
				boolean notLowerPrecedence = true;
				
				while(operators.isEmpty() == false && notLowerPrecedence){
					// Pop next most recent operator from the operators stack
					String nextOperator = operators.pop();	
					
					// If the next item on the operators stack has lower precedence, or is an opening parenthesis
					// then put it back on the operators stack and set loop condition variable accordingly
					if(highPrecedence == true && (nextOperator.equals("+") || nextOperator.equals("-")) || nextOperator.equals("(")){
						operators.push(nextOperator);
						notLowerPrecedence = false;
					} 
					
					// Otherwise take the operator popped from the stack and pass it the popCalculateAndPush 
					// function to evaluate the result of the next section of the expression 
					else {
						popCalculateAndPush(values, nextOperator);
					}
				}	
				
				// Once the values on the value stack have been updated in accordance with operator precedence
				// then the current operator token can be pushed on to the operators stack.
				operators.push(thisToken);
			}
			
			// If the token is not a parenthesis, operator, or null character, then it is an operand 
			else if(thisToken.length() > 0){
				values.push(thisToken);	// So it is pushed on to the values stack
			}
		}
		
		/*
		 *  At this stage, the entire list of tokens has been traversed, and during traversal parentheses and
		 *  operator precedence have been dealt with. So if there are any operators left on the operator stack
		 *  now then they can just be applied to the remaining values on the values stack.
		 */
		while(operators.isEmpty() == false){
			// If there is another operator but there no values left, then there is a semantic error in the expression
			if(values.isEmpty()){
				throw new IllegalArgumentException();
			}
			
			// Otherwise pop the next operator and apply it to the next two values on the values stack
			String nextOperator = operators.pop();	
			popCalculateAndPush(values, nextOperator);
		}
		
		//  At this stage all the operators have been applied, so there should just be a single value
		//  left on the values stack - the result of the expression
		if(values.size() != 1){
			throw new IllegalArgumentException(); // If not then there is a semantic error in the expression
		}
		
		// Pop the value off its stack, convert it to an int, and return it.
		String finalResult = values.pop();
		return Integer.parseInt(finalResult);
	}
	
	
	/**
	 *  Main function to orchestrate the operation of the calculator.
	 * 
	 * @param expression - The expression to be evaluated by the calculator 
	 * @return - the result of the expression
	 * @throws IllegalArgumentException
	 * @throws ArithmeticException
	 */
	public int calculate(String expression) throws IllegalArgumentException, ArithmeticException{
		// Ensure expression is not null or empty
		if(expression == null || expression.equals("")){
			throw new IllegalArgumentException();
		}
		
		// Parse expression string to create list of tokens in the expression
		String[] expressionElements = parseString(expression);
		
		// Evaluate the list of tokens to generate a result for the expression
		int result = evaluate(expressionElements);
		
		// Return the result of the expression
		return result;
	}
	
}

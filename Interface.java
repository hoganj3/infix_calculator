
import java.util.*;


/**
 * @author Jason Hogan
 * 
 * Interface class to interact with the user, accept expressions entered by the user, and pass them
 * to the calculator object.
 *
 */
public class Interface {

	public static void main(String[] args) {
		
		// Calculator object initialised
		Calculator myCalculator = new Calculator();
		
		// Scanner initialised to handle user input
		Scanner inputScanner = new Scanner(System.in);
				
		System.out.println("Welcome to calculator!");
		System.out.println("Type expressions to be calculated, or enter 'exit' to quit.\n");

		/* 	Infinite loop which allows user to enter expressions for the calculator to solve
		*	until the user elects to quit the program.
		**/
		boolean active = true;
		while(active){
			System.out.println("Please enter an expression: ");
			String expression = "";
			
			// Try read next line containing the user-typed expression
			try {
				expression = inputScanner.nextLine();
			} catch(Exception e){
				System.out.println("Error; please enter a semantically correct mathematical expression");
				continue;
			}
			
			// If user types 'exit', then close the scanner and terminate the program.
			if(expression == "exit"){
				inputScanner.close();
				return;
			}
			
			// Try evaulate the expression using the calculator and show the result
			// Catch and handle any exceptions
			try{
				int resultOfExpression = myCalculator.calculate(expression);
				System.out.println("Result = " + resultOfExpression + "\n");
			} catch(ArithmeticException e){
				System.out.println("Oops, there is an arithmetic error in the expression");
			} catch(Exception e){
				System.out.println("Oops, there is a semantic error in the expression");
			} 			
		}
			
		// Close the scanner and terminate the program
		inputScanner.close();
		return;
	}
}

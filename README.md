#Calculator Program

This is a simple integer calculator built in Java, with JUnit testing.

* The calculator can carry out addition, subtraction, multiplication, and division.

* The calculator can handle parentheses, but does not acknowledge the property of paretheses whereby 
paretheses can be used to denote multiplication - i.e. "(2+3)(6-4)" being equivalent to "(2+3) * (6-4)".

* The calculator evaluates infix mathematical expressions, without converting them to postfix or prefix notation.

* A custom method was also defined for turning the expression string into a list of tokens, instead of using Java's StringTokenizer.


&nbsp;

## Approach

Two stacks are used to control order of operations - one stack for numeric values and another stack for operators and parentheses.

The calculator takes an expression in the form of a string as input from the user. It parses this string and creates from it a list of substrings, where each substring represents one token (operand, operator, or parenthesis) in the expression. It then evaluates this list of tokens, pushing values and operands on to the two stacks as it goes. 

When parentheses or operators with low precedence are encountered, the order of operations must be considered. In these cases, the precedences of all the operations within the expression might not be equal so the values of some parts of the expression are calculated immediately, until the precedence is once again uniform across the whole expression.

&nbsp;

## Author
Jason Hogan
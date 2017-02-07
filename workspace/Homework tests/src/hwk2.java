
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Test;

public class hwk2{
	public static void main(String []args){
		Scanner stdIn = new Scanner(System.in);
		System.out.println (parse( promptAndRead(stdIn) ));
	}

	private static String promptAndRead(Scanner stdIn) {
		String code="";
		do {
			System.out.println("Please enter a Java if-then-else statement in one line:");
			code = stdIn.nextLine();
		} while (code.length()== 0);

		return code;
	}
	
	private static String parse(String code) {
		// TODO
		String tempCode = "";
		String nextLine = "\n";
		String tabLine = "\t";
		String space = " ";
		
		int exit = getBrackets(code) - 1;//counts for the exit amount
		int startFrom = 0;
		int bracket = code.indexOf('{', startFrom);
		int backBracket = code.indexOf('}', startFrom);
		int par = code.indexOf('(', startFrom);
		int backPar = code.indexOf(')', startFrom) + 1;
		int r = code.indexOf('r', startFrom);
		int e = code.indexOf('e', startFrom);
		int end = code.length();
		
		for (int count = 0; count <= exit; count++){//counts the loop
			if (count == 0){
				tempCode = code.substring(startFrom , par);//if 
				tempCode = tempCode.trim();
			
				startFrom = par;
				
				tempCode = tempCode + space + code.substring(startFrom , bracket);
				tempCode = tempCode.trim();
			}
			else{			
				tempCode = tempCode + nextLine;
				
				tempCode = tempCode + code.substring(startFrom , bracket - 1);
				tempCode = tempCode.trim();
				
				startFrom = bracket - 1;
				
				tempCode = tempCode + space + code.substring(startFrom , bracket);
				tempCode = tempCode.trim();
			}
			
			tempCode = tempCode + space + code.substring(bracket, bracket);
			
			startFrom = bracket;
			r = code.indexOf('r', startFrom);
			
			tempCode = tempCode + code.substring(startFrom , r);//if (true) {\n\t
			tempCode = tempCode.trim();
			
			startFrom = r;
			bracket = code.indexOf('{', startFrom) + 1;
			backBracket = code.indexOf('}', startFrom);
			
			tempCode = tempCode + nextLine + tabLine + code.substring(startFrom,  backBracket);//return 1;\n
			tempCode = tempCode.trim();
			
			startFrom = backBracket;
			
			if (count == exit){
				tempCode = tempCode + nextLine + code.substring(startFrom, backBracket + 1);//}\n
			}
			else {
				e = code.indexOf('e', startFrom);
				tempCode = tempCode + nextLine + code.substring(startFrom , e);
				tempCode = tempCode.trim();
			}
			e = code.indexOf('e', startFrom);
			
			startFrom = e;
			bracket = code.indexOf('{', startFrom) + 1;
			backBracket = code.indexOf('}', startFrom);
			
		}
		code = tempCode.trim();
		
		return code;
	} 
	
	public static int getBrackets(String code)//gets the number of brackets
	{
		int numOfBrackets = 0;
		
		for (int i = 0; i < code.length(); i++){
			if (code.charAt(i) == '}'){
				numOfBrackets++;
			}
		}
		return numOfBrackets;
	}
	
	@Test
	public void test1() {
		String input = "if (true) { return 1; } else { return 0; }";
		String output = "if (true) {\n\treturn 1;\n}\nelse {\n\treturn 0;\n}"; 
		assertTrue(parse(input).equals(output));
	}
	
	@Test
	public void test2() {
		String input = "if (true) { return 1; }";
		String output = "if (true) {\n\treturn 1;\n}"; 
		assertTrue(parse(input).equals(output));
	}
	
	@Test
	public void test3() {
		String input = "if(true){return 1;}else{return 0;}";
		String output = "if (true) {\n\treturn 1;\n}\nelse {\n\treturn 0;\n}"; 
		assertTrue(parse(input).equals(output));
	}
	
	@Test
	public void test4() {
		String input = "if(true){return 1;}";
		String output = "if (true) {\n\treturn 1;\n}"; 
		assertTrue(parse(input).equals(output));
	}
	
	@Test
	public void test5() {
		String input = "if   (true) {   return 1;     } else { return 0; }";
		String output = "if (true) {\n\treturn 1;\n}\nelse {\n\treturn 0;\n}"; 
		assertTrue(parse(input).equals(output));
	}
	
	@Test
	public void test6() {
		String input = "if   (true)   {   return 1;    }";
		String output = "if (true) {\n\treturn 1;\n}"; 
		assertTrue(parse(input).equals(output));
	}
}

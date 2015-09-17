package textbuddy.ce2;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextBuddyTest {
	
	@Before
	public void setUp(){
		TextBuddy.textContent.add("Hello World!");
		TextBuddy.textContent.add("Bye Bye World!");
	}
	
	public void simulateUserInput(String fakeInput){
		ByteArrayInputStream in = new ByteArrayInputStream(fakeInput.getBytes());
		System.setIn(in);
		TextBuddy.reader = new Scanner(System.in);
	}
	
	@Test
	public void processTest() {
		String expectedType; String expectedContent; String actualType; String actualContent;
		
		//Test 1
		simulateUserInput("add Hello World");
		String command = TextBuddy.readCommand();
	    TextBuddy.processCommand(command);
	    
	    expectedType = "add";
	    expectedContent = "Hello World";
	    actualType = TextBuddy.commandType;
	    actualContent = TextBuddy.commandContent;
	    assertTrue(expectedType.equals(actualType));
	    assertTrue(expectedContent.equals(actualContent));
	    
	    //Test 2
	    simulateUserInput("display");
		command = TextBuddy.readCommand();
	    TextBuddy.processCommand(command);
	    
	    expectedType = "display";
	    expectedContent = "";
	    actualType = TextBuddy.commandType;
	    actualContent = TextBuddy.commandContent;
	    assertTrue(expectedType.equals(actualType));
	    assertTrue(expectedContent.equals(actualContent));
	}
	
	@Test
	public void addTest() {
	    TextBuddy.addText("It's A Small World");    
	    
	    String expectedLine3 = "It's A Small World";
	    String actualLine3 = TextBuddy.getLine(3);
	    //System.out.println("Expected: " + expectedLine3 + " | actual: " + actualLine3);
	    assertTrue(expectedLine3.equals(actualLine3));
	}
	
	@Test
	public void deleteTest() {
		TextBuddy.addText("Anyone There?");
	    TextBuddy.deleteText(1);
	    TextBuddy.deleteText(1);
	    
	    String expectedLine1 = "Anyone There?";
	    String actualLine1 = TextBuddy.getLine(1);
	    assertTrue(expectedLine1.equals(actualLine1));
	}
	
	@Test
	public void executeTest() {
		TextBuddy.commandType = "add";
		TextBuddy.commandContent = "AHHHHHHH";
		TextBuddy.executeCommand();
		String expectedLine3 = "AHHHHHHH";
	    String actualLine3 = TextBuddy.getLine(3);
	    assertTrue(expectedLine3.equals(actualLine3));
	    
	    TextBuddy.commandType = "delete";
		TextBuddy.commandContent = "2";
		TextBuddy.executeCommand();
		String expectedLine2 = "AHHHHHHH";
	    String actualLine2 = TextBuddy.getLine(2);
	    assertTrue(expectedLine2.equals(actualLine2));
	}
	
	@Test
	public void updateTest() {
		simulateUserInput("This is Sparta!");
		TextBuddy.updateText(1);

		int expectedNumberOfLines = 2;
	    int actualNumberOfLines = TextBuddy.textContent.size();
	    assertTrue(expectedNumberOfLines == actualNumberOfLines);		
		
	    String expectedLine1 = "This is Sparta!";
	    String actualLine1 = TextBuddy.getLine(1);
	    assertTrue(expectedLine1.equals(actualLine1));
	    
	    String expectedLine2 = "Bye Bye World!";
	    String actualLine2 = TextBuddy.getLine(2);
	    assertTrue(expectedLine2.equals(actualLine2));
	}
	
	@Test
	public void insertTest() {
		simulateUserInput("This is Sparta!");
		TextBuddy.insertText(1);

		int expectedNumberOfLines = 3;
	    int actualNumberOfLines = TextBuddy.textContent.size();
	    assertTrue(expectedNumberOfLines == actualNumberOfLines);		
		
	    String expectedLine1 = "This is Sparta!";
	    String actualLine1 = TextBuddy.getLine(1);
	    assertTrue(expectedLine1.equals(actualLine1));
	    
	    String expectedLine2 = "Hello World!";
	    String actualLine2 = TextBuddy.getLine(2);
	    assertTrue(expectedLine2.equals(actualLine2));
	    
	    String expectedLine3 = "Bye Bye World!";
	    String actualLine3 = TextBuddy.getLine(3);
	    assertTrue(expectedLine3.equals(actualLine3));
	}
	
	@Test
	public void validLineTest() {
		assertFalse(TextBuddy.isValidLineNumber(""));
		assertFalse(TextBuddy.isValidLineNumber("0"));
		assertFalse(TextBuddy.isValidLineNumber("-123"));
		assertFalse(TextBuddy.isValidLineNumber("sdgs"));
		assertFalse(TextBuddy.isValidLineNumber("3"));
		assertTrue(TextBuddy.isValidLineNumber("2"));
	}
	
	@Test
	public void clearTest() {
		simulateUserInput("y");
		TextBuddy.clearContent();
		
	    assertTrue(TextBuddy.textContent.isEmpty());
	}

	@Test
	public void saveTest() {
		String[] fileName = {"junitTest"};
		TextBuddy.fileManager.setUpFile(fileName);
		simulateUserInput("y"); //save before exit
		TextBuddy.saveContent();
		simulateUserInput("y");
		TextBuddy.clearContent();
		
		TextBuddy.fileManager.loadContentFromFile("junitTest.txt");
	    String expectedLine1 = "Hello World!";
	    String actualLine1 = TextBuddy.getLine(1);
	    assertTrue(expectedLine1.equals(actualLine1));
	    
	    String expectedLine2 = "Bye Bye World!";
	    String actualLine2 = TextBuddy.getLine(2);
	    assertTrue(expectedLine2.equals(actualLine2));
	    
	    //clean up junitTest.txt
	    simulateUserInput("y");
	    TextBuddy.clearContent();
		simulateUserInput("y");
		TextBuddy.saveContent();
	}
	
	@Test
	public void sortTest() {
		TextBuddy.addText("AHHHHH");
		TextBuddy.sortContent();
		
		String expectedLine1 = "AHHHHH";
	    String actualLine1 = TextBuddy.getLine(1);
	    assertTrue(expectedLine1.equals(actualLine1));
	    
	    String expectedLine2 = "Bye Bye World!";
	    String actualLine2 = TextBuddy.getLine(2);
	    assertTrue(expectedLine2.equals(actualLine2));
	    
	    String expectedLine3 = "Hello World!";
	    String actualLine3 = TextBuddy.getLine(3);
	    assertTrue(expectedLine3.equals(actualLine3));
	}
	
	@Test
	public void searchTest() {
		TextBuddy.addText("Welcome To Heaven!");
		TextBuddy.addText("Welcome To Hell!");
		TextBuddy.addText("Welcome To My World!");
		
		//Test 1
		ArrayList<String> searchResult = TextBuddy.searchContent("world");
		int expectedNumberOfLines = 3;
	    int actualNumberOfLines = searchResult.size();
	    assertTrue(expectedNumberOfLines == actualNumberOfLines);
		
		String expectedLine1 = "Hello World!";
	    String actualLine1 = searchResult.get(0);
	    assertTrue(expectedLine1.equals(actualLine1));
	    
	    String expectedLine2 = "Bye Bye World!";
	    String actualLine2 = searchResult.get(1);
	    assertTrue(expectedLine2.equals(actualLine2));
	    
	    String expectedLine3 = "Welcome To My World!";
	    String actualLine3 = searchResult.get(2);
	    assertTrue(expectedLine3.equals(actualLine3));
	    
		//Test 2
	    searchResult = TextBuddy.searchContent("hell");
		expectedNumberOfLines = 1;
	    actualNumberOfLines = searchResult.size();
	    assertTrue(expectedNumberOfLines == actualNumberOfLines);
		
		expectedLine1 = "Welcome To Hell!";
	    actualLine1 = searchResult.get(0);
	    assertTrue(expectedLine1.equals(actualLine1));
	}
	
	@After
	public void cleanUp(){
		TextBuddy.textContent.clear();
		TextBuddy.commandType = "";
		TextBuddy.commandContent = "";
		System.setIn(System.in);
	}
}

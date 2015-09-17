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
		TextBuddy.conMan.content.add("Hello World!");
		TextBuddy.conMan.content.add("Bye Bye World!");
	}
	
	void simulateUserInput(String fakeInput){
		ByteArrayInputStream in = new ByteArrayInputStream(fakeInput.getBytes());
		System.setIn(in);
		TextBuddy.ui.reader = new Scanner(System.in);
	}
	
	@Test
	public void processTest() {
		String expectedType; String expectedContent; String actualType; String actualContent;
		
		//Test 1
		simulateUserInput("add Hello World");
		String command = TextBuddy.ui.readInput();
	    String[] commandParts = TextBuddy.logic.processCommand(command);
	    
	    expectedType = "add";
	    expectedContent = "Hello World";
	    actualType = commandParts[0];
	    actualContent = commandParts[1];
	    assertTrue(expectedType.equals(actualType));
	    assertTrue(expectedContent.equals(actualContent));
	    
	    //Test 2
	    simulateUserInput("display");
		command = TextBuddy.ui.readInput();
		commandParts = TextBuddy.logic.processCommand(command);
	    
	    expectedType = "display";
	    expectedContent = "";
	    actualType = commandParts[0];
	    actualContent = commandParts[1];
	    assertTrue(expectedType.equals(actualType));
	    assertTrue(expectedContent.equals(actualContent));
	}
	
	@Test
	public void addTest() {
	    TextBuddy.conMan.addText("It's A Small World");    
	    
	    String expectedLine3 = "It's A Small World";
	    String actualLine3 = TextBuddy.conMan.getLine(3);
	    //System.out.println("Expected: " + expectedLine3 + " | actual: " + actualLine3);
	    assertTrue(expectedLine3.equals(actualLine3));
	}
	
	@Test
	public void deleteTest() {
		TextBuddy.conMan.addText("Anyone There?");
	    TextBuddy.conMan.deleteText(1);
	    TextBuddy.conMan.deleteText(1);
	    
	    String expectedLine1 = "Anyone There?";
	    String actualLine1 = TextBuddy.conMan.getLine(1);
	    assertTrue(expectedLine1.equals(actualLine1));
	}
	
	@Test
	public void executeTest() {
		String[] commandParts = {"add", "AHHHHHHH"};
		TextBuddy.logic.executeCommand(commandParts);
		String expectedLine3 = "AHHHHHHH";
	    String actualLine3 = TextBuddy.conMan.getLine(3);
	    assertTrue(expectedLine3.equals(actualLine3));
	    
	    String[] commandParts2 = {"delete", "2"};
		TextBuddy.logic.executeCommand(commandParts2);
		String expectedLine2 = "AHHHHHHH";
	    String actualLine2 = TextBuddy.conMan.getLine(2);
	    assertTrue(expectedLine2.equals(actualLine2));
	}
	
	@Test
	public void updateTest() {
		simulateUserInput("This is Sparta!");
		TextBuddy.conMan.updateText(1);

		int expectedNumberOfLines = 2;
	    int actualNumberOfLines = TextBuddy.conMan.content.size();
	    assertTrue(expectedNumberOfLines == actualNumberOfLines);		
		
	    String expectedLine1 = "This is Sparta!";
	    String actualLine1 = TextBuddy.conMan.getLine(1);
	    assertTrue(expectedLine1.equals(actualLine1));
	    
	    String expectedLine2 = "Bye Bye World!";
	    String actualLine2 = TextBuddy.conMan.getLine(2);
	    assertTrue(expectedLine2.equals(actualLine2));
	}
	
	@Test
	public void insertTest() {
		simulateUserInput("This is Sparta!");
		TextBuddy.conMan.insertText(1);

		int expectedNumberOfLines = 3;
	    int actualNumberOfLines = TextBuddy.conMan.content.size();
	    assertTrue(expectedNumberOfLines == actualNumberOfLines);		
		
	    String expectedLine1 = "This is Sparta!";
	    String actualLine1 = TextBuddy.conMan.getLine(1);
	    assertTrue(expectedLine1.equals(actualLine1));
	    
	    String expectedLine2 = "Hello World!";
	    String actualLine2 = TextBuddy.conMan.getLine(2);
	    assertTrue(expectedLine2.equals(actualLine2));
	    
	    String expectedLine3 = "Bye Bye World!";
	    String actualLine3 = TextBuddy.conMan.getLine(3);
	    assertTrue(expectedLine3.equals(actualLine3));
	}
	
	@Test
	public void validLineTest() {
		assertFalse(TextBuddy.logic.isValidLineNumber(""));
		assertFalse(TextBuddy.logic.isValidLineNumber("0"));
		assertFalse(TextBuddy.logic.isValidLineNumber("-123"));
		assertFalse(TextBuddy.logic.isValidLineNumber("sdgs"));
		assertFalse(TextBuddy.logic.isValidLineNumber("3"));
		assertTrue(TextBuddy.logic.isValidLineNumber("2"));
	}
	
	@Test
	public void clearTest() {
		simulateUserInput("y");
		TextBuddy.conMan.clearContent();
		
	    assertTrue(TextBuddy.conMan.content.isEmpty());
	}

	@Test
	public void saveTest() {
		String[] fileName = {"junitTest"};
		TextBuddy.fileMan.setUpFile(fileName);
		simulateUserInput("y"); //save before exit
		TextBuddy.saveContent();
		simulateUserInput("y");
		TextBuddy.conMan.clearContent();
		
		TextBuddy.fileMan.loadContentFromFile("junitTest.txt");
	    String expectedLine1 = "Hello World!";
	    String actualLine1 = TextBuddy.conMan.getLine(1);
	    assertTrue(expectedLine1.equals(actualLine1));
	    
	    String expectedLine2 = "Bye Bye World!";
	    String actualLine2 = TextBuddy.conMan.getLine(2);
	    assertTrue(expectedLine2.equals(actualLine2));
	    
	    //clean up junitTest.txt
	    simulateUserInput("y");
	    TextBuddy.conMan.clearContent();
		simulateUserInput("y");
		TextBuddy.saveContent();
	}
	
	@Test
	public void sortTest() {
		TextBuddy.conMan.addText("AHHHHH");
		TextBuddy.conMan.sortContent();
		
		String expectedLine1 = "AHHHHH";
	    String actualLine1 = TextBuddy.conMan.getLine(1);
	    assertTrue(expectedLine1.equals(actualLine1));
	    
	    String expectedLine2 = "Bye Bye World!";
	    String actualLine2 = TextBuddy.conMan.getLine(2);
	    assertTrue(expectedLine2.equals(actualLine2));
	    
	    String expectedLine3 = "Hello World!";
	    String actualLine3 = TextBuddy.conMan.getLine(3);
	    assertTrue(expectedLine3.equals(actualLine3));
	}
	
	@Test
	public void searchTest() {
		TextBuddy.conMan.addText("Welcome To Heaven!");
		TextBuddy.conMan.addText("Welcome To Hell!");
		TextBuddy.conMan.addText("Welcome To My World!");
		
		//Test 1
		ArrayList<String> searchResult = TextBuddy.conMan.searchContent("world");
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
	    searchResult = TextBuddy.conMan.searchContent("hell");
		expectedNumberOfLines = 1;
	    actualNumberOfLines = searchResult.size();
	    assertTrue(expectedNumberOfLines == actualNumberOfLines);
		
		expectedLine1 = "Welcome To Hell!";
	    actualLine1 = searchResult.get(0);
	    assertTrue(expectedLine1.equals(actualLine1));
	}
	
	@After
	public void cleanUp(){
		TextBuddy.conMan.content.clear();
		System.setIn(System.in);
	}
}

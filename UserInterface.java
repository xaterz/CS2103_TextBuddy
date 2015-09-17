package textbuddy.ce2;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
	Scanner reader = new Scanner(System.in);
	
	final String INPUT_ARROW = ">> "; 
	final String MESSAGE_WELCOME = "\nWelcome to TextBuddy!\n";
	final String MESSAGE_EMPTY_COMMAND = "Error: Please enter a command!\n";
	final String MESSAGE_EMPTY_ADD = "Error: There's nothing to add!\n";
	final String MESSAGE_UNRECOGNISED_COMMAND = "Error: Unrecognised command '%1$s'.\n";
	final String MESSAGE_TEXT_ADDED = "Added '%1$s' to content!\n";
	final String MESSAGE_EMPTY_DISPLAY = "There's nothing to display!\n";
	final String MESSAGE_DISPLAY_TEXT = "Displaying content:\n";
	final String MESSAGE_LINE_NUMBER_EMPTY = "Error: Please indicate the line number!\n";
	final String MESSAGE_LINE_NUMBER_INVALID = "Error: '%1$s' is not a valid line number!\n";
	final String MESSAGE_LINE_NUMBER_OUT_OF_BOUND = "Error: Line %1$s is out of bound!\n";
	final String MESSAGE_UPDATING_TEXT = "Updating line %1$s '%2$s'...\n";
	final String MESSAGE_TEXT_UPDATED = "Line %1$s of content has been updated to '%2$s'\n";
	final String MESSAGE_OPERATION_CANCELLED = "Operation cancelled.\n";
	final String MESSAGE_ENTER_NEW_TEXT = "Please enter a new text (Enter nothing to cancel) ";
	final String MESSAGE_TEXT_DELETED = "Deleted '%1$s' from content!\n";
	final String MESSAGE_INSERTING_TEXT = "Inserting new text at line %1$s...\n";
	final String MESSAGE_TEXT_INSERTED = "Inserted '%1$s' into content at line %2$s.\n";
	final String MESSAGE_CLEAR_CONFIRM = "Are you sure you want to delete all contents? [y/n] ";
	final String MESSAGE_ALL_CLEARED = "All contents deleted!\n";
	final String MESSAGE_SAVE_CONFIRM = "Save modified contents to %1$s? [y/n] ";
	final String MESSAGE_SAVING_CONTENT = "Saving contents to %1$s... ";
	final String MESSAGE_SAVE_CANCELLED = "Contents not saved.\n";
	final String MESSAGE_INVALID_REPLY = "Error: Invalid input! Please type 'y' (yes) or 'n' (no) ";
	final String MESSAGE_CONTENT_SORTED = "Contents have been sorted!\n";
	final String MESSAGE_NO_RESULT = "No results found for '%1$s'.\n";
	final String MESSAGE_SHOWING_RESULT = "Showing search results for '%1$s'.\n";
	final String MESSAGE_GOODBYE = "Exiting TextBuddy... Goodbye!\n";
	
	void showToUser(String message){
		System.out.print(message);
	}
	
	String readInput(){
		showToUser(INPUT_ARROW);
		String input = reader.nextLine();
		return input;
	}
	
	void displayText(){
		if (TextBuddy.conMan.content.isEmpty()) {
			showToUser(MESSAGE_EMPTY_DISPLAY);
		} else {
			showToUser(MESSAGE_DISPLAY_TEXT);
			for (int i = 1; i < TextBuddy.conMan.content.size()+1; i++) {
				showToUser(i + "   " + TextBuddy.conMan.getLine(i) + "\n");
			}
		}
	}
	
	String getNewText() {
		showToUser(MESSAGE_ENTER_NEW_TEXT);
		return readInput();
	}
	
	void showSearchResult(String word, ArrayList<String> result){
		if (result.isEmpty()) {
			showToUser(String.format(MESSAGE_NO_RESULT, word));
		} else {
			showToUser(String.format(MESSAGE_SHOWING_RESULT, word));
			for (int i = 0; i < result.size(); i++) {
				String line = result.get(i);
				showToUser(i+1 + "   " + line + "\n");
			}
		}
	}

}

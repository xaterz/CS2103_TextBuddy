package textbuddy.ce2;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
	//This is used to read the command entered by the user.
	public Scanner reader = new Scanner(System.in);
	
	public final String INPUT_ARROW = ">> "; 
	public final String MESSAGE_WELCOME = "\nWelcome to TextBuddy!\n";
	public final String MESSAGE_EMPTY_COMMAND = "Error: Please enter a command!\n";
	public final String MESSAGE_EMPTY_ADD = "Error: There's nothing to add!\n";
	public final String MESSAGE_UNRECOGNISED_COMMAND = "Error: Unrecognised command '%1$s'.\n";
	public final String MESSAGE_TEXT_ADDED = "Added '%1$s' to content!\n";
	public final String MESSAGE_EMPTY_DISPLAY = "There's nothing to display!\n";
	public final String MESSAGE_DISPLAY_TEXT = "Displaying content:\n";
	public final String MESSAGE_LINE_NUMBER_EMPTY = "Error: Please indicate the line number!\n";
	public final String MESSAGE_LINE_NUMBER_INVALID = "Error: '%1$s' is not a valid line number!\n";
	public final String MESSAGE_LINE_NUMBER_OUT_OF_BOUND = "Error: Line %1$s is out of bound!\n";
	public final String MESSAGE_UPDATING_TEXT = "Updating line %1$s '%2$s'...\n";
	public final String MESSAGE_TEXT_UPDATED = "Line %1$s of content has been updated to '%2$s'\n";
	public final String MESSAGE_OPERATION_CANCELLED = "Operation cancelled.\n";
	public final String MESSAGE_ENTER_NEW_TEXT = "Please enter a new text (Enter nothing to cancel) ";
	public final String MESSAGE_TEXT_DELETED = "Deleted '%1$s' from content!\n";
	public final String MESSAGE_INSERTING_TEXT = "Inserting new text at line %1$s...\n";
	public final String MESSAGE_TEXT_INSERTED = "Inserted '%1$s' into content at line %2$s.\n";
	public final String MESSAGE_CLEAR_CONFIRM = "Are you sure you want to delete all contents? [y/n] ";
	public final String MESSAGE_ALL_CLEARED = "All contents deleted!\n";
	public final String MESSAGE_SAVE_CONFIRM = "Save modified contents to %1$s? [y/n] ";
	public final String MESSAGE_SAVING_CONTENT = "Saving contents to %1$s... ";
	public final String MESSAGE_SAVE_CANCELLED = "Contents not saved.\n";
	public final String MESSAGE_INVALID_REPLY = "Error: Invalid input! Please type 'y' (yes) or 'n' (no) ";
	public final String MESSAGE_CONTENT_SORTED = "Contents have been sorted!\n";
	public final String MESSAGE_NO_RESULT = "No results found for '%1$s'.\n";
	public final String MESSAGE_SHOWING_RESULT = "Showing search results for '%1$s'.\n";
	public final String MESSAGE_GOODBYE = "Exiting TextBuddy... Goodbye!\n";
	
	public void showToUser(String message){
		System.out.print(message);
	}
	
	public String readInput(){
		showToUser(INPUT_ARROW);
		return reader.nextLine();
	}
	
	public void displayText(){
		if (TextBuddy.textContent.isEmpty()) {
			showToUser(MESSAGE_EMPTY_DISPLAY);
		} else {
			showToUser(MESSAGE_DISPLAY_TEXT);
			for (int i = 1; i < TextBuddy.textContent.size()+1; i++) {
				showToUser(i + "   " + TextBuddy.getLine(i) + "\n");
			}
		}
	}
	
	public String getNewText() {
		showToUser(MESSAGE_ENTER_NEW_TEXT);
		return readInput();
	}
	
	public void showSearchResult(String word, ArrayList<String> result){
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

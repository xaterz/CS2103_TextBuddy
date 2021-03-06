package textbuddy.ce2;

import java.util.ArrayList;
import java.util.Arrays;

public class Logic {
	/*
	 * This limits the number of lines that can be stored in the text content.
	 * This is to avoid parsing very large line number string (which will generate an error).
	 */
	private final String MAX_NUMBER_OF_LINES = "999999999";
	
	//This is used to check the user's reply to a [y/n] prompt
	private final ArrayList<String> acceptableReplies = new ArrayList<String>(Arrays.asList("y", "yes", "n", "no"));
	
	
	String[] processCommand(String command){
		String[] commandParts = new String[2];
		String[] splitCommand = command.split(" ", 2);
		
		commandParts[0] = splitCommand[0].toLowerCase();
		if (splitCommand.length < 2) {
			commandParts[1] = "";
		} else {
			commandParts[1] = splitCommand[1];
		}
		return commandParts;
	}
	
	void executeCommand(String[] commandParts){
		String commandType = commandParts[0];
		String commandContent = commandParts[1];
		if (commandType.isEmpty()){
			TextBuddy.ui.showToUser(TextBuddy.ui.MESSAGE_EMPTY_COMMAND);
		} else {
			switch (commandType) {
				case "add" :
					if (commandContent.isEmpty()){
						TextBuddy.ui.showToUser(TextBuddy.ui.MESSAGE_EMPTY_ADD);
					} else {
						TextBuddy.conMan.addText(commandContent);
					}
					break;
				case "display" :
					TextBuddy.ui.displayText();
					break;
				case "update" :
					if (isValidLineNumber(commandContent)){
						int lineNumber = Integer.parseInt(commandContent);
						TextBuddy.conMan.updateText(lineNumber);
					}
					break;
				case "insert" :
					if (isValidLineNumber(commandContent)){
						int lineNumber = Integer.parseInt(commandContent);
						TextBuddy.conMan.insertText(lineNumber);
					}
					break;
				case "delete" :
					if (isValidLineNumber(commandContent)){
						int lineNumber = Integer.parseInt(commandContent);
						TextBuddy.conMan.deleteText(lineNumber);
					}
					break;
				case "clear" :
					TextBuddy.conMan.clearContent();
					break;
				case "save" :
					TextBuddy.saveContent();
					break;
				case "sort" :
					TextBuddy.conMan.sortContent();
					break;
				case "search" :
					TextBuddy.conMan.searchContent(commandContent);
					break;
				case "exit" :
					TextBuddy.exitProgram();
				default:
					TextBuddy.ui.showToUser(String.format(TextBuddy.ui.MESSAGE_UNRECOGNISED_COMMAND, commandType));
			}
		}
	}
	
	/**
	 * This method determines whether the user has indicated a valid line number in the command.
	 * A line number is valid if it is a positive integer that is not out of bound.
	 */
	boolean isValidLineNumber(String commandContent){
		if (commandContent.isEmpty()){
			TextBuddy.ui.showToUser(TextBuddy.ui.MESSAGE_LINE_NUMBER_EMPTY);
			return false;
		} else if (!IsPositiveInteger(commandContent)){
			TextBuddy.ui.showToUser(String.format(TextBuddy.ui.MESSAGE_LINE_NUMBER_INVALID, commandContent));
			return false;
		} else if (isLineNumberOutOfBound(commandContent)){
			TextBuddy.ui.showToUser(String.format(TextBuddy.ui.MESSAGE_LINE_NUMBER_OUT_OF_BOUND, commandContent));
			return false;
		}
		return true;
	}

	boolean IsPositiveInteger(String commandContent) {
		char c = commandContent.charAt(0); //first character of command content
		if (c == '0'){
			return false;
		}
		for (int i = 0; i < commandContent.length(); i++) {
			c = commandContent.charAt(i);
			if (!Character.isDigit(c)){
				return false;
			}
		}
		return true;
	}
	
	boolean isLineNumberOutOfBound(String lineNumberString) {
		if (lineNumberString.length() > MAX_NUMBER_OF_LINES.length()){
			return true;
		}
		int lineNumber = Integer.parseInt(lineNumberString);
		int totalNumberOfLines = TextBuddy.conMan.content.size();
		if (lineNumber > totalNumberOfLines){
			return true;
		}
		return false;
	}
	
	/**
	 * This method checks whether the user replies yes or no to a yes/no question prompted by the program.
	 * @return true if user replies yes.
	 */
	boolean isReplyYes() {
		String userReply = TextBuddy.ui.readInput().toLowerCase();
		
		while (!acceptableReplies.contains(userReply)) {
			TextBuddy.ui.showToUser(TextBuddy.ui.MESSAGE_INVALID_REPLY);
			userReply = TextBuddy.ui.readInput();
		}
		
		return (userReply.equals("y") || userReply.equals("yes"));
	}
}

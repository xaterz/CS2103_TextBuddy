package textbuddy.ce2;

public class Logic {
	
	/*
	 * This limits the number of lines that can be stored in the text content.
	 * This is to avoid parsing very large line number string (which will generate an error).
	 */
	private static final String MAX_NUMBER_OF_LINES = "999999999";
	
	public String[] processCommand(String command){
		String[] commandParts = new String[2];
		String[] splitCommand = command.split(" ", 2);
		
		commandParts[0] = splitCommand[0];
		if (splitCommand.length < 2) {
			commandParts[1] = "";
		} else {
			commandParts[1] = splitCommand[1];
		}
		return commandParts;
	}
	
	public void executeCommand(String[] commandParts){
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
						TextBuddy.addText(commandContent);
					}
					break;
				case "display" :
					TextBuddy.ui.displayText();
					break;
				case "update" :
					if (isValidLineNumber(commandContent)){
						int lineNumber = Integer.parseInt(commandContent);
						TextBuddy.updateText(lineNumber);
					}
					break;
				case "insert" :
					if (isValidLineNumber(commandContent)){
						int lineNumber = Integer.parseInt(commandContent);
						TextBuddy.insertText(lineNumber);
					}
					break;
				case "delete" :
					if (isValidLineNumber(commandContent)){
						int lineNumber = Integer.parseInt(commandContent);
						TextBuddy.deleteText(lineNumber);
					}
					break;
				case "clear" :
					TextBuddy.clearContent();
					break;
				case "save" :
					TextBuddy.saveContent();
					break;
				case "sort" :
					TextBuddy.sortContent();
					break;
				case "search" :
					TextBuddy.searchContent(commandContent);
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
	public boolean isValidLineNumber(String commandContent){
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

	public boolean IsPositiveInteger(String commandContent) {
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
	
	public boolean isLineNumberOutOfBound(String lineNumberString) {
		if (lineNumberString.length() > MAX_NUMBER_OF_LINES.length()){
			return true;
		}
		int lineNumber = Integer.parseInt(lineNumberString);
		int totalNumberOfLines = TextBuddy.textContent.size();
		if (lineNumber > totalNumberOfLines){
			return true;
		}
		return false;
	}
	
}

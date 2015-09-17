package textbuddy.ce2;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * TextBuddy is a program that manipulates text in a text file.
 * User can create a new text file or read/update an existing text file.
 * The text contents are temporary stored in TextBuddy, where the user can make changes to the text.
 * 
 * User will be prompted to save the changes to the text file before exiting the program.
 * User may also save the changes to the file manually at any time using the save command.
 * 
 * This is preferred over saving after each user operation or saving periodically, 
 * because this method gives the user the freedom of deciding when to save the file.
 * User can also undo all changes made after the last save, by choosing not to save when exiting.
 * 
 * The commands available to the user are add, display, update, insert, delete, clear, save, and exit.
 * The command format is given by the example interaction below:

Welcome to TextBuddy!
Please enter a file name >> helloWorld
New file helloWorld.txt has been created!
TextBuddy is ready for use.
>> add Hello world!
Added 'Hello world!' to content!
>> add Goodbye world!
Added 'Goodbye world!' to content!
>> display
Displaying content:
1   Hello world!
2   Goodbye world!
>> update 2
Updating line 2 'Goodbye world!'...
Please enter a new text (Enter nothing to cancel) >> Bye bye world!
Line 2 of content has been updated to 'Bye bye world!'
>> insert 2
Inserting new text at line 2...
Please enter a new text (Enter nothing to cancel) >> See you soon world!
Inserted 'See you soon world!' into content at line 2.
>> display
Displaying content:
1   Hello world!
2   See you soon world!
3   Bye bye world!
>> delete 3
Deleted 'Bye bye world!' from content!
>> save
Would you like to save modified contents to helloWorld.txt? [y/n] >> y
Saving text to helloWorld.txt... Done!
>> clear
Are you sure you want to delete all contents? [y/n] >> y
All contents deleted!
>> display
There's nothing to display!
>> exit
Save modified contents to helloWorld.txt? [y/n] >> n
Contents not saved.
Exiting TextBuddy... Goodbye!

After TextBuddy exits, helloWorld.txt will look like this:
Hello world!
See you soon world!

 * @author Rufus
 */
public class TextBuddy {
	
	//This handles all the file-related operations. 
	public static FileManager fileManager = new FileManager();
	
	//This handles all operations involving display and getting user input. 
	public static UserInterface ui = new UserInterface();
	
	//This temporarily stores the modified text before it is being saved (written) to the text file.
	public static ArrayList<String> textContent = new ArrayList<String>();
	
	//These store the user command after it has been split into 2 parts, 'type' and 'content'.
	static String commandType = ""; 
	static String commandContent = "";
	
	/*
	 * This limits the number of lines that can be stored in the text content.
	 * This is to avoid parsing very large line number string (which will generate an error).
	 */
	private static final String MAX_NUMBER_OF_LINES = "999999999";
	
	
	public static void main(String[] fileName) {
		ui.showToUser(ui.MESSAGE_WELCOME);
		fileManager.setUpFile(fileName);
		
		while (true){
			String command = ui.readInput();
			processCommand(command);
			executeCommand();
		}
	}
	
	static void processCommand(String command){
		String[] commandParts = command.split(" ", 2);
		commandType = commandParts[0];
		if (commandParts.length > 1){
			commandContent = commandParts[1];
		} else {
			commandContent= "";
		}
	}
	
	static void executeCommand(){
		if (commandType.isEmpty()){
			ui.showToUser(ui.MESSAGE_EMPTY_COMMAND);
		} else {
			switch (commandType) {
				case "add" :
					if (commandContent.isEmpty()){
						ui.showToUser(ui.MESSAGE_EMPTY_ADD);
					} else {
						addText(commandContent);
					}
					break;
				case "display" :
					ui.displayText();
					break;
				case "update" :
					if (isValidLineNumber(commandContent)){
						int lineNumber = Integer.parseInt(commandContent);
						updateText(lineNumber);
					}
					break;
				case "insert" :
					if (isValidLineNumber(commandContent)){
						int lineNumber = Integer.parseInt(commandContent);
						insertText(lineNumber);
					}
					break;
				case "delete" :
					if (isValidLineNumber(commandContent)){
						int lineNumber = Integer.parseInt(commandContent);
						deleteText(lineNumber);
					}
					break;
				case "clear" :
					clearContent();
					break;
				case "save" :
					saveContent();
					break;
				case "sort" :
					sortContent();
					break;
				case "search" :
					searchContent(commandContent);
					break;
				case "exit" :
					exitProgram();
				default:
					ui.showToUser(String.format(ui.MESSAGE_UNRECOGNISED_COMMAND, commandType));
			}
		}
	}
	
	static void addText(String newText){
		textContent.add(newText);
		ui.showToUser(String.format(ui.MESSAGE_TEXT_ADDED, newText));
	}
	
	/**
	 * This method determines whether the user has indicated a valid line number in the command.
	 * A line number is valid if it is a positive integer that is not out of bound.
	 */
	static boolean isValidLineNumber(String commandContent){
		if (commandContent.isEmpty()){
			ui.showToUser(ui.MESSAGE_LINE_NUMBER_EMPTY);
			return false;
		} else if (!IsPositiveInteger(commandContent)){
			ui.showToUser(String.format(ui.MESSAGE_LINE_NUMBER_INVALID, commandContent));
			return false;
		} else if (isLineNumberOutOfBound(commandContent)){
			ui.showToUser(String.format(ui.MESSAGE_LINE_NUMBER_OUT_OF_BOUND, commandContent));
			return false;
		}
		return true;
	}

	static boolean IsPositiveInteger(String commandContent) {
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
	
	static boolean isLineNumberOutOfBound(String lineNumberString) {
		if (lineNumberString.length() > MAX_NUMBER_OF_LINES.length()){
			return true;
		}
		int lineNumber = Integer.parseInt(lineNumberString);
		int totalNumberOfLines = textContent.size();
		if (lineNumber > totalNumberOfLines){
			return true;
		}
		return false;
	}
	
	static String getLine(int lineNumber){
		int index = lineNumber - 1;
		return textContent.get(index);
	}
	
	static void updateText(int lineNumber){
		int index = lineNumber - 1;
		ui.showToUser(String.format(ui.MESSAGE_UPDATING_TEXT, lineNumber, getLine(lineNumber)));
		String newText = ui.getNewText();
		if (newText.isEmpty()) {
			ui.showToUser(ui.MESSAGE_OPERATION_CANCELLED);
		} else {
			textContent.set(index, newText);
			ui.showToUser(String.format(ui.MESSAGE_TEXT_UPDATED, lineNumber, newText));
		}
	}
	
	static void insertText(int lineNumber){
		int index = lineNumber-1;
		ui.showToUser(String.format(ui.MESSAGE_INSERTING_TEXT, lineNumber));
		String newText = ui.getNewText();
		if (newText.isEmpty()) {
			ui.showToUser(ui.MESSAGE_OPERATION_CANCELLED);
		} else {
			textContent.add(index, newText);
			ui.showToUser(String.format(ui.MESSAGE_TEXT_INSERTED, newText, lineNumber));
		}
	}
	
	static void deleteText(int lineNumber){
		int index = lineNumber-1;
		ui.showToUser(String.format(ui.MESSAGE_TEXT_DELETED, getLine(lineNumber)));
		textContent.remove(index);
	}
	
	static void clearContent() {
		ui.showToUser(ui.MESSAGE_CLEAR_CONFIRM);
		if (isReplyYes()) {
			textContent.clear();
			ui.showToUser(ui.MESSAGE_ALL_CLEARED);
		} else {
			ui.showToUser(ui.MESSAGE_OPERATION_CANCELLED);
		}
	}
	
	static void saveContent(){
		ui.showToUser(String.format(ui.MESSAGE_SAVE_CONFIRM, fileManager.textFileName));
		if (isReplyYes()) {
			ui.showToUser(String.format(ui.MESSAGE_SAVING_CONTENT, fileManager.textFileName));
			fileManager.writeContentToFile(fileManager.textFile);
		} else {
			ui.showToUser(ui.MESSAGE_SAVE_CANCELLED);
		}
	}
	
	/**
	 * This method checks whether the user replies yes or no to a yes/no question prompted by the program.
	 * @return true if user replies yes.
	 */
	static boolean isReplyYes() {
		String userReply = ui.readInput();
		while (!userReply.equalsIgnoreCase("y") && !userReply.equalsIgnoreCase("n")
				&& !userReply.equalsIgnoreCase("yes") && !userReply.equalsIgnoreCase("no")) {
			ui.showToUser(ui.MESSAGE_INVALID_REPLY);
			userReply = ui.readInput();
		}
		return (userReply.equals("y") || userReply.equals("yes"));
	}
	
	static void sortContent(){
		//This comparator ensures that content is sorted alphabetically.
		Comparator<String> compareLine = new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		};	
		
		textContent.sort(compareLine);
		ui.showToUser(ui.MESSAGE_CONTENT_SORTED);
	}
	
	static ArrayList<String> searchContent(String word){
		String optimisedWord = optimiseTextForSearch(word);
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 1; i < textContent.size()+1; i++) {
			String line = getLine(i);
			String optimsedLine = optimiseTextForSearch(line);
			if (optimsedLine.contains(optimisedWord)) {
				result.add(line);
			}
		}
		ui.showSearchResult(word, result);
		return result;
	}
	
	/**
	 * This method optimises the text for search by 
	 * 1) adding spaces to the ends, so that a line must contain the full word to be valid
	 * 2) converting the characters to lower cases, so that cases are ignored
	 * 3) removing all special characters that are not alphanumeric
	 */
	static String optimiseTextForSearch(String text){
		return " " + text.toLowerCase().replaceAll("[^a-zA-Z0-9 ]+","") + " ";
	}
	
	static void exitProgram(){
		saveContent(); //prompts user to save content to file before exiting
		ui.showToUser(ui.MESSAGE_GOODBYE);
		System.exit(0);
	}
}





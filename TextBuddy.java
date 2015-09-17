package textbuddy.ce2;

/**
 * TextBuddy is a program that manipulates text in a text file.
 * User can create a new text file or read/update an existing text file.
 * The text content are temporary stored in TextBuddy, where the user can make changes to the text.
 * 
 * User will be prompted to save the changes to the text file before exiting the program.
 * User may also save the changes to the file manually at any time using the save command.
 * 
 * This is preferred over saving after each user operation or saving periodically, 
 * because this method gives the user the freedom of deciding when to save the file.
 * User can also undo all changes made after the last save, by choosing not to save when exiting.
 * 
 * The commands available to the user are add, display, update, insert, delete, clear, sort, search, save, and exit.
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
Please enter a new text (Enter nothing to cancel) >> Welcome world!
Inserted 'Welcome world!' into content at line 2.
>> display
Displaying content:
1   Hello world!
2   Welcome world!
3   Bye bye world!
>> sort
Content has been sorted!
>> display
Displaying content:
1   Bye bye world!
2   Hello world!
3   Welcome world!
>> delete 1
Deleted 'Bye bye world!' from content!
>> display
Displaying content:
1   Hello world!
2   Welcome world!
>> search hello
Showing search results for 'hello':
1   Hello world!
>> save
Would you like to save modified content to helloWorld.txt? [y/n] >> y
Saving text to helloWorld.txt... Done!
>> clear
Are you sure you want to delete all content? [y/n] >> y
All content deleted!
>> display
There's nothing to display!
>> exit
Save modified content to helloWorld.txt? [y/n] >> n
Content not saved.
Exiting TextBuddy... Goodbye!

After TextBuddy exits, helloWorld.txt will look like this:
Hello world!
Welcome world!

 * @author Rufus
 */
public class TextBuddy {
	
	//This is used to manipulate and store content within TextBuddy.
	public static ContentManager conMan = new ContentManager();
	
	//This handles all the file-related operations. 
	public static FileManager fileMan = new FileManager();
	
	//This is the 'brain' of the program that processes user input and makes decision for the program.
	public static Logic logic = new Logic();
	
	//This handles all operations involving getting user input and displaying output to user. 
	public static UserInterface ui = new UserInterface();
	
	
	public static void main(String[] fileName) {
		ui.showToUser(ui.MESSAGE_WELCOME);
		fileMan.setUpFile(fileName);
		
		while (true){
			String command = ui.readInput();
			String[] commandParts = logic.processCommand(command);
			logic.executeCommand(commandParts);
		}
	}
	
	static void saveContent(){
		ui.showToUser(String.format(ui.MESSAGE_SAVE_CONFIRM, fileMan.textFileName));
		if (logic.isReplyYes()) {
			ui.showToUser(String.format(ui.MESSAGE_SAVING_CONTENT, fileMan.textFileName));
			fileMan.writeContentToFile(fileMan.textFile);
		} else {
			ui.showToUser(ui.MESSAGE_SAVE_CANCELLED);
		}
	}
	
	static void exitProgram(){
		saveContent(); //prompts user to save content to file before exiting
		ui.showToUser(ui.MESSAGE_GOODBYE);
		System.exit(0);
	}
}





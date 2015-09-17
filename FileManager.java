package textbuddy.ce2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * FileManager handles all the operations that directly involve the text file.
 * It can create, load from, or write to a text file of the given file name.
 * @author Rufus
 */
public class FileManager {
	
	//This is the name of the text file as entered by the user.
	String textFileName = "";
	
	//This is the text file that will be initialized with the name given by the user.
	File textFile;
	
	/**
	 * This method takes in a file name and creates a new text file of that name.
	 * If a file of the same name exists, it will loads the text contents from that file into TextBuddy. 
	 * @param name of the file as entered by the user.
	 */
	void setUpFile(String[] fileName) {
		getFileName(fileName);
		
		//Append '.txt' extension if absent 
		if (!textFileName.endsWith(".txt")) {
			textFileName += ".txt";
		}
		
		/*
		 * Creates a new text file that will store the modified content.
		 * If a file of the same name already exists in the directory, this will overwrite that file 
		 * once it is saved.
		 */
		textFile = new File(textFileName);
		
		if (hasFileInDirectory(textFile)){
			System.out.println("Existing file " + textFileName + " found.");
			System.out.print("Loading " + textFileName + "'s contents into TextBuddy... ");
			loadContentFromFile(textFileName);
			System.out.println("Done!");
		} else {
			System.out.println("New file " + textFileName + " has been created!");
		}
		System.out.println("TextBuddy is ready for use.");
	}
	
	/**
	 * This method gets the name of the file from the user, which can be entered when the user opens TextBuddy
	 * via the command shell, or when the program prompts the user to enter a file name when it first starts up.
	 * @param fileName that was entered by the user when TextBuddy is opened
	 */
	void getFileName(String[] fileName) {
		if (fileName.length == 0) { //if user did not indicate a file name when opening Textbuddy
			System.out.print("Please enter a file name ");
			textFileName = TextBuddy.ui.readInput();
		} else {
			for (int i = 0; i < fileName.length; i++) {
				textFileName += fileName[i];
			}
		}
	}
	
	boolean hasFileInDirectory(File file) {
		return (file.exists() && !file.isDirectory());
	}
	
	void loadContentFromFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
		    try {
				while ((line = reader.readLine()) != null) {
					TextBuddy.conMan.content.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	void writeContentToFile(File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			for (int i = 0; i < TextBuddy.conMan.content.size(); i++) {
				writer.write(TextBuddy.conMan.content.get(i) + "\n");
			}
			writer.close();
			System.out.println("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

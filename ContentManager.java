package textbuddy.ce2;

import java.util.ArrayList;
import java.util.Comparator;

public class ContentManager {
	//This temporarily stores the modified text before it is being saved (written) to the text file.
	ArrayList<String> content = new ArrayList<String>();
	
	String getLine(int lineNumber){
		int index = lineNumber - 1;
		return content.get(index);
	}
	
	void addText(String newText){
		content.add(newText);
		TextBuddy.ui.showToUser(String.format(TextBuddy.ui.MESSAGE_TEXT_ADDED, newText));
	}

	void updateText(int lineNumber){
		int index = lineNumber - 1;
		TextBuddy.ui.showToUser(String.format(TextBuddy.ui.MESSAGE_UPDATING_TEXT, lineNumber, getLine(lineNumber)));
		String newText = TextBuddy.ui.getNewText();
		if (newText.isEmpty()) {
			TextBuddy.ui.showToUser(TextBuddy.ui.MESSAGE_OPERATION_CANCELLED);
		} else {
			content.set(index, newText);
			TextBuddy.ui.showToUser(String.format(TextBuddy.ui.MESSAGE_TEXT_UPDATED, lineNumber, newText));
		}
	}
	
	void insertText(int lineNumber){
		int index = lineNumber-1;
		TextBuddy.ui.showToUser(String.format(TextBuddy.ui.MESSAGE_INSERTING_TEXT, lineNumber));
		String newText = TextBuddy.ui.getNewText();
		if (newText.isEmpty()) {
			TextBuddy.ui.showToUser(TextBuddy.ui.MESSAGE_OPERATION_CANCELLED);
		} else {
			content.add(index, newText);
			TextBuddy.ui.showToUser(String.format(TextBuddy.ui.MESSAGE_TEXT_INSERTED, newText, lineNumber));
		}
	}
	
	void deleteText(int lineNumber){
		int index = lineNumber-1;
		TextBuddy.ui.showToUser(String.format(TextBuddy.ui.MESSAGE_TEXT_DELETED, getLine(lineNumber)));
		content.remove(index);
	}
	
	void clearContent() {
		TextBuddy.ui.showToUser(TextBuddy.ui.MESSAGE_CLEAR_CONFIRM);
		if (TextBuddy.logic.isReplyYes()) {
			content.clear();
			TextBuddy.ui.showToUser(TextBuddy.ui.MESSAGE_ALL_CLEARED);
		} else {
			TextBuddy.ui.showToUser(TextBuddy.ui.MESSAGE_OPERATION_CANCELLED);
		}
	}
	
	void sortContent(){
		//This comparator ensures that content is sorted alphabetically.
		Comparator<String> compareLine = new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.toLowerCase().compareTo(s2.toLowerCase());
			}
		};	
		
		content.sort(compareLine);
		TextBuddy.ui.showToUser(TextBuddy.ui.MESSAGE_CONTENT_SORTED);
	}
	
	ArrayList<String> searchContent(String word){
		String optimisedWord = optimiseTextForSearch(word);
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 1; i < content.size()+1; i++) {
			String line = getLine(i);
			String optimisedLine = optimiseTextForSearch(line);
			if (optimisedLine.contains(optimisedWord)) {
				result.add(line);
			}
		}
		TextBuddy.ui.showSearchResult(word, result);
		return result;
	}
	
	/**
	 * This method optimises the text for search by 
	 * 1) adding spaces to the ends, so that a line must contain the full word to be valid
	 * 2) converting the characters to lower cases, so that cases are ignored
	 * 3) removing all special characters that are not alphanumeric
	 */
	String optimiseTextForSearch(String text){
		return " " + text.toLowerCase().replaceAll("[^a-zA-Z0-9 ]+","") + " ";
	}
}

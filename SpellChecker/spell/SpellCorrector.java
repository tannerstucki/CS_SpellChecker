package spell;

import java.util.*;
import java.io.*;
import java.lang.*;

public class SpellCorrector implements ISpellCorrector{
	//data members
	private Trie launch = new Trie();
	Trie matchTester = new Trie();
	//private Set<String> editSet = new HashSet<String>();
	private List<String> editList = new ArrayList<>();
	private List<String> editList2 = new ArrayList<>();
	boolean Distance2 = false;
	//constructor
	public SpellCorrector(){}

	//functions
		public void useDictionary(String dictionaryFileName) throws IOException{
			//System.out.println(dictionaryFileName);
			Scanner scan = new Scanner(new File(dictionaryFileName));
			while(scan.hasNext()){
				String file_in = scan.next();
				if(isAlpha(file_in)){
					launch.add(file_in.toLowerCase());
				}
			}
			scan.close();

			//all this was to test equals
			/*Scanner scan2 = new Scanner(new File("tester2.txt"));
			while(scan2.hasNext()){
				String file_in2 = scan2.next();
				if(isAlpha(file_in2)){
					matchTester.add(file_in2.toLowerCase());
				}
			}
			scan2.close();
			System.out.println(launch.equals(matchTester));*/ //to test equals

			//System.out.print(launch.toString()); // to test toString
			//System.out.println(launch.find("chicken")); // to test find
			//suggestSimilarWord("dvk");
		}

		public boolean isAlpha(String name){
			return name.matches("[A-Za-z]+");
		}

	/**
	 * Suggest a word from the dictionary that most closely matches
	 * <code>inputWord</code>
	 * @param inputWord
	 * @return The suggestion or null if there is no similar word in the dictionary
	 */
	public String suggestSimilarWord(String inputWord){
		editList.clear();
		//editSet.clear();
		Distance2 = false;
		if (launch.find(inputWord.toLowerCase()) != null) {
			return inputWord.toLowerCase();
		}

		Deletion(inputWord);
		Transposition(inputWord);
		Alteration(inputWord);
		Insertion(inputWord);
		Distance2 = true;

		//System.out.println(editList.size());
		//System.out.println(editList);

		String replaceWord = checkList(editList);

		if (replaceWord == null) {
			editList2.clear();
			for (int i = 0; i < editList.size(); i++) {
				Deletion(editList.get(i));
				Transposition(editList.get(i));
				Alteration(editList.get(i));
				Insertion(editList.get(i));
			}
			//System.out.println(editList2.size());
			//System.out.println(editList2);
			replaceWord = checkList(editList2);
		}

		return replaceWord.toLowerCase();
	}

	public String checkList(List<String> editList){
		int curWordFreq = 0;
		String replaceWord = null;

		for (int i = 0; i < editList.size(); i++) {
		if (launch.find(editList.get(i)) != null) {
			if (curWordFreq < launch.find(editList.get(i)).getValue()) {
				curWordFreq = launch.find(editList.get(i)).getValue();
				replaceWord = editList.get(i);
			}
			/*System.out.println(launch.find(editList.get(i)));
			System.out.println(launch.find(editList.get(i)).getValue());
			System.out.println(editList.get(i));*/
			}
		}
		//System.out.println("\n" + replaceWord);
		return replaceWord;
	}

	public void Deletion(String inputWord){
		char[] input_array = inputWord.toCharArray();
		StringBuilder tempword = new StringBuilder();
		for (int i = 0; i < input_array.length; i++){
			tempword.setLength(0);
			tempword.append(input_array);
			tempword.deleteCharAt(i);
			if (Distance2 == false) {
				//editSet.add(tempword.toString());
				editList.add(tempword.toString());
			}
			else{
				editList2.add(tempword.toString());
			}
		}
	}

	public void Transposition(String inputWord){
		char[] input_array = inputWord.toCharArray();
		char tempChar;
		StringBuilder tempword = new StringBuilder();
		for (int i = 0; i < input_array.length - 1; i++){
			tempword.setLength(0);
			tempword.append(input_array);
			tempChar = input_array[i];
			tempword.setCharAt(i,input_array[i+1]);
			tempword.setCharAt(i+1,tempChar);
			if (Distance2 == false) {
				//editSet.add(tempword.toString());
				editList.add(tempword.toString());
			}
			else{
				editList2.add(tempword.toString());
			}
		}
	}

	public void Alteration(String inputWord){
		char[] input_array = inputWord.toCharArray();
		int tempChar;
		StringBuilder tempword = new StringBuilder();
		for (int i = 0; i < input_array.length; i++){
			for (int j = 0; j < 26 ; j++) {
				tempword.setLength(0);
				tempword.append(input_array);
				tempChar = 'a' + j;
				if ((char)tempChar != input_array[i]) {
					tempword.setCharAt(i,(char)tempChar);
					if (Distance2 == false) {
						//editSet.add(tempword.toString());
						editList.add(tempword.toString());
					}
					else{
						editList2.add(tempword.toString());
					}
				}
			}
		}
	}

	public void Insertion(String inputWord){
		char[] input_array = inputWord.toCharArray();
		int tempChar;
		StringBuilder tempword = new StringBuilder();
		for (int i = 0; i < input_array.length + 1; i++){
			for (int j = 0; j < 26 ; j++) {
				tempword.setLength(0);
				tempword.append(input_array);
				tempChar = 'a' + j;
				tempword.insert(i,(char)tempChar);
				if (Distance2 == false) {
					//editSet.add(tempword.toString());
					editList.add(tempword.toString());
				}
				else{
					editList2.add(tempword.toString());
				}
			}
		}
	}
}
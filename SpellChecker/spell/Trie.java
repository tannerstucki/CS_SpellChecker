package spell;

import java.util.*;
import java.io.*;
import java.lang.*;

public class Trie implements ITrie{
	
	//data members
	Node root = new Node();
	int word_count;
	int node_count = 1;
	boolean trieMatch = true;
	boolean wordFound = true;


	//constructor
	public Trie (){

	}

	//functions
		public void add(String word){
			char [] word_array = word.toCharArray();
			Node curNode = root;

			for (int i = 0; i < word_array.length; i++) {
				int index = word_array[i] - 'a';
				if (curNode.node_array[index] == null) {
					curNode.node_array[index] = new Node();
					node_count++;
				}
				curNode = curNode.node_array[index];
			}
			if (curNode.frequency == 0) {
				word_count++;
			}
			curNode.frequency++;
			/*if (word_count < 25) {
				System.out.println("This word: " + word);
				System.out.println("Word Count: " + word_count);
				System.out.println("Node Count: " + node_count);
				System.out.println("This word's frequency: " + curNode.frequency + "\n");	
			}*/
		}
	
	/**
	 * Searches the trie for the specified word
	 * 
	 * @param word The word being searched for
	 * 
	 * @return A reference to the trie node that represents the word,
	 * 			or null if the word is not in the trie
	 */
	public Node find(String word){ // for testing
		Node curNode = root;
		wordFound = true;
		char [] word_array = word.toCharArray();
		for (int i = 0; i < word_array.length; i++) {
			int charInt = word_array[i] - 'a';
			if (curNode.node_array[charInt] == null) {
				return null;
			}
			curNode = curNode.node_array[charInt];
		}

		if (curNode.frequency != 0) {
			return curNode;
		}
		return null;
	}

	//this method works but it needs to be changed to return as above
	/*public boolean findHelper(String word){ // for testing
		Node curNode = root;
		wordFound = true;
		char [] word_array = word.toCharArray();
		for (int i = 0; i < word_array.length; i++) {
			int charInt = word_array[i] - 'a';
			if (curNode.node_array[charInt] == null) {
				return false;
			}
			curNode = curNode.node_array[charInt];
		}

		if (curNode.frequency != 0) {
			return true;
		}
		return false;
	}*/
	
	/**
	 * Returns the number of unique words in the trie
	 * 
	 * @return The number of unique words in the trie
	 */
	public int getWordCount(){
		return word_count;
	}
	
	/**
	 * Returns the number of nodes in the trie
	 * 
	 * @return The number of nodes in the trie
	 */
	public int getNodeCount(){
		return node_count;
	}
	
	/**
	 * The toString specification is as follows:
	 * For each word, in alphabetical order:
	 * <word>\n
	 */
	@Override
	public String toString(){
		StringBuilder curword = new StringBuilder();
		StringBuilder output = new StringBuilder();
		toStringHelper(root, curword, output);
		//System.out.println(output.toString());
		return output.toString();
	}

	public void toStringHelper(Node curNode, StringBuilder curword, StringBuilder output){
		if (curNode.frequency > 0){
			output.append(curword.toString() + "\n");
		}

		Node tempNode = new Node();
		//if (curNode.node_array != null){
			for (int i = 0; i < curNode.node_array.length; i++) {
				if (curNode.node_array[i] != null) {
					int temp = 'a' + i;
					char tempChar = (char) temp;
					curword.append(tempChar);
					tempNode = curNode.node_array[i];
					toStringHelper(tempNode,curword,output);
					curword.setLength(curword.length() - 1);
				}
		//}
			}
	}
	
	@Override
	public int hashCode(){
		//temp return for testing
		return 37 * word_count * node_count;
	}
	
	@Override
	public boolean equals(Object o){
	//public boolean equals(Trie o){ //for testing this method with a trie object
		trieMatch = true;

		if (!(o instanceof Trie)) {
			return false;
		}

		Trie compare = (Trie) o;
		Node ogNode = root;
		Node newNode = compare.root;

		if (word_count != compare.word_count) {
			return false;
		}
		if (node_count != compare.node_count) {
			return false;
		}

		return equalsHelper(ogNode, newNode);
		//return true;
	}

	public boolean equalsHelper(Node ogCurNode, Node newCurNode){
		for (int i = 0; i < ogCurNode.node_array.length; i++) {
			if (ogCurNode.node_array[i] != null) {
				if (newCurNode.node_array[i] == null) {
					trieMatch = false;
				}
				Node ogTempNode = ogCurNode.node_array[i];
				Node newTempNode = newCurNode.node_array[i];
				equalsHelper(ogTempNode, newTempNode);
			}
		}
		return trieMatch;
		//return true;
	}

	/**
	 * Your trie node class should implement the ITrie.INode interface
	 */
	public class Node implements ITrie.INode{
	
		/**
		 * Returns the frequency count for the word represented by the node
		 * 
		 * @return The frequency count for the word represented by the node
		 */

		private int frequency;
		private Node[] node_array = new Node[26];


		Node() {
			frequency = 0;
		}

		public int getValue(){
			return frequency;
		}
	}
}
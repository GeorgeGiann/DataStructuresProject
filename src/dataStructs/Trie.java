package dataStructs;

import java.util.Date;

/**
 * Description: Class implementing Trie structure
 * @author Gsquared
 *
 */
public class Trie {
	
	public static int comparisons;
	private TrieNode root;
	
	/**
	 * Description: Constructor
	 */
	Trie(){
		root = new TrieNode();
	}
	
	/**
	 * Description: method for adding a new string in the trie
	 * @param word
	 * @return TrieNode
	 */
	private TrieNode addString(String word){
		TrieNode currentNode = root;
		int wordlength = word.length();
		for(int i=0;i<wordlength;i++){
			int index = Character.toLowerCase(word.charAt(i)) -'a';
			if(currentNode.link[index]==null) currentNode.link[index] = new TrieNode(word.charAt(i));
			currentNode =currentNode.link[index];
		}
		return currentNode;
	}
	
	/**
	 * Description: Method for adding information in a Trie node
	 * @param word
	 * @param hname
	 * @param rdate
	 * @param rdur
	 */
	public void addInfo(String word,String hname,Date rdate,int rdur){
		TrieNode node = addString(word);
		node.addInfo(hname, rdate, rdur);
	}
	
	/**
	 * Description: Method implementing search via trie 
	 * @param word
	 * @return TrieNode
	 */
	public TrieNode searchString(String word){
		TrieNode currentNode = root;
		int wordlength = word.length();
		for(int i=0;i<wordlength;i++){
			comparisons++;
			int index = Character.toLowerCase(word.charAt(i))-'a';
			if(currentNode.link[index]==null){
				return null;
			}
			currentNode =currentNode.link[index];
		}
		return currentNode;
	}
	
}

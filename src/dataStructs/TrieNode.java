package dataStructs;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Description: class describing the node of a trie
 * @author george
 *
 */
public class TrieNode {
	
	private static final int ABsize = 26;
	char content;
	TrieNode[] link;
	ArrayList<String> hotelname = new ArrayList<String>();
	ArrayList<Date> date = new ArrayList<Date>();
	List<Integer> duration = new ArrayList<>();
	
	/**
	 * Description: Constructor
	 */
	TrieNode(){
		this.link = new TrieNode[ABsize];
		this.content = ' ';
	}
	
	/**
	 * Description: Method of incrementing a trie node
	 * @param ch
	 */
	public TrieNode(char ch){
		this.link = new TrieNode[ABsize];
		this.content = ch;
	}
	
	/**
	 * Description: Add info in a trie node
	 * @param hname
	 * @param rdate
	 * @param rdur
	 */
	public void addInfo(String hname,Date rdate,int rdur){
		this.hotelname.add(hname);
		this.date.add(rdate);
		this.duration.add(rdur);
	}
}

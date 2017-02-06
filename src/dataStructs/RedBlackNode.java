package dataStructs;

/**
 * Description class describing a node of Red Black tree
 * @author Gsquared
 *
 */
public class RedBlackNode {
	// define colors constant values
		public static final int BLACK = 0;
		public static final int RED = 1;
	//define nodes variables
	public int KEY ;
	
	RedBlackNode parent;
	RedBlackNode left;
	RedBlackNode right;
	
	public int numLeft = 0;
	public int numRight = 0;
	public int color;
	
	/**
	 * Description Constructor
	 */
	RedBlackNode(){
        color = BLACK;
        numLeft = 0;
        numRight = 0;
        parent = null;
        left = null;
        right = null;
    }
	
	/**
	 * Description Constructor
	 * @param key
	 */
	RedBlackNode(int key){
        this();
        this.KEY = key;
	}
	
}

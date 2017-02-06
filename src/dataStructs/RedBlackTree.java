package dataStructs;

/**
 * Description Class implementing Red Black Tree
 * @author Gsquared
 *
 */
public class RedBlackTree {

	public static int comparisons;
	
	private RedBlackNode nilNode = new RedBlackNode();
	private RedBlackNode root = nilNode;
	
	/**
	 * Description Constructor
	 */
	public RedBlackTree(){
		root.left = nilNode;
		root.right = nilNode;
		root.parent = nilNode;
	}
	
	private boolean isNil(RedBlackNode node){

		// return appropriate value
		return node == nilNode;

	}
	
	/**
	 * Description Method implementing left rotation
	 * @param RotNode
	 */
	private void leftRotation(RedBlackNode RotNode){
		
		
		RedBlackNode y = RotNode.right;
		RotNode.right = y.left;
		
		//update numleft and num right values undexr RotNode
		if (isNil(RotNode.left) && isNil(RotNode.right.left)){
			RotNode.numLeft = 0;
			RotNode.numRight = 0;
			RotNode.right.numLeft = 1;
		} else if (isNil(RotNode.left) && !isNil(RotNode.right.left)){
			RotNode.numLeft = 0;
			RotNode.numRight = 1 + RotNode.right.left.numLeft +
								RotNode.right.left.numRight;
			RotNode.right.numLeft = 2 + RotNode.right.left.numLeft +
								RotNode.right.left.numRight;
		}else if (!isNil(RotNode.left) && isNil(RotNode.right.left)){
			RotNode.numRight = 0;
			RotNode.right.numLeft = 2 + RotNode.left.numLeft + RotNode.left.numRight;
		}else{
			RotNode.numRight = 1 + RotNode.right.left.numLeft +
								RotNode.right.left.numRight;
			RotNode.right.numLeft = 3 + RotNode.left.numLeft + RotNode.left.numRight +
								RotNode.right.left.numLeft + RotNode.right.left.numRight;
		}
				
		//left rotate
		if (!isNil(y.left))	y.left.parent = RotNode;
		y.parent = RotNode.parent;
		
		if (isNil(RotNode.parent)) root = y; // x's parent is nul
		else if (RotNode.parent.left == RotNode) RotNode.parent.left = y; // x is the left child of it's parent
		else RotNode.parent.right = y; // x is the right child of it's parent.
		
		y.left = RotNode;
		RotNode.parent = y;
		
		
	}//end of leftRotation
	
	/**
	 * Description Method implementing Right Rotation
	 * @param RotNode
	 */
	private void rightRotation(RedBlackNode RotNode){
		
		System.out.println("Right rotation");
		RedBlackNode y = RotNode.left;
		RotNode.left = y.right;
		
		if (isNil(RotNode.right) && isNil(RotNode.left.right)){
			RotNode.numRight = 0;
			RotNode.numLeft = 0;
			RotNode.left.numRight = 1;
		}
		//  Rotnode.left.right also exists in addition to Case 1
		else if (isNil(RotNode.right) && !isNil(RotNode.left.right)){
			RotNode.numRight = 0;
			RotNode.numLeft = 1 + RotNode.left.right.numRight +
					RotNode.left.right.numLeft;
			RotNode.left.numRight = 2 + RotNode.left.right.numRight +
					RotNode.left.right.numLeft;
		}
		//  RotNode.right also exists in addition to first case
		else if (!isNil(RotNode.right) && isNil(RotNode.left.right)){
			RotNode.numLeft = 0;
			RotNode.left.numRight = 2 + RotNode.right.numRight +RotNode.right.numLeft;

		}		
		else{ //  RotNode.right & RotNode.left.right exist in addition to first case
			RotNode.numLeft = 1 + RotNode.left.right.numRight +
					RotNode.left.right.numLeft;
			RotNode.left.numRight = 3 + RotNode.right.numRight +
					RotNode.right.numLeft +
			RotNode.left.right.numRight + RotNode.left.right.numLeft;
		}
		
		//rotate right
		if (!isNil(y.right)) y.right.parent = RotNode;
		y.parent = RotNode.parent;
		
		if (isNil(RotNode.parent)) root = y;
		else if (RotNode.parent.right == RotNode) RotNode.parent.right = y;
		else  RotNode.parent.left = y;
		
		y.right = RotNode;
		RotNode.parent = y;
		
		//update numleft and num right values under RotNode
		if (isNil(RotNode.right) && isNil(RotNode.left.right)){
			RotNode.numRight = 0;
			RotNode.numLeft = 0;
			RotNode.left.numRight = 1;
		} else if (isNil(RotNode.right) && !isNil(RotNode.left.right)){
			RotNode.numRight = 0;
			RotNode.numLeft = 1 + RotNode.left.right.numRight +	RotNode.left.right.numLeft;
			RotNode.left.numRight = 2 + RotNode.left.right.numRight + RotNode.left.right.numLeft;
		} else if (!isNil(y.right) && isNil(y.left.right)){
			RotNode.numLeft = 0;
			RotNode.left.numRight = 2 + RotNode.right.numRight +RotNode.right.numLeft;

		} else{
			RotNode.numLeft = 1 + RotNode.left.right.numRight + RotNode.left.right.numLeft;
			RotNode.left.numRight = 3 + RotNode.right.numRight + RotNode.right.numLeft +
					RotNode.left.right.numRight + RotNode.left.right.numLeft;
			}	
	}// end of rightRotation
	
	/**
	 * Description Method implementing search via RedBlack tree
	 * @param key
	 * @return RedBlackNode
	 */
	public RedBlackNode search(int key){
		RedBlackNode current = root;
		while (!isNil(current)){
			comparisons++;
			// If we have found a node with a key equal to key
			if (current.KEY==key) return current;
			// go  right 
			else if (current.KEY < key ) current = current.right;				
			// go left 
			else current = current.left;
		}
		return null;
	}
	
	/**
	 * Description Insertion using a key value
	 * @param key
	 */
	public void insert(int key) {
        insertNode(new RedBlackNode(key));
    }
	
	/**
	 * Description Insertion of a new node
	 * @param newNode
	 */
	private void insertNode(RedBlackNode newNode){
		
		RedBlackNode y = nilNode;// y node is initialised to Nil. used for the new node's position
		RedBlackNode x = root; // x node is the node from which the search starts
		//Run tree from root to leaf and update numLeft or numRight
		while (!isNil(x)){
			y = x;
			// if z.key is < than the current key, go left
			if (newNode.KEY < x.KEY ){ 
				// Update x.numLeft as z is < than x
				x.numLeft++;
				x = x.left;
			}// else z.key >= x.key so go right.
			else{	
				// Update x.numGreater as z is => x
				x.numRight++;
				x = x.right;
			}
		}
		// insert z and update values above it
		newNode.parent = y; // y is new nodes parent
		//Place new node left or right to parent y 
		if (isNil(y))
			root = newNode;
		else if (newNode.KEY< y.KEY)
			y.left = newNode;
		else
			y.right = newNode;
		
		// Initialize z's children to nil and z's color to red
		newNode.left = nilNode;
		newNode.right = nilNode;
		newNode.color = RedBlackNode.RED;
		
		fixTreeInsertion(newNode);
	}//end insertNode
	
	/**
	 * Description fix tree balance after insertion
	 * @param newNode
	 */
	private void fixTreeInsertion(RedBlackNode newNode){
		RedBlackNode y = nilNode;
		while (newNode.parent.color == RedBlackNode.RED){

			if(newNode.parent == root) {
				break;
			}
				
			// If newnode's parent is the the left child of it's parent.
			if (newNode.parent == newNode.parent.parent.left){

				// Initialize y to newnode 's cousin
				y = newNode.parent.parent.right;
 
				if (y.color == RedBlackNode.RED){ //if y is red...change color
					newNode.parent.color = RedBlackNode.BLACK;
					y.color = RedBlackNode.BLACK;
					newNode.parent.parent.color = RedBlackNode.RED;
					newNode = newNode.parent.parent;
				}
				else if (newNode == newNode.parent.right){// y is black & newnode is a right child

					// left Rotation around newnode's parent
					newNode = newNode.parent;
					System.out.println("a "+newNode.KEY);
					leftRotation(newNode);
				}				
				else{					// y is black & newnode is a left child
					// change color and rotate round newnode's grandpa
					newNode.parent.color = RedBlackNode.BLACK;
					newNode.parent.parent.color = RedBlackNode.RED;
					rightRotation(newNode.parent.parent);
				}
			}			
			else{ //  newnode's parent is the right child of it's parent.

				// Initialize y to newnode's cousin
				y = newNode.parent.parent.left;

				//  if y is red...change color
				if (y.color == RedBlackNode.RED){
					newNode.parent.color = RedBlackNode.BLACK;
					y.color = RedBlackNode.BLACK;
					newNode.parent.parent.color = RedBlackNode.RED;
					newNode = newNode.parent.parent;
				}				
				else if (newNode == newNode.parent.left){ // y is black and newnode is a left child
					// rightRotate around newnode's parent
					newNode = newNode.parent;
					rightRotation(newNode);
				}				
				else{			// y  is black and newnode is a right child
					// change color and rotate around newnode's grandpa
					newNode.parent.color = RedBlackNode.BLACK;
					newNode.parent.parent.color = RedBlackNode.RED;
					leftRotation(newNode.parent.parent);
				}
			}
			//root is always black
			root.color = RedBlackNode.BLACK;
		}
	}// end of fixTreeInsertion
	
	/**
	 * Description Return the size of the tree
	 * @return int
	 */
	public int size(){

		return root.numLeft + root.numRight + 1;
	}// end size()

	/**
	 * Description the root node of the tree
	 * @return int
	 */
	public RedBlackNode getRoot(){
		return this.root;
	}
	
}// end of class

package avl;

public class AVL {

  public Node root;

  private int size;

  public int getSize() {
    return size;
  }
//Joseph Frare
  /** find w in the tree. return the node containing w or
  * null if not found */
  public Node search(String w) {
    return search(root, w);
  }
  private Node search(Node n, String w) {
    if (n == null) {
      return null;
    }
    if (w.equals(n.word)) {
      return n;
    } else if (w.compareTo(n.word) < 0) {
      return search(n.left, w);
    } else {
      return search(n.right, w);
    }
  }

  /** insert w into the tree as a standard BST, ignoring balance */
  public void bstInsert(String w) {
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    bstInsert(root, w);
  }

  /* insert w into the tree rooted at n, ignoring balance
   * pre: n is not null */
  private void bstInsert(Node n, String w) {
   int cmp=w.compareTo(n.word);
   //checks for dups
   if (cmp==0){
    return;
   }
   
   if(cmp<0){
    if(n.left==null){
      n.left=new Node(w,n);
      size++;
      return;
    }
    bstInsert(n.left, w);
    return;
   }
    if(cmp>0){
    if(n.right==null){
      n.right=new Node(w,n);
      size++;
      return;
    }
    bstInsert(n.right, w);
    return;
   }
  }


  /** insert w into the tree, maintaining AVL balance
  *  precondition: the tree is AVL balanced and any prior insertions have been
  *  performed by this method. */
  public void avlInsert(String w) {
      if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    avlInsert(root, w);
  }

  /* insert w into the tree, maintaining AVL balance
   *  precondition: the tree is AVL balanced and n is not null */
  private void avlInsert(Node n, String w) {
    int cmp = w.compareTo(n.word);
    //we check the value/word to see what side to recure or place on
    if(cmp<0){
    if(n.left==null){
      n.left=new Node(w,n);
      size++;
      
    }
    else{avlInsert(n.left, w);}
    
    }
    else if(cmp>0){
    if(n.right==null){
      n.right=new Node(w,n);
      size++;
      
    }
    else{avlInsert(n.right, w);}
    
    }
    else{return;}
    //cleaning up
    updateHeight(n);
    rebalance(n);
  }


  /** do a left rotation: rotate on the edge from x to its right child.
  *  precondition: x has a non-null right child */
  public void leftRotate(Node x) {
    Node y = x.right;
    Node gamma = y.left;
    
    x.right=gamma;
   
  
    if(gamma != null){
      gamma.parent=x;
    }

    y.left=x;
    y.parent=x.parent;
    if(x.parent==null){
      root=y;
    }
    else if(x.parent.left==x){
        x.parent.left = y;}
      else{
        x.parent.right=y;
      }
      x.parent=y;
      updateHeight(x);
      updateHeight(y);
    }
  

  /** do a right rotation: rotate on the edge from x to its left child.
  *  precondition: y has a non-null left child */
  public void rightRotate(Node y) {
    if(y==null||y.left==null){return;}
  
    Node x =y.left;
    Node gamma = x.right;

    x.right=y;
    y.left=gamma;

    x.parent=y.parent;

    if(gamma != null){
      gamma.parent=y;
    }

    if(x.parent==null){
      root=x;
    }else{
      if(x.parent.left==y){
        x.parent.left=x;
      }
     else{ x.parent.right=x;
    }
    }
    y.parent=x;
    updateHeight(y);
    updateHeight(x);
  }

  /** rebalance a node N after a potentially AVL-violoting insertion.
  *  precondition: none of n's descendants violates the AVL property */
  public void rebalance(Node n) {
    if(n==null){
      return;
    }
  int leftH =(n.left==null)?-1:    n.left.height;
     int rightH= (n.right==null)?-1:   n.right.height;
     int balance=rightH-leftH;

    if(balance<-1){
      int childBal= getbalance(n.left);
      if(childBal<=0){
        rightRotate(n);
      }
      else{
        leftRotate(n.left);
        rightRotate(n);
      }
    }
    else if(balance>1){
      int childBal = getbalance(n.right);
      if(childBal>=0){
        leftRotate(n);
      }
      else{
        rightRotate(n.right);
        leftRotate(n);
    }
    }
    updateHeight(n);
    

  }
  private int getbalance(Node n){
    if (n==null){
      return 0;
    }
     int leftH =(n.left==null)?-1:    n.left.height;
     int rightH= (n.right==null)?-1:   n.right.height;
    return rightH-leftH;
  }
  private void updateHeight(Node n){
        if (n==null){
      return;
    }
     int leftH =(n.left==null)?-1:    n.left.height;
     int rightH= (n.right==null)?-1:   n.right.height;
     n.height=1+Math.max(leftH, rightH);
  }


  /** remove the word w from the tree */
  public void remove(String w) {
    remove(root, w);
  }

  /* remove w from the tree rooted at n */
  private void remove(Node n, String w) {
    return; // (enhancement TODO - do the base assignment first)
  }

  /** print a sideways representation of the tree - root at left,
  * right is up, left is down. */
  public void printTree() {
    printSubtree(root, 0);
  }
  private void printSubtree(Node n, int level) {
    if (n == null) {
      return;
    }
    printSubtree(n.right, level + 1);
    for (int i = 0; i < level; i++) {
      System.out.print("        ");
    }
    System.out.println(n);
    printSubtree(n.left, level + 1);
  }

  /** inner class representing a node in the tree. */
  public class Node {
    public String word;
    public Node parent;
    public Node left;
    public Node right;
    public int height;

    public String toString() {
      return word + "(" + height + ")";
    }

    /** constructor: gives default values to all fields */
    public Node() { }

    /** constructor: sets only word */
    public Node(String w) {
      word = w;
    }

    /** constructor: sets word and parent fields */
    public Node(String w, Node p) {
      word = w;
      parent = p;
    }

    /** constructor: sets all fields */
    public Node(String w, Node p, Node l, Node r) {
      word = w;
      parent = p;
      left = l;
      right = r;
    }
  }
}

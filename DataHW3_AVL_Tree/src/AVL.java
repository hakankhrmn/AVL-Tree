/******************************************************
 * AVL ADT.
 * Supported operations:
 * Insert
 * Delete
 * Find
 * Min
 * Max
 * Depth
 * Print
 ******************************************************/
public class AVL{
    private AVLNode root;      /* Pointer to the root of the tree */
    private int noOfNodes;     /* No of nodes in the tree */

    /*******************************************************
     * Constructor: Initializes the AVL
     *******************************************************/
    public AVL(){root=null; noOfNodes=0;}

    /*******************************************************
     * Returns a pointer to the root of the tree
     *******************************************************/
    public AVLNode Root(){return root;}

    /*******************************************************
     * Returns the number of nodes in the tree
     *******************************************************/
    public int NoOfNodes(){return noOfNodes;}

    /*******************************************************
     * Inserts the key into the AVL. Returns a pointer to
     * the inserted node
     *******************************************************/
    public AVLNode Insert(int key){
        // Fill this in
        AVLNode newNode=new AVLNode(key);
        int count=1;
        increaseCount(root,key);
        if (Find(key)!=null){
            count=Find(key).count;
        }else{
            noOfNodes++;
        }

        newNode.count=count;
        root=insertRecursive(root,newNode);


        return root;
    } //end-Insert

    /*******************************************************
     * Deletes the key from the tree (if found). Returns
     * 0 if deletion succeeds, -1 if it fails
     *******************************************************/
    public int Delete(int key){
        // Fill this in
        if (Find(key)!=null){
            AVLNode node;
            reduceCount(root, key);
            root=deleteRecursive(root,key);
            if (Find(key)==null){
                noOfNodes--;
            }

            return 0;
        }

        return -1;
    } //end-Delete

    /*******************************************************
     * Searches the AVL for a key. Returns a pointer to the
     * node that contains the key (if found) or NULL if unsuccessful
     *******************************************************/
    public AVLNode Find(int key){
        // Fill this in
        AVLNode node=root;
        node=findRecursive(node,key);
        return node;
    } //end-Find

    /*******************************************************
     * Returns a pointer to the node that contains the minimum key
     *******************************************************/
    public AVLNode Min(){
        // Fill this in
        AVLNode current = root;

        /* loop down to find the leftmost leaf */
        while (current.left != null)
            current = current.left;

        return current;
    } //end-Min

    /*******************************************************
     * Returns a pointer to the node that contains the maximum key
     *******************************************************/
    public AVLNode Max(){
        // Fill this in
        AVLNode current = root;

        /* loop down to find the leftmost leaf */
        while (current.right != null)
            current = current.right;

        return current;
    } //end-Max

    /*******************************************************
     * Returns the depth of tree. Depth of a tree is defined as
     * the depth of the deepest leaf node. Root is at depth 0
     *******************************************************/
    public int Depth(){
        // Fill this in
        return height(root)-1;
    } //end-Depth

    /*******************************************************
     * Performs an inorder traversal of the tree and prints [key, count]
     * pairs in sorted order
     *******************************************************/
    public void Print(){
        // Fill this in
        inOrderPrintRecursive(root);
    } //end-Print





    private void reduceCount(AVLNode node, int key){
        if (node==null){
            return;
        }
        else if (node.key==key){
            node.count--;
        }
        reduceCount(node.left,key);
        reduceCount(node.right,key);
    }

    private void increaseCount(AVLNode node, int key){
        if (node==null){
            return;
        }
        else if (node.key==key) {
            node.count++;
        }
        increaseCount(node.left,key);
        increaseCount(node.right,key);
    }

    private int height(AVLNode node) {
        if (node == null){
            return 0;
        }
        else{
            int leftHeight = height(node.left);
            int rightHeight = height(node.right);

            return max(leftHeight, rightHeight) + 1;
        }
    }

    private AVLNode findRecursive(AVLNode node, int key){
        if (node==null || node.key==key){
            return node;
        }else if (node.key > key){
            return findRecursive(node.left,key);
        }else if(node.key < key){
            return findRecursive(node.right,key);
        }
        return null;
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        return x;
    }

    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        return y;
    }

    private int getBalance(AVLNode node) {
        if (node == null) {
            return 0;
        }

        return height(node.left) - height(node.right);
    }

    private AVLNode insertRecursive(AVLNode node, AVLNode newNode) {


        if (node == null)

            return (newNode);

        if (newNode.key < node.key)
            node.left = insertRecursive(node.left, newNode);
        else if (newNode.key > node.key)
            node.right = insertRecursive(node.right, newNode);
        else if (newNode.key == node.key){
            node.right = insertRecursive(node.right, newNode);

        }

        int balance = getBalance(node);

        if (balance > 1 && newNode.key < node.left.key)
            return rightRotate(node);

        if (balance < -1 && newNode.key > node.right.key)
            return leftRotate(node);

        if (balance > 1 && newNode.key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && newNode.key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        if (balance > 1 && newNode.key == node.left.key)
            return rightRotate(node);

        if (balance < -1 && newNode.key == node.right.key)
            return leftRotate(node);

        return node;
    }

    private AVLNode deleteRecursive(AVLNode node, int key){

        if (node == null){
            return node;
        }

        if (key < node.key){
            node.left = deleteRecursive(node.left, key);
        } else if (key > node.key){
            node.right = deleteRecursive(node.right, key);
        } else {

            if ((node.left == null) || (node.right == null))
            {
                AVLNode temp = null;
                if (temp == node.left){
                    temp = node.right;
                } else{
                    temp = node.left;
                }

                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }

            } else
            {

                AVLNode temp = maxValueNode(node.left);

                node.key = temp.key;

                node.left = deleteRecursive(node.left, temp.key);
            }
        }

        if (node == null){
            return node;
        }


        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0)
            return rightRotate(node);

        if (balance > 1 && getBalance(node.left) < 0)
        {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && getBalance(node.right) <= 0)
            return leftRotate(node);

        if (balance < -1 && getBalance(node.right) > 0)
        {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private AVLNode maxValueNode(AVLNode node) {
        AVLNode current = node;

        while (current.right != null)
            current = current.right;

        return current;
    }

    private void inOrderPrintRecursive(AVLNode node) {
        if (node != null) {
            inOrderPrintRecursive(node.left);
            System.out.print(node.key + " ");
            inOrderPrintRecursive(node.right);
        }
    }

};

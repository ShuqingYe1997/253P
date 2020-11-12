import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName: AVLTree
 * @Description:
 * @Author: SQ
 * @Date: 2020-11-11
 */


class AVLNode {
    int val;
    AVLNode left;
    AVLNode right;
    int height;

    AVLNode(){
        val = 0;
        left = null;
        right = null;
        height = 1;
    };

    AVLNode(int val) {
        this.val = val;
        left = null;
        right = null;
        height = 1;
    }

    AVLNode(int val, AVLNode left, AVLNode right, int height) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.height = height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
}

class AVLTree{

    AVLNode root;

    List<List<Integer>> paths;
    List<String> results;

    AVLTree() {
        root = null;
        paths = new ArrayList<>();
        results = new ArrayList<>();
    }

    void find(int n) {
        recursive_find(root, n, new ArrayList<>());
    }

    void insert(int n) {
        root = recursive_insert(root, n, new ArrayList<>());
    }

    void delete(int n) {
        root = recursive_delete(root, n, new ArrayList<>());
    }

    private void save(List<Integer> path, String result) {
        paths.add(path);
        results.add(result);
    }

    private AVLNode rotateLeft (AVLNode root) {
        AVLNode rightTree = root.right;
        root.right = rightTree.left;
        rightTree.left = root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;
        rightTree.height = Math.max(height(rightTree.left), height(rightTree.right)) + 1;

        return rightTree;
    }

    private AVLNode rotateRight (AVLNode root) {
        AVLNode leftTree = root.left;
        root.left = leftTree.right;
        leftTree.right = root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;
        leftTree.height = Math.max(height(leftTree.left), height(leftTree.right)) + 1;

        return leftTree;
    }

    // return the height of a node
    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    // Get Balance factor of a node
    private int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private void recursive_find(AVLNode node, int n, List<Integer> path) {
        if (node == null) {
            save(path, "not found!");
            return;
        }

        path.add(node.val);
        if (node.val > n) {
            recursive_find(node.left, n, path);
        }
        else if (node.val < n) {
            recursive_find(node.right, n, path);
        }
        else {  // hit
            save(path, "found");
        }
    }

    private AVLNode recursive_insert(AVLNode parent, int n, List<Integer> path) {
        if (parent == null) {
            path.add(n);
            save(path, "inserted");
            return new AVLNode(n);
        }

        path.add(parent.val);
        if (parent.val > n)
            parent.left = recursive_insert(parent.left, n, path);
        else if (parent.val < n)
            parent.right = recursive_insert(parent.right, n, path);
        else
            return parent; // duplicated node

        parent.height = Math.max(height(parent.left), height(parent.right)) + 1;

        // check if parent is balanced
        int balance = getBalance(parent);

        // Left Left
        if (balance > 1 && n < parent.left.val)
            return rotateRight(parent);

        // Right Right
        if (balance < -1 && n > parent.right.val)
            return rotateLeft(parent);

        // Left Right
        if (balance > 1 && n > parent.left.val) {
            parent.left = rotateLeft(parent.left);  // Change LR to LL
            return rotateRight(parent);
        }

        // Right Left
        if (balance < -1 && n < parent.right.val) {
            parent.right = rotateRight(parent.right);  // Change RL to RR
            return rotateLeft(parent);
        }

        // Still balanced
        return parent;
    }

    private AVLNode recursive_delete(AVLNode node, int n, List<Integer> path) {
        if (node == null) {
            save(path, "not found!");
            return null;
        }

        // 1. regular deletion
        path.add(node.val);
        if (node.val > n)
            node.left = recursive_delete(node.left, n, path);
        else if (node.val < n)
            node.right = recursive_delete(node.right, n, path);
        else {  // hit
            save(path, "deleted");
            if (node.left == null && node.right == null) // parent has no child
                node = null;
            else if (node.left == null)  // has right child
                node = node.right;
            else if (node.right == null)  // has left child
                node = node.left;
            else {  // parent has 2 children
//                AVLNode suc = getSuccessor(node.right);  // get the min node in right subTree
//                node.val = suc.val;  // copy min node's value
                AVLNode pre = getPredecessor(node.left);  // get the max node in left subTree
                node.val = pre.val;
                node.left = recursive_delete(node.left, pre.val);
            }
        }

        // if node is null then it can skip all the following activities...
        if (node == null)
            return null;

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = getBalance(node);

        // Left Left
        if (balance > 1 && getBalance(node.left) >= 0)
            return rotateRight(node);

        // Right Right
        if (balance < -1 && getBalance(node.right) <= 0)
            return rotateLeft(node);

        // Left Right
        if (balance > 1 && getBalance(node.left) < 0)
        {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right Left
        if (balance < -1 && getBalance(node.right) > 0)
        {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // For deleting predecessor/successor of node under inorder traversal
    private AVLNode recursive_delete(AVLNode node, int n) {
        if (node == null)
            return null;

        // 1. regular deletion
        if (node.val > n)
            node.left = recursive_delete(node.left, n);
        else if (node.val < n)
            node.right = recursive_delete(node.right, n);
        else {  // hit
            if (node.left == null && node.right == null) // parent has no child
                node = null;
            else if (node.left == null)  // has right child
                node = node.right;
            else if (node.right == null)  // has left child
                node = node.left;
            else {  // parent has 2 children
                AVLNode pre = getPredecessor(node.left);  // get the max node in left subTree
                node.val = pre.val;
                node.left = recursive_delete(node.left, pre.val);
            }
        }

        // if node is null then it can skip all the following activities...
        if (node == null)
            return null;

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = getBalance(node);

        // Left Left
        if (balance > 1 && getBalance(node.left) >= 0)
            return rotateRight(node);

        // Right Right
        if (balance < -1 && getBalance(node.right) <= 0)
            return rotateLeft(node);

        // Left Right
        if (balance > 1 && getBalance(node.left) < 0)
        {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right Left
        if (balance < -1 && getBalance(node.right) > 0)
        {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private AVLNode getPredecessor(AVLNode node) {
        if (node == null)
            return null;
        while(node.right != null) {
            node = node.right;
        }
        return node;
    }

    void printAll() {
        for (int i = 0; i < paths.size(); i++) {
            List<Integer> path = paths.get(i);
            String result = results.get(i);
            for (int num : path)
                System.out.print(num + " ");
            System.out.println("(" + result +")");
        }

    }
}


public class AVLTreeImpl {


    public static void main(String[] args) {
        try{
            Scanner scanner = new Scanner(System.in);
            String filename = scanner.nextLine();
            FileReader fileReader = new FileReader(new File("./" + filename +".txt"));

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            AVLTree avlTree = new AVLTree();
            while ((line = bufferedReader.readLine()) != null) {
                String[] commands = line.split("\\s+");
                switch (commands[0]) {
                    case "insert":
                        avlTree.insert(Integer.parseInt(commands[1]));
                        break;
                    case "find":
                        avlTree.find(Integer.parseInt(commands[1]));
                        break;
                    case "delete":
                        avlTree.delete(Integer.parseInt(commands[1]));
                        break;
                    default:
                        System.out.println("No such method! Please check your input.");
                }


            }
            // Final output
            avlTree.printAll();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.util.Arrays;
import java.util.Scanner;

/**
 * @ClassName: Quiz5
 * @Description:
 * @Author: SQ
 * @Date: 2020-11-13
 */


class Node{
    int key;
    Node left;
    Node right;

    Node() {
        key = 0;
        left = null;
        right = null;
    }

    Node(int key, Node left, Node right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }
}
class Solution {
    int maxSum = 0;  // global variable, stores the sum of deepest leaves

    int maxHeight = 0;  // global variable, stores the height of tree that has been traversed

    public int sumDeepestLeaves(Node root) {
        recursiveSum(root, 0);
        return maxSum;
    }


    private void recursiveSum(Node root, int height) {
        if (root == null)
            return;
        if (height > maxHeight) {
            maxHeight = height;
            maxSum = root.key;
        }
        else if (height == maxHeight) {
            maxSum += root.key;
        }

        recursiveSum(root.left, height + 1);
        recursiveSum(root.right, height + 1);
    }

    // nums is always a full binary tree (null is included)
    public Node buildTree(String[] nums) {
        int n = nums.length;
        if(n == 0)
            return null;

        Node[] nodes = new Node[n + 1];  // starts with 1 is WAY better... If starts with 0, 2 * 0 = 0......
        for(int i = 0; i < nums.length; i++) {
            if(nums[i].trim().compareTo("null") == 0)
                nodes[i + 1] = null;
            else
                nodes[i + 1] = new Node(Integer.parseInt(nums[i].trim()), null, null);
        }

        for(int i = 1 ;i <= n; i++) {
            Node root = nodes[i];
            if (root == null)
                continue;
            int left = 2 * i;
            int right = 2 * i + 1;
            if (left <= n)
                root.left = nodes[left];
            if (right <= n)
                root.right = nodes[right];
        }
        return nodes[1];
    }

    private void preOrder(Node root) {
        if(root == null)
            return;
        System.out.println(root.key);
        preOrder(root.left);
        preOrder(root.right);
    }
}


public class Quiz5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = null;

        Solution solution = null;
        while((line = scanner.nextLine()) != null) {
            solution = new Solution();
            Node root = solution.buildTree(line.split(","));
            System.out.println("Output: " + solution.sumDeepestLeaves(root));
        }
    }

}

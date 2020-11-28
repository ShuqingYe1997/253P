import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName: DNASequenceAlignment
 * @Description: A classic DP problem, 全靠画图解决问题
 * @Author: SQ
 * @Date: 2020-11-26
 */

class DNASequenceAlignmentImpl {
    private String[] s1;
    private String[] s2;
    private int n;
    private int m;
    private int[][] dp;

    DNASequenceAlignmentImpl(String[] s1, String[] s2) {
        this.s1 = s1;
        this.s2 = s2;
        this.n = s1.length;
        this.m = s2.length;
        this.dp = new int[n + 1][m + 1];
    }

    private void init() {
        for (int i = 0; i <= m; i++)
            dp[0][i] = i;
        for (int i = 0; i <= n; i++)
            dp[i][0] = i;
    }

    void findMinimumInsertion() {
        init();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (!isMatching(s1[i - 1], s2[j - 1]))
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
                else
                    dp[i][j] = dp[i - 1][j - 1];
            }
        }
    }

    private boolean isMatching(String c1, String c2) {
        switch (c1) {
            case "A":
                return c2.equals("T");
            case "T":
                return c2.equals("A");
            case "C":
                return c2.equals("G");
            case "G":
                return c2.equals("C");
        }
        return false;
    }

    void printAlignmentSequence() {
        int spaceNum = dp[n][m];
        List<String> list1 = new ArrayList<>(Arrays.asList(s1));
        List<String> list2 = new ArrayList<>(Arrays.asList(s2));

        int i = n, j = m;
        while(i > 0 && j > 0) {
            if (dp[i - 1][j] == dp[i][j] - 1) { // go up
                list2.add(j, "_");
                i--;
            }
            else if (dp[i][j - 1] == dp[i][j] - 1) {  // go left
                list1.add(i, "_");
                j--;
            }
            else {
                i--;j--;
            }
        }
        while (i > 0) {
            list2.add(j, "_");
            i--;
        }
        while (j > 0) {
            list1.add(i, "_");
            j--;
        }
//        printDP();
        for (String s: list1)
            System.out.print(s + " ");
        System.out.println();
        for (String s: list2)
            System.out.print(s + " ");
    }

    private void printDP() {
        for (int[] array: dp)
            System.out.println(Arrays.toString(array));
    }

}

public class DNASequenceAlignment {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[][] input = new String[2][];
        String line;
        int cnt = 0;
        DNASequenceAlignmentImpl sequenceAlignment;

        while((line = scanner.nextLine()) != null && !line.equals("exit")) {
            String[] strings = line.split("\\s+");
            if (isValid(strings))
                input[cnt++] = strings;
            else {
                System.out.println("Invalid DNA sequence!");
                continue;
            }
            if (cnt == 2) {
                sequenceAlignment = new DNASequenceAlignmentImpl(input[0], input[1]);
                sequenceAlignment.findMinimumInsertion();
                sequenceAlignment.printAlignmentSequence();
                cnt = 0;
            }
        }
    }

    static boolean isValid(String[] strings) {
        String[] bases = {"A", "T", "C", "G"};
        for (String string: strings) {
            boolean flag = false;
            for (String base: bases) {
                if (string.equals(base))
                    flag = true;
            }
            if (!flag)
                return false;
        }

        return true;
    }
}

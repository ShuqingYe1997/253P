import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName: DNASequenceAlignmentWithInvalidPairs
 * @Description:
 * @Author: SQ
 * @Date: 2020-11-27
 */

class DNASequenceAlignmentWithInvalidPairsImpl {
    private String[] s1;
    private String[] s2;
    private int n;
    private int m;
    private double[][] dp;

    DNASequenceAlignmentWithInvalidPairsImpl(String[] s1, String[] s2) {
        this.s1 = s1;
        this.s2 = s2;
        this.n = s1.length;
        this.m = s2.length;
        this.dp = new double[n + 1][m + 1];
    }

    private void init() {
        for (int i = 1; i <= n; i++)
            dp[i][0] = dp[i - 1][0] + getScore(s1[i - 1]);
        for (int i = 1; i <= m; i++)
            dp[0][i] = dp[0][i - 1] + getScore(s2[i - 1]);
    }

    void findMaximizeScore() {
        init();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                double tmp = Math.min(dp[i - 1][j], dp[i][j - 1])  + 1;
                dp[i][j] = Math.min(tmp, dp[i - 1][j - 1] + getScore(s1[i - 1], s2[j - 1]));

            }
        }
    }

//    T - T,  C - C, and T - C mis-pairings are penalized 3/2 times as much as a spacer
//    T - G and A - C mis-pairings are penalized 7/4 times as much as a spacer
//    A - A, G - G, and A - G mis-pairings are penalized 5/2 times as much as a spacer
    private double getScore(String s1, String s2) {
        if (s1.equals("T")) {
            if (s2.equals("T") || s2.equals("C"))
                return 3.0 / 2;
            else if (s2.equals("G"))
                return 7.0 / 4;
        }
        else if (s1.equals("C")) {
            if (s2.equals("C") || s2.equals("T"))
                return 3.0 / 2;
            else if (s2.equals("A"))
                return 7.0 / 4;
        }
        else if (s1.equals("A")) {
            if (s2.equals("A") || s2.equals("G"))
                return 5.0 / 2;
            else if (s2.equals("C"))
                return 7.0 / 4;
        }
        else if (s1.equals("G")) {
            if (s2.equals("A") || s2.equals("G"))
                return 5.0 / 2;
            else if (s2.equals("T"))
                return 7.0 / 4;
        }
        // matching
        return 0;
    }

    private double getScore(String s) {
        switch (s) {
            case "A":
                return 7.0 / 4;
            case "C":
            case "T":
            case "G":
                return 3.0 / 2;
        }
        return 0;
    }

    void printAlignmentSequence() {
        double maxScore = dp[n][m];
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
        for (double[] array: dp)
            System.out.println(Arrays.toString(array));
    }
}

public class DNASequenceAlignmentWithInvalidPairs {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[][] input = new String[2][];
        String line;
        int cnt = 0;
        DNASequenceAlignmentWithInvalidPairsImpl sequenceAlignment;

        while((line = scanner.nextLine()) != null && !line.equals("exit")) {
            String[] strings = line.split("\\s+");
            if (isValid(strings))
                input[cnt++] = strings;
            else {
                System.out.println("Invalid DNA sequence!");
                continue;
            }
            if (cnt == 2) {
                sequenceAlignment = new DNASequenceAlignmentWithInvalidPairsImpl(input[0], input[1]);
                sequenceAlignment.findMaximizeScore();
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

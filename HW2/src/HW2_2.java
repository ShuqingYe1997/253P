import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName: HW2_2
 * @Description:
 * @Author: SQ
 * @Date: 2020-10-20
 */
public class HW2_2 {
    /**
     * @Description: Write strstrx(string needle, string haystack) from problem 1 using useKnuth-Morris-Pratt.
     *               This is an O(N) algorithm by pre-processing the needle.
     *               For more info about this KMP, see https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/
     * @Param: [needle, hayStack]
     * @Return: int
     * @Author: SQ
     * @Date: 2020-10-15
     **/
    public static int numOccurrences(String needle, String hayStack) {
        if(needle == null || hayStack == null ||
                needle.length() == 0 || hayStack.length() == 0 ||
                needle.length() > hayStack.length())
            return -1;
        int i =0, j = 0;
        int[] lps = getLps(needle.toCharArray());
        List<Integer> res = new ArrayList<>();
        while (i < hayStack.length()) {
            if(hayStack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            }
            if(j == needle.length()){
                res.add(i - j);
                j = lps[j - 1];
            }
            else if (i < hayStack.length() && hayStack.charAt(i) != needle.charAt(j)) {
                if (j != 0)
                    j = lps[j - 1];  // hayStack.subString(0, i) === needle.subString(0, j)
                    // so j - 1 chars in needle don't need to be compared since they are gonna match
                    // don't move i
                else
                    i++;
            }
        }
        return res.size();
    }

    /**
     * @Description: For each sub-pattern pat[0..i] where i = 0 to m-1,
     *              lps[i] stores length of the maximum matching proper prefix
     *              which is also a suffix of the sub-pattern pat[0..i]
     * @Param: [needle]
     * @Return: int[]
     * @Author: SQ
     * @Date: 2020-10-15
     **/
    private static int[] getLps(char[] needle) {
        int m = needle.length;
        int[] lps = new int[m];
        lps[0] = 0;
        int i = 1, len = 0;
        while (i < m) {
            if(needle[i] == needle[len]) {
                lps[i] = ++len;
                i++;
            }
            else {  // needle[i] != needle[len]
                if (len == 0) {
                    lps[i] = 0;
                    i++;
                }
                else
                    len = lps[len - 1];
            }
        }
        return lps;
    }

    public static void main(String[] args) {
        String needle = "";
        String hayStack = "";
            Scanner scanner = new Scanner(System.in);
            System.out.print("needle <-- ");
            needle = scanner.nextLine();
            System.out.print("haystack <-- ");
            hayStack = scanner.nextLine();

            System.out.println(numOccurrences(needle, hayStack));
    }
}

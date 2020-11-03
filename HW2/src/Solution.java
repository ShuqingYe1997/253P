import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Solution
 * @Description: Solutions to HW2, 253P
 * @Author: SQ
 * @Date: 2020-10-14
 */
public class Solution {


    /**
     * @Description: Count the occurrence of each char in needle
     * @Param: [s]
     * @Return: int[]
     * @Author: SQ
     * @Date: 2020-10-14
     **/
    private static int[] getcharMap(String s) {
        int[] charMap = new int[128];
        for (int i = 0; i < s.length(); i++) {
            charMap[s.charAt(i)]++;
        }
        return charMap;
    }

    /**
     * @Description: compare two charMaps to see if they are equal
     * @Param: [map1, map2]
     * @Return: boolean
     * @Author: SQ
     * @Date: 2020-10-14
     **/
    private static boolean compare(String s, int[] map) {
        for (int i = 0; i < s.length(); i++) {
            if(map[s.charAt(i)- 'a'] == 0)
                return false;
            map[s.charAt(i)- 'a']--;
        }
        for (int i = 0; i < map.length; i++)
            if(map[i] != 0)
                return false;
        return true;
    }

    /**
     * @Description: Write anagram(string needle, string haystack) that returns a list of indices of characters
     *              in haystack that mark the beginning of an anagram for needle.
     * @Param: [needle, hayStack]
     * @Return:  void
     * @Author: SQ
     * @Date: 2020-10-14
     **/
    public static void anagram(String needle, String hayStack) {
        List<Integer> indices =  new ArrayList<>();
        for(int i = 0; i < hayStack.length() - needle.length() + 1; i++) {
            if (compare(hayStack.substring(i, i + needle.length()), getcharMap(needle)) )
                indices.add(i);
        }
        System.out.println(indices);
    }



    /**
     * @Description: LeetCode 1247
     * @Param: [s1, s2]
     * @Return: int
     * @Author: SQ
     * @Date: 2020-10-15
     **/
    public int minimumSwap(String s1, String s2) {
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();

        int xy = 0, yx = 0, res = 0;
        for (int i = 0 ; i < chars1.length; i++) {
            if(chars1[i] == 'x' && chars2[i] == 'y')
                xy ++;
            else if (chars1[i] == 'y' && chars2[i] == 'x')
                yx++;
        }
        res += xy / 2 + yx / 2;  // a pair of xy or a pair of yx contributes 1 swap
        xy %= 2;
        yx %= 2;
        if(xy == yx) {
            res += (xy + yx);  // a pair of (xy, yx) contributes 2 swaps
            return res;
        }
        else return -1;

    }

    /**
     * @Description: LeetCode 481
     *              122112122122... is a magic number because the number of
     *              contiguous occurrences of characters '1' and '2' generates the string itself.
     *              For example:
     *              1 22 11 2 1 22 1 22
     *              the size of each group is:
     *              1 2 2 1 1 2 1 2
     *
     * @Param: [n]
     * @Return: int
     * @Author: SQ
     * @Date: 2020-10-15
     **/
    public int magicalString(int n) {
        if (n <= 0)
            return 0;

        int[] s = new int[n];
        s[0] = 1;

        int i = 0, j = 0, cnt = 0;
        int flag = 1;
        while (j < n) {  // j moves faster than i
            if (s[i] == 1) {
                s[j] = flag;
                if (s[j] == 1)
                    cnt++;
                i++;
                j++;  // j only moves one step
            }
            else {  // s[i] == 2
                s[j] = flag;
                if (s[j] == 1)
                    cnt++;
                if (j + 1 < n){
                    s[j + 1] = s[j];
                    if (s[j + 1] == 1)
                        cnt++;
                }
                i++;
                j += 2;  // j moves 2 steps
            }
            if(flag == 1)  // the char of each group changes by step
                flag = 2;
            else flag = 1;
        }
        return cnt;
    }

    public static void main(String[] args) {

    }
}

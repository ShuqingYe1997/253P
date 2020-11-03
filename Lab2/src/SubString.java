import java.util.*;

/**
 * @ClassName: SubString
 * @Description:
 * @Author: SQ
 * @Date: 2020-10-20
 */
public class SubString {


    // sort strings by length, ascending
    public static void sortByLength(List<String> strings) {
        if (strings.size() <= 1)
            return;
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
    }

    public static List<List<String>> findSubString(List<String> strings) {
        List<List<String>> res = new ArrayList<>();
//        if (strings.size() <= 1)
//            return res;
        for (int i = 0; i < strings.size(); i++) {
            res.add(i, new ArrayList<>());
            for (int j = i + 1; j < strings.size(); j++) {
                if (isSubString(strings.get(i), strings.get(j))) {
                    List<String> tmp = res.get(i);
//                    if (tmp == null)
//                        res.add(i, Arrays.asList(strings.get(j)));
//                    else
                        tmp.add(strings.get(j));
                }
            }
        }
        return res;
    }

    // is s1 a subString of s2
    public static boolean isSubString(String s1, String s2) {
        for (int i = 0; i < s2.length(); i++) {
            int j = 0;
            for (; j < s1.length() && i + j < s2.length(); j++) {
                if (s1.charAt(j) != s2.charAt(i + j))
                    break;
            }
            if (j == s1.length())
                return true;
        }
        return false;
    }

    public static void print(List<String> strings, List<List<String>> res) {
        for (int i = 0; i < strings.size(); i++) {
            System.out.print(strings.get(i) + ": ");
            List<String> list = res.get(i);
            if (list.size() > 0) {
                int j = 0;
                for (; j < list.size() - 1; j++)
                    System.out.print(list.get(j) + ", ");
                System.out.println(list.get(j));
            } else
                System.out.println();
        }
    }

    public static void main (String[] args) {
        System.out.print("Input the strings:");
        List<String> strings = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String s = "";
        while ((s = scanner.nextLine()) != null && s.length() > 0) {
            strings.add(s);
        }
        sortByLength(strings);
        List<List<String>> res = findSubString(strings);
        print(strings, res);
    }
}

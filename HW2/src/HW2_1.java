import java.util.Scanner;

/**
 * @ClassName: HW2_1
 * @Description:
 * @Author: SQ
 * @Date: 2020-10-20
 */
public class HW2_1 {
    /**
     * @Description: strstr(string needle, string haystack) returns the index of
     *               the starting character of the first occurrence of needle in the haystack,
     *               or -1 if the needle does not exist in the haystack.
     * @Param: [needle, hayStack]
     * @Return: int
     * @Author: SQ
     * @Date: 2020-10-14
     **/
    public static int strstr(String needle, String hayStack) {
        if(needle == null || hayStack == null ||
                needle.length() == 0 || hayStack.length() == 0 ||
                needle.length() > hayStack.length())
            return -1;
        for(int i = 0; i < hayStack.length(); i++) {
            int j = 0;
            for (; j < needle.length() && i + j < hayStack.length(); j++) {
                if(hayStack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
            }
            if(j == needle.length())
                return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        String needle = "";
        String hayStack = "";
        Scanner scanner = new Scanner(System.in);
        System.out.print("needle <-- ");
//        needle = scanner.next();
        needle = scanner.nextLine();
        System.out.print("haystack <-- ");
//        hayStack = scanner.next();
        hayStack = scanner.nextLine();

//        next()一定要读取到有效字符后才可以结束输入，对输入有效字符之前遇到的空格键、Tab键或Enter键等结束符，
//        next()方法会自动将其去掉，只有在输入有效字符之后，next()方法才将其后输入的空格键、Tab键或Enter键等视为分隔符或结束符。


        System.out.println(strstr(needle, hayStack));
    }
}

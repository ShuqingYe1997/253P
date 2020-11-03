import java.util.*;

/**
 * @ClassName: ParseString
 * @Description:
 * Design a program that reads in parenthesized text and, if valid, on a new line prints the text of each nesting level
 * preceded with two spaces for level of nesting.  The text should be printed in order of nesting.
 *
 * Input:
 * a string of parenthesized text from stdin
 *
 * Output:
 * if parentheses are valid, then on a new line, prints the text of each nesting level preceded with two spaces for level
 * of nesting to stdout.
 * otherwise, print error message
 *
 * Example 1:
 * input:
 * (does{[[well!]]}work)this
 *
 * output:
 * |this
 * |  does work
 * |        well!
 *
 * Example 1:
 * input:
 * (doesn’t{[[work!]})this
 *
 * output:
 * |mismatched groups!
 *
 * @Author: SQ
 * @Date: 2020-10-25
 */
public class ParseString {

    public static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static boolean parse(String input, Map<Integer, List<String>> map) {
        Stack<Character> stack = new Stack<>();
        StringBuffer stringBuffer = new StringBuffer();

        for (char c: input.toCharArray()) {
            switch (c) {
                // handle space, tab and enter(?)
                case ' ':
                case '\t':
                case '\n':
                    if (stack.isEmpty())  // if spaces are in front of or behind parentheses, just ignore them
                        continue;
                    else
                        stringBuffer.append(c);
                    break;

                // handle left parentheses
                case '(':
                case '[':
                case '{':
                    addText(map, stack, stringBuffer);
                    stringBuffer = new StringBuffer();  // init stringBuffer
                    stack.push(c);
                    break;

                // handle right parentheses
                case ')':
                case ']':
                case '}':
                    addText(map, stack, stringBuffer);
                    stringBuffer = new StringBuffer();  // init stringBuffer
                    if (!isParenthesesMatch(stack,c))
                        return false;
                    else
                        stack.pop();
                    break;

                    // handle other characters, e.g. letters, digits...
                    default:
                        stringBuffer.append(c);
                        break;
            }
        }
        if (stringBuffer.length() > 0)
            addText(map, stack, stringBuffer);
        return true;
     }

    public static boolean isParenthesesMatch(Stack<Character> stack, char c) {
        return (!stack.isEmpty() &&
                        (stack.peek() == '(' && c == ')'||
                        stack.peek() == '[' && c == ']' ||
                        stack.peek() == '{' && c == '}')
                );
    }

    // Store texts between parentheses
    public static void addText(Map<Integer, List<String>> map, Stack<Character> stack, StringBuffer stringBuffer) {
        if (stringBuffer.length() == 0)
            return;
        List<String> list = map.get(stack.size());
        if (list == null)
            list = new ArrayList<>();
        list.add(stringBuffer.toString().trim());  // ignore unnecessary spaces
        map.put(stack.size(), list);
    }

    public static void print(Map<Integer, List<String>> map) {
        if (map.size() == 0)
            System.out.println("|");

        TreeMap<Integer, List<String>> res = new TreeMap<>(map);
//        System.out.println("map size:" + map.size()+"   tree map size:" + res.size());

        for (Integer i: res.keySet()) {
            List<String> list = res.get(i);
            System.out.print("|");
            for (int j = 0; j < i; j++)
                System.out.print("  "); // 2 spaces
            for (int j = 0; j < list.size() - 1; j++)
                System.out.print(list.get(j) + " ");
            System.out.println(list.get(list.size() - 1));
        }
    }
    public static void main(String[] args) {
        while (true) {
            String input = getInput();
            if (input.equals("exit"))
                break;
            Map<Integer, List<String>> map = new HashMap<>();
            if(parse(input, map))
                print(map);
            else
                System.out.println("|mismatched groups!");
        }
    }
}

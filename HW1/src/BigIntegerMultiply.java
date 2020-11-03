import java.util.Scanner;

public class BigIntegerMultiply {
    public static void main(String[] args) {
        while(true) {
            System.out.print("Input two numbers:");
            Scanner scanner = new Scanner(System.in);
            String s1 = scanner.next();
            String s2 = scanner.next();
            System.out.println(multiply(s1, s2));
        }
    }

    public static String multiply(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        if(m == 0 || n == 0)
            return "0";

        int[] res = new int[m + n];  // 9 * 9 = 81, dont need m + n + 1
        char[] num1 = s1.toCharArray();
        char[] num2 = s2.toCharArray();

        for(int i = num1.length - 1; i >= 0; i--) {
            for(int j = num2.length - 1; j >= 0; j--) {
                res[i + j] += (num1[i] - '0') * (num2[j] - '0');
            }
        }
        int carry = 0;
        StringBuffer sb = new StringBuffer();
        for(int i = res.length - 2; i >= 0; i--) {
            res[i] += carry;
            sb.append(res[i] % 10);
            carry = res[i] / 10;
        }
        if(carry != 0)
            sb.append(carry);
        return sb.reverse().toString();
    }
}

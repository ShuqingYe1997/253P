import java.util.*;

/**
 * @ClassName: RomanNumeralConverter
 * @Description:
 * Write a program that converts integers into Roman numerals and Roman numerals into integers.
 * We will be using the rule that no Roman numeral is to be repeated in succession more than 3 times,
 * thus there will be a bound on the size of the numbers your program can process.
 *
 * @Author: SQ
 * @Date: 2020-11-18
 */
class RomanNumeralConverterImpl {
    static final String ROMAN_ZERO = "UNDEFINED";
    static final Map<Character, Integer> ROMAN_VALUE_MAP = new HashMap<>(){{
        put('I', 1);  // 1
        put('V', 5);  // 5
        put('X', 10);  // 10
        put('L', 50);  // 50
        put('C', 100);  // 100
        put('D', 500);  // 500
        put('M', 1000);  // 1000
    }};
    static final int[] DECIMAL = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    static final String[] ROMAN_NUMERAL = {"I","IV","V","IX","X","XL","L","XC","C","CD","D","CM","M"};

    // MMM CM XC IX
    static final int MAX_VALUE = 3999;

    boolean isDigit(String s) {
        char c = s.charAt(0);
        return c >= '0' && c <= '9';
    }

    boolean isValidNumber(String s) {
        if (s.equals("0")) {
            System.err.println(ROMAN_ZERO);
            return false;
        }
        else if (s.charAt(0) == '-') {
            System.err.println("Invalid number! Your input should NOT be a negative number.");
            return false;
        }
        else if (Integer.parseInt(s) > 3999) {
            System.err.println("Invalid number! Your input should NOT exceed 3999.");
            return false;
        }
        return true;
    }

    boolean isValidRomanNumeral(String s) {
        for (char c : s.toCharArray()) {
            if (!ROMAN_VALUE_MAP.keySet().contains(c)) {
                System.err.println("Invalid Roman numerals! Your input has illegal characters.");
                return false;
            }
        }
        if (s.length() >= 4 && s.substring(0, 4).equals("MMMM")) {
            System.err.println("Invalid Roman numerals! Your input should NOT exceed the limited value.");
            return false;
        }
        return true;
    }

    String num2Roman(String s) {
        StringBuilder res = new StringBuilder();
        int num = Integer.parseInt(s);
        int i = ROMAN_NUMERAL.length - 1;
        while (num > 0) {
            int cnt = num / DECIMAL[i];
            while (cnt-- > 0) {
                res.append(ROMAN_NUMERAL[i]);
            }
            num %= DECIMAL[i];
            i--;
        }
        return res.toString();
    }

    int roman2Num(String s) {
        int sum = 0;
        int i = 0;
        for (; i < s.length() - 1; i++) {
            if (ROMAN_VALUE_MAP.get(s.charAt(i)) >= ROMAN_VALUE_MAP.get(s.charAt(i + 1)))
                sum += ROMAN_VALUE_MAP.get(s.charAt(i));
            else {  // e.g. IX: minus this value then add next value, move 2 steps at a time
                sum -= ROMAN_VALUE_MAP.get(s.charAt(i));
                sum += ROMAN_VALUE_MAP.get(s.charAt(++i));
            }
        }
        // the last char
        if (i < s.length())
            sum += ROMAN_VALUE_MAP.get(s.charAt(i));
        return sum;
    }
}

public class RomanNumeralConverter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        RomanNumeralConverterImpl converter = new RomanNumeralConverterImpl();

        while (!(input = scanner.nextLine()).toLowerCase().equals("exit")) {
            if (converter.isDigit(input)) {
                if (converter.isValidNumber(input))
                    System.out.println(converter.num2Roman(input));
            }
            else {
                if (converter.isValidRomanNumeral(input))
                    System.out.println(converter.roman2Num(input));
            }
        } // end of while
    }
}

import java.io.*;
import java.util.Arrays;

/**
 * @ClassName: MusicPlayer
 * @Description:
 * @Author: SQ
 * @Date: 2020-11-3
 */
public class MusicPlayer {

    public static String[] parseInput(String input) {
        String[] res;
        StringBuffer sb = new StringBuffer();
        for (char c: input.toCharArray()) {
            if (c == ' ')
                break;
            sb.append(c);
        }
        if(sb.length() == input.length()) {
            res = new String[1];
            res[0] = input;
        }
        else {
            res  = new String[2];
            res[0] = sb.toString();
            res[1] = input.substring(res[0].length() + 1);  // + 1 for whitespace
        }
        return res;
    }

    public static void execute(String[] commands, SimplePlayList playList) {
        String command = commands[0];
        String content = "";
        if (commands.length == 2)
             content = commands[1];
        switch (command) {
            case "push": {
                playList.push(content);
                break;
            }
            case "queue": {
                playList.queue(content);
                break;
            }
            case "current": {
                playList.current();
                break;
            }
            case "delete": {
                playList.delete();
                break;
            }
            case "prev": {
                playList.prev();
                break;
            }
            case "next": {
                playList.next();
                break;
            }
            case "restart": {
                playList.restart();
                break;
            }
            case "find": {
                playList.find(content);
                break;
            }
            case "changeTo": {
                playList.changeTo(content);
                break;
            }
            case "addBefore": {
                playList.addBefore(content);
                break;
            }
            case "addAfter": {
                playList.addAfter(content);
                break;
            }
            case "random": {
                playList.random();
                break;
            }
            case "print": {
                playList.print();
                break;
            }
            default: {
                System.out.println("Command " + command + "not found.");
            }
        }
    }

    public static void main(String[] args) {
        SimplePlayList playList = new SimplePlayList();
//        Scanner scanner = new Scanner(System.in);
        String input;
        try {
            FileReader fileReader = new FileReader(new File("sample_input.txt"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((input = bufferedReader.readLine()) != null) {
                String[] commands = parseInput(input);
                System.out.println(Arrays.toString(commands));
                execute(commands, playList);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

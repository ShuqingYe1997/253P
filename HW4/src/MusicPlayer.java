import java.io.*;

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
               // playList.print();
                break;
            }
            case "queue": {
                playList.queue(content);
               // playList.print();
                break;
            }
            case "current": {
                playList.current();
               // playList.print();
                break;
            }
            case "delete": {
                playList.delete();
               // playList.print();
                break;
            }
            case "prev": {
                playList.prev();
               // playList.print();
                break;
            }
            case "next": {
                playList.next();
               // playList.print();
                break;
            }
            case "restart": {
                playList.restart();
               // playList.print();
                break;
            }
            case "find": {
                playList.find(content);
               // playList.print();
                break;
            }
            case "changeTo": {
                playList.changeTo(content);
               // playList.print();
                break;
            }
            case "addBefore": {
                playList.addBefore(content);
               // playList.print();
                break;
            }
            case "addAfter": {
                playList.addAfter(content);
               // playList.print();
                break;
            }
            case "random": {
                playList.random();
               // playList.print();
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
        String line;
        try {
            FileReader fileReader = new FileReader(new File("sample_input.txt"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                String[] commands = parseInput(line);
                System.out.println(line);
                execute(commands, playList);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Roster {

    public static int groupSize;
    public static List<String> enrolledStudents;
    public static List<String> absentStudents;
    public static List<List<String>> groups = new ArrayList<>();

    public static String parseLine(String line) {
        String[] strings = line.split("\\t");
        String[] name = strings[1].split(",\\s+");
        return name[1] + " "+ name[0];
    }

    public static void loadRoster() {
        enrolledStudents = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream("roster.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = "";
            while((line = reader.readLine()) != null && !line.startsWith("Student#")){
                continue;
            }
            while((line = reader.readLine()) != null && line.length() > 0){
                enrolledStudents.add(parseLine(line));
            }

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void divideGroups() {
        List<String> presentStudents = enrolledStudents;
        for(String student : absentStudents) {
            presentStudents.remove(student);
        }
        if(presentStudents.size() <= 0)
            return;
        Collections.shuffle(presentStudents);

        groupSize = Math.min(groupSize, presentStudents.size());
        int groupNumber = presentStudents.size() / groupSize;
        int i = 0, j = 0;
        for (; i < groupNumber; i++)
            groups.add(new ArrayList<>());
        i = 0;
        while(i < presentStudents.size()) {
            if (groups.get(groupNumber - 1).size() == groupSize) { // the last group is full
                if (presentStudents.size() - i <= groupNumber) {
                    groups.get(j++).add(presentStudents.get(i++));
                    j %= groupNumber;
                } else {
                    List<String> newGroup = new ArrayList<>();
                    while (i < presentStudents.size()) {
                        newGroup.add(presentStudents.get(i++));
                    }
                    groups.add(newGroup);
                }
            } else {
                groups.get(j++).add(presentStudents.get(i++));
                j %= groupNumber;
            }
        }
    }

    public static void print() {
        System.out.println("Groups:");
        for(int i = 0; i < groups.size(); i++) {
            System.out.print(i + 1 + ".");
            List<String> group = groups.get(i);
            for(int j = 0; j < group.size(); j++)
                System.out.println(group.get(j));
        }
    }

    public static void readParams() {
        System.out.print("Group size:");
        Scanner scanner = new Scanner(System.in);
        groupSize = scanner.nextInt();
        while(groupSize <= 0) {
            System.out.print("Invalid number! Group size:");
            groupSize = scanner.nextInt();
        }
        scanner.nextLine();
        String s = "";
        System.out.print("Absent students:");
        absentStudents = new ArrayList<>();
        while((s = scanner.nextLine()) != null && s.length() > 0) {
            absentStudents.add(s.toUpperCase());
        }
    }


    public static void main(String[] args){
            readParams();
            loadRoster();
            divideGroups();
            print();
    }
}

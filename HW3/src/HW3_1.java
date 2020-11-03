import java.io.*;
import java.util.*;


/**
 * @ClassName: HW3_1
 * @Description: Write a program that reads in a graphical maze from a text file, and determines a solution path.
 * @Author: SQ
 * @Date: 2020-10-26
 */
class Maze {
    private int m;
    private int n;
    private File file;
    private List<Set<Integer>> graph;  // len = m * n
    private boolean[] visited;
    List<Integer> solution;

    public Maze() {
        m = 0;
        n = 0;
        file = null;
    }

    public Maze(String filename) {
        m = 0;
        n = 0;
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File doesn't exist!");
            return;
        }
        this.file = file;
        return;
    }

    public void init() {
        graph = new ArrayList<>();
        for (int i = 0; i < m * n; i++)
            graph.add(new HashSet<>());
        visited = new boolean[m * n];
        solution = new ArrayList<>();
    }

    public void parseFile() {
        try {
            FileReader fileReader = new FileReader(this.file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            List<String> mazeString;

            while((line = bufferedReader.readLine()) != null) {
                if (line.contains("Maze!"))
                    parseMN(line);
                else if (line.contains("(start)")) {
                    mazeString = new ArrayList<>();
                    mazeString.add(line);
                    while((line = bufferedReader.readLine()) != null) {
                        if (line.contains("(end)"))
                            break;
                        mazeString.add(line.trim());  // remove heading spaces
                    }
                    parseMaze(mazeString);
                }

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printSolution() {
        backtrace(new ArrayList<Integer>(), 0, -1);
        System.out.print("Solution Path:");
        if (solution.size() == 0) {
            System.out.println("Can't find a solution!");
            return;
        }
        for (int i = 0; i < solution.size() - 1; i++)
            System.out.print(solution.get(i) + ", ");
        System.out.println(solution.get(solution.size() - 1));
        drawSolutionPath();
    }

    private void drawSolutionPath() {
        final int preSpace = 6;
        for (int i = 0; i < (2 * n - 1 + preSpace); i++)
            System.out.print('~');
        System.out.println();
        System.out.println();
        System.out.println(" (start)");
        char[][] solutionPath = new char[m][2 * n - 1 + preSpace];
        for (int i = 0; i < solutionPath.length; i++) {
            for (int j = 0; j < solutionPath[0].length; j++) {
                solutionPath[i][j] = ' ';
            }
        }
        for (Integer no: solution) {
            int[] coordinate= parseNo(no);
            solutionPath[coordinate[0]][2 * coordinate[1] + preSpace] = 'X';
        }
        for (int i = 0; i < solutionPath.length; i++) {
            for (int j = 0; j < solutionPath[0].length - 1; j++) {
                System.out.print(solutionPath[i][j]);
            }
            System.out.println(solutionPath[i][solutionPath[0].length - 1]);
        }
        for (int i = 0; i < (2 * n - 3 + preSpace); i++)
            System.out.print(' ');
        System.out.println("(end)");

        for (int i = 0; i < (2 * n - 1 + preSpace); i++)
            System.out.print('~');
        System.out.println();
    }

    // bfs
    private void backtrace(List<Integer> tmp, int no, int parent) {
        if (no == getNo(m - 1, n - 1)){  // reached destination
            tmp.add(no);
            solution = new ArrayList<>(tmp);
            return;
        }
        if (!visited[no]){
            visited[no] = true;
            tmp.add(no);
            Iterator iterator = graph.get(no).iterator();
            while(iterator.hasNext()){
                int i = (int)iterator.next();
                if (!visited[i] && i != no) {
                    backtrace(tmp, i, no);
                }
            }
            visited[tmp.get(tmp.size() - 1)] = false;
            tmp.remove(tmp.size() - 1);
        }
    }

    // line sample:    5 x 8 Maze!
    private void parseMN(String line) {
        StringBuffer stringBuffer = new StringBuffer();
        for(char c : line.toCharArray()) {
            if (isDigit(c)) {
                stringBuffer.append(c);
            }
            else if (stringBuffer.length() != 0){
                if (m == 0) {
                    m = Integer.parseInt(stringBuffer.toString());
                    stringBuffer = new StringBuffer();
                }
                else {
                    n = Integer.parseInt(stringBuffer.toString());
                }
            }
        }
//        System.out.println("m = " + m + ", n = "+ n);
    }

    private boolean isDigit(char c) {
        return c - '0' >= 0 && c - '0' <= 9;
    }

    private void parseMaze(List<String> mazeString) {
        init();
        for (int i = 1; i < mazeString.size(); i++) {  // the first line can be ignored
            for (int j = 1; j < mazeString.get(i).length() - 1; j += 2) {
                int row = i - 1;
                int col = (j - 1) / 2;
                int no = getNo(row, col);
                addUpEdge(no, row, col, mazeString.get(i - 1).charAt(j));
                addDownEdge(no, row, col, mazeString.get(i).charAt(j));  //char of itself
                addLeftEdge(no, row, col, mazeString.get(i).charAt(j - 1));
                addRightEdge(no, row, col, mazeString.get(i).charAt(j + 1));
            }
        }
//        for (int i = 0; i < graph.size(); i++)
//            System.out.println(i + ": "+graph.get(i));
    }

    private int getNo(int row, int col) {
        return row * n + col;
    }

    private int[] parseNo(int no) {
        int[] res = new int[2];
        res[0] = no / n;
        res[1] = no % n;
        return res;
    }

    private void addUpEdge(int no, int row, int col, char up) {
        if (row - 1 < 0  || up == '_')
            return;
        int upNo = getNo(row - 1, col);
        graph.get(no).add(upNo);
        graph.get(upNo).add(no);
    }
    private void addDownEdge(int no, int row, int col, char self) {
        if (row + 1 >= m || self == '_')
            return;
        int downNo = getNo(row + 1, col);
        graph.get(no).add(downNo);
        graph.get(downNo).add(no);
    }
    private void addLeftEdge(int no, int row, int col, char left) {
        if (col - 1 < 0 || left == '|')
            return;
        int leftNo = getNo(row, col - 1);
        graph.get(no).add(leftNo);
        graph.get(leftNo).add(no);
    }
    private void addRightEdge(int no, int row, int col, char right) {
        if (col + 1 >= n || right == '|')
            return;
        int rightNo = getNo(row, col + 1);
        graph.get(no).add(rightNo);
        graph.get(rightNo).add(no);
    }

    public File getFile() {
        return file;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setN(int n) {
        this.n = n;
    }
}


public class HW3_1 {

    public static void main(String[] args) {
        System.out.print("Input the filename: ");
        Scanner scanner = new Scanner(System.in);
        String inputFilename = scanner.next();
        String filename = "./sample_hw1/" + inputFilename;
        Maze maze = new Maze(filename);
        if (maze.getFile() != null) {
            maze.parseFile();
            maze.printSolution();
        }
    }
}

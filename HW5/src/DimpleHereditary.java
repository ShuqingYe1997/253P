import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName: DimpleHereditary
 * @Description:
 * @Author: SQ
 * @Date: 2020-11-13
 */

class Record {
    double DD;
    double Dd;
    double dd;

    Record() {
        DD = 0.0;
        Dd = 0.0;
        dd = 0.0;
    }

    Record(double DD ,double Dd, double dd) {
        this.DD = DD;
        this.Dd = Dd;
        this.dd = dd;
    }

    double getDimpleProbability() {
        return 1.0 - dd;
    }

    // For test
    public String toString() {
        return "DD = " + DD + " ,Dd = " + Dd + ", dd = " + dd;
    }
}

class Node {
    Record record;
    String name;

    Node parent1;
    Node parent2;

    Node(){
        record = null;
        name = "";
        parent1 = null;
        parent2 = null;
    }

    Node(Record record, String name) {
        this.record = record;
        this.name = name;
    }

    Node(Record record, String name, Node parent1, Node parent2) {
        this.record = record;
        this.name = name;
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    public String toString() {
        return name + ": " + String.format("%.1f", record.getDimpleProbability() * 100)  + "%";
    }

}

class HereditaryGraph {

    List<Node> nodes;

    HereditaryGraph() {
        nodes = new ArrayList<>();
    }

    Node insert(String name, String description, String p1, String p2) {
        Node node = null;

        if (p1 == null && p2 == null) {
            node = new Node(calculate(description), name);
            nodes.add(node);
            return node;
        }

        Node parent1 = find(p1);
        Node parent2 = find(p2);

        if (parent1 == null) {
            System.err.println("Can't find parent " + p1);
            return null;
        } else if (parent2 == null) {
            System.err.println("Can't find parent " + p2);
            return null;
        } else {
            node = new Node(calculate(parent1, parent2), name, parent1, parent2);
            nodes.add(node);
        }
        return node;
    }

    // BFS
    Node find(String target) {
        Queue<Node> queue = new ArrayDeque<>();

        // 调用Arrays.asList()产生的List中add、remove方法时报异常，这是由于Arrays.asList()返回的是Arrays的内部类ArrayList，
        // 而不是java.util.ArrayList。Arrays的内部类ArrayList和java.util.ArrayList都是继承AbstractList，
        // remove、add等方法在AbstractList中是默认throw UnsupportedOperationException而且不作任何操作
        // List<Node> tmp = List.copyOf(nodes);

        List<Node> tmp = new ArrayList<>(nodes);

        while (tmp.size() > 0) {
            queue.add(tmp.get(tmp.size() - 1));
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                tmp.remove(node);
                if (node.name.compareTo(target) == 0)
                    return node;  // hit
                if (node.parent1 != null)
                    queue.add(node.parent1);
                if (node.parent2 != null)
                    queue.add(node.parent2);
            }
        }
        return null;
    }

    void printAll() {
        for(Node node: nodes)
            System.out.println(node.toString());
    }

    private Record calculate(String des) {
        Record record = new Record();
        switch (des) {
            case "DD":
                record.DD = 1.0;
                break;
            case "Dd": case "dD":
                record.Dd = 1.0;
                break;
            case "dd": case "no dimples":
                record.dd = 1.0;
                break;
            case "dimples":
                record.DD = 1.0/3;
                record.Dd = 2.0/3;
                break;
            case "unknown":
                record.DD = 1.0/4;
                record.Dd = 2.0/4;
                record.dd = 1.0/4;
                break;
        }
        return record;
    }

    private Record calculate(Node p1, Node p2) {
        Record record = new Record();

        Record r1 = p1.record;
        Record r2 = p2.record;

        record.dd = r1.dd * r2.dd + 1.0/4 * r1.Dd * r2.Dd;
        record.DD = r1.DD * r2.DD + 1.0/4 * r1.Dd * r2.Dd + 1.0/2 * r1.DD * r2.Dd + 1.0/2 * r1.Dd * r2.DD;
        record.Dd = 1 - (record.DD + record.dd);
        return record;
    }
}



public class DimpleHereditary {

    public static void main(String[] args) {
        try{
//            Scanner scanner = new Scanner(System.in);
//            String filename = scanner.nextLine();
            FileReader fileReader = new FileReader(new File("./sample_input"  +".txt"));

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            HereditaryGraph graph = new HereditaryGraph();
            while ((line = bufferedReader.readLine()) != null) {
                String[] commands = line.split("\\s+");
                switch (commands.length) {
                    case 2:
                        graph.insert(commands[0], commands[1], null, null);
                        break;
                    case 3:
                        graph.insert(commands[0], null, commands[1], commands[2]);
                        break;
                    default:
                        System.err.println("Please check your input!");
                }
            }
            // Final output
            graph.printAll();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}

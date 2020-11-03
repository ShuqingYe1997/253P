import java.io.File;

public class TestFile {
    public static void main(String[] args) {
        File file1 = new File("myMusic.txt");  //succeed
        if(file1.exists())
            System.out.println("1");
        File file2 = new File("./myMusic.txt"); // succeed
        if(file2.exists())
            System.out.println("2");
        File file3 = new File("/myMusic.txt");  // failed
        if(file3.exists())
            System.out.println("3");

        System.out.println("Year Published: 2014".split(":\\s")[1]);

    }
}

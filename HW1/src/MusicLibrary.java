import java.io.*;
import java.util.Scanner;

class Song {
    private String title;
    private String artist;
    private int yearPublished;

    public Song() {
    }

    public Song(String title, String artist, int yearPublished) {
        this.title = title;
        this.artist = artist;
        this.yearPublished = yearPublished;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String toString() {
        return String.format("Title: %s, Artist: %s, Year Published: %d\n", this.title, this.artist, this.yearPublished);
    }

}

public class MusicLibrary {

    public static int currentNumbersOfSongs = 0;
    public static final int MAX_SIZE = 1024;
    public static Song[] musicLibrary = new Song[MAX_SIZE];
    public static String musicLibraryFilename = "myMusic";

    public static boolean isOverflow() {
        if (currentNumbersOfSongs >= MAX_SIZE) {
            System.out.println("The number of songs in " + musicLibraryFilename + " has reached its maximum!");
            return true;
        }
        else return false;
    }

    public static boolean isUnderflow() {
        if (currentNumbersOfSongs <= 0) {
            System.out.println("0 song in " + musicLibraryFilename + "!");
            return true;
        }
        else return false;
    }

    // todo: A LOT optimization can be done in crunch(es)! For example, heap sort/build
    // Invoke by insert: move each item to the right by one step
    public static void crunchDownFromIndex(int index) {
        for (int i = currentNumbersOfSongs; i > index; i--) {
            musicLibrary[i] = musicLibrary[i - 1];
        }
    }

    // Invoke by delete: move each item to the left by one step
    public static void crunchUpFromIndex(int index) {
        for (int i = index; i < currentNumbersOfSongs; i++) {
            musicLibrary[i] = musicLibrary[i + 1];
        }
    }

    public static int compareSongsByTitle(String t1, String t2) {
        return t1.toLowerCase().compareTo(t2.toLowerCase());
    }

    // Binary Search
    //todo: Search by title is not a good idea, we prefer sid
    public static int findIndexOfSongWithTitle(String target) {
        if(currentNumbersOfSongs == 0)
            return 0;
        int left = 0;
        int right = currentNumbersOfSongs - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (compareSongsByTitle(musicLibrary[mid].getTitle(), target) == 0) {
                return mid;
            } else if (compareSongsByTitle(musicLibrary[mid].getTitle(), target) > 0) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    public static void searchSongFromMusicLibraryByTitle() {
       if (isUnderflow())
           return;
        String title = promptInfoForSearch();
        int index = findIndexOfSongWithTitle(title);
        if (compareSongsByTitle(musicLibrary[index].getTitle(), title) == 0) {
            System.out.print(musicLibrary[index].toString());
        }
        else {
            System.out.println("No songs called " + title + "!");
        }
    }

    public static void addSongToMusicLibrary(Boolean toTerminal) {
        if(isOverflow())
            return;
        if (toTerminal) {
            Song song = promptInfoForInsert();
            int index = findIndexOfSongWithTitle(song.getTitle());
            crunchDownFromIndex(index);
            musicLibrary[index] = song;
            currentNumbersOfSongs++;
            System.out.println("---Added " + song.getTitle() + " ! Total " + currentNumbersOfSongs + " songs---");
            if(isOverflow())
                return;
        }
        else {
            try {
                readSongs();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeSongFromMusicLibraryByTitle() {
        if (isUnderflow()) {
            return;
        }
        String title = promptInfoForDelete();
        int index = findIndexOfSongWithTitle(title);
        if (compareSongsByTitle(musicLibrary[index].getTitle(), title) != 0) {
            System.out.println("No songs called " + title + "!");
            return;
        }
        while (!isUnderflow() && compareSongsByTitle(musicLibrary[index].getTitle(), title) == 0) {
            crunchUpFromIndex(index);
            currentNumbersOfSongs--;
            index = findIndexOfSongWithTitle(title);
            System.out.println("---Deleted " + title + "! Total " + currentNumbersOfSongs + " songs---");
        }
    }

    public static void printMusicLibrary(Boolean toTerminal) throws IOException{
        if (toTerminal) {
            for (int i = 0; i < currentNumbersOfSongs; i++) {
                System.out.print(i + 1 + " " + musicLibrary[i].toString());
            }
            System.out.println("---Total " + currentNumbersOfSongs + " songs---");
        } else {
            String filename = "";
            System.out.print("Input the file name:");
            Scanner scanner = new Scanner(System.in);
            filename = scanner.nextLine();
            FileOutputStream outputStream = new FileOutputStream(filename + ".txt");
            try {
                for (int i = 0; i < currentNumbersOfSongs; i++)
                    outputStream.write(musicLibrary[i].toString().getBytes());
                System.out.println("---Print " + currentNumbersOfSongs + " songs to " + filename + "---");
            } finally {
                outputStream.close();
            }
        }
    }


    public static void loadMusicLibrary() throws IOException {
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = new FileInputStream("./" + musicLibraryFilename + ".txt");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String s = "";
            while ((s = reader.readLine()) != null) {
                currentNumbersOfSongs++;
                String[] values = s.split("Title:\\s|Artist:\\s|Year Published:\\s|,\\s");  // regex!!
                musicLibrary[currentNumbersOfSongs - 1] = new Song(values[1], values[3], Integer.parseInt(values[5]));
            }
            System.out.println("---MusicLibrary Loaded---");
        } finally {
            inputStream.close();
            reader.close();
        }
    }

    public static void storeMusicLibrary() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("./" + musicLibraryFilename + ".txt");
        try {
            for (int i = 0; i < currentNumbersOfSongs; i++)
                outputStream.write(musicLibrary[i].toString().getBytes());
            System.out.println("---Save " + currentNumbersOfSongs + " songs to " + musicLibraryFilename + "---");
        } finally {
            outputStream.close();
        }
    }

    public static boolean isFileExisted(String filename) {
        File file = new File(filename + ".txt");
        return file.exists();
    }

    public static void readSongs() throws IOException{
        boolean flag = true;
        String filename = "";
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        while (flag) {
            System.out.print("Input the file name:");
            Scanner scanner = new Scanner(System.in);
            filename = scanner.nextLine();
            if (isFileExisted(filename.trim())) {
                System.out.println("Inserting songs to " + musicLibraryFilename + "...");
                flag = false;
            } else {
                System.out.println(filename + " doesn't exist!");
            }
        }
        try {
            inputStream = new FileInputStream(filename + ".txt");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String s = "";
            int cnt = 0;
            while ((s = reader.readLine()) != null) {
                cnt++;
                String[] values = s.split("Title: |Artist: |Year Published: |, ");  // regex!!
                Song song = new Song(values[1], values[3], Integer.parseInt(values[5]));
                int index = findIndexOfSongWithTitle(song.getTitle());
                crunchDownFromIndex(index);
                musicLibrary[index] = song;
                currentNumbersOfSongs++;
            }
            System.out.println("---Added " + cnt + " songs! Total " + currentNumbersOfSongs + " songs---");
        } finally {
            inputStream.close();
            reader.close();
        }
    }

    public static Song promptInfoForInsert() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Title:");
        String title = scanner.nextLine();
        if(title.trim().length() <= 0)
            promptInfoForInsert();
        System.out.print("Artist:");
        String artist = scanner.nextLine();
        System.out.print("Year Published:");
        int year = scanner.nextInt();

        return new Song(title, artist, year);
    }

    public static String promptInfoForDelete() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Title:");
        String title = scanner.nextLine();
        return title;
    }

    public static String promptInfoForSearch() {
        return promptInfoForDelete();
    }

    public static void promptInfoForInvalidInput() {
        System.out.println("Invalid input!");
    }

    public static void promptInfoForFilename() {
        boolean flag = true;
        while (flag) {
            System.out.print("Input your MusicLibrary file name(\"myMusic\" as default):");
            Scanner scanner = new Scanner(System.in);
            String filename = scanner.nextLine();
            if (filename.trim().length() > 0) {
                if (isFileExisted(filename)) {
                    System.out.println("Loading " + filename + "...");
                    musicLibraryFilename = filename;
                    flag = false;
                } else {
                    System.out.println(filename + " doesn't exist!");
                }
            }
            else {
                flag = false;
            }
        }
    }

    // R/W to Terminal or file
    public static boolean promptInfoForTerminalOrFile() {
        char c;
        do {
            System.out.println("Press 1 to read from/write to terminal, press 2 to read from/write to file:");
            Scanner scanner = new Scanner(System.in);
            c = scanner.next().charAt(0);
        }while(c != '1' && c!= '2');
        return c == '1';
    }

    public static char readCommand() {
        System.out.print(musicLibraryFilename + " Commands:");
        Scanner scanner = new Scanner(System.in);
        return scanner.next().charAt(0);
    }

//    I - insert a new song, prompt for title, artist, and year published, maintain the list in ascending alphabetical order by song title, ignore letter case when doing comparisons, but retain letter case in all stored information
//    P - print out all the entries in this MusicLibrary
//    D - delete a specified song, prompt for the title of the song to be deleted, delete all matching songs
//    L - lookup the song for a specified title, prompt for the title to look-up, use binary search which only works on sorted list.
//    Q - save the current MusicLibrary to its file and exit (quit) the program.

    public static void evaluateCommand(char c) throws IOException{
        switch (c) {
            case 'I':
            case 'i':
                addSongToMusicLibrary(promptInfoForTerminalOrFile());
                break;
            case 'D':
            case 'd':
                removeSongFromMusicLibraryByTitle();
                break;
            case 'L':
            case 'l':
                searchSongFromMusicLibraryByTitle();
                break;
            case 'P':
            case 'p':
                printMusicLibrary(promptInfoForTerminalOrFile());
                break;
            case 'Q':
            case 'q':
                storeMusicLibrary();
                break;
            default:
                promptInfoForInvalidInput();
        }
    }

    public static void runMusicLibrary() {
        System.out.println("=============================Welcome to MusicLibrary==========================");
        System.out.println("MusicLibrary is an amazing app that allows you to manage your music collection.\n" +
                "MusicLibrary is functional and easy to use. Only by typing in a few simple letters:\n" +
                "\tI/i: Insert a new song and put in its title, artist and year published.\n" +
                "\tD/d: Delete a specific song by its title, all matched songs will be deleted.\n" +
                "\tL/l: Look up a song with a specified title.\n" +
                "\tP/p: Print out all the songs in MusicLibrary.\n" +
                "\tQ/q: Save the MusicLibrary and quit the program.");
        System.out.println("==========================Enjoy your journey in MusicLibrary!=======================");
        promptInfoForFilename();
        try {
            loadMusicLibrary();
            while(true) {
                char c = readCommand();
                evaluateCommand(c);
                if (c == 'Q' || c == 'q')
                    break;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        runMusicLibrary();
    }
}


/**
 * @ClassName: Song
 * @Description:
 * @Author: SQ
 * @Date: 2020-11-3
 */
public class Song {
    protected String title;

    protected String artist;

    public Song(){
        this.title = "";
        this.artist = "";
    };

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return title + " " + "[" + artist +  "]";
    }
}


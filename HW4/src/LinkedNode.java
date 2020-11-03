/**
 * @ClassName: LinkedNode
 * @Description:
 * @Author: SQ
 * @Date: 2020-11-3
 */
public class LinkedNode {

    private Song song;
    private LinkedNode next;

    LinkedNode() {
        this.song = new Song();
        this.next = null;
    }

    LinkedNode(Song song) {
        this.song = song;
        this.next = null;
    }

    LinkedNode(Song song, LinkedNode next) {
        this.song = song;
        this.next = next;
    }

    public LinkedNode getNext() {
        return next;
    }

    public Song getSong() {
        return song;
    }

    public void setNext(LinkedNode next) {
        this.next = next;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}

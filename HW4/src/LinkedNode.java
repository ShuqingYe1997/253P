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
        this.song = null;
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

    // for updating the node
    // Java, 哼(
    public void set(LinkedNode node) {
        if(node == null) {
            this.song = null;
            this.next = null;
        }
        this.song = node.getSong();
        this.next = node.getNext();
    }

    public void set(Song song,LinkedNode node) {

        this.song = song;
        this.next = node;
    }
}

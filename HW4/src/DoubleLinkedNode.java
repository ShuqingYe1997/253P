/**
 * @ClassName: DoubleDoubleLinkedNode
 * @Description:
 * @Author: SQ
 * @Date: 2020-11-3
 */
public class DoubleLinkedNode {
    private Song song;
    private DoubleLinkedNode next;
    private DoubleLinkedNode pre;

    DoubleLinkedNode() {
        this.song = new Song();
        this.next = null;
        this.pre = null;
    }

    DoubleLinkedNode(Song song) {
        this.song = song;
        this.next = null;
        this.pre = null;
    }

    DoubleLinkedNode(Song song, DoubleLinkedNode next) {
        this.song = song;
        this.next = next;
        this.pre = null;
    }

    DoubleLinkedNode(Song song, DoubleLinkedNode next, DoubleLinkedNode pre) {
        this.song = song;
        this.next = next;
        this.pre = pre;
    }

    public DoubleLinkedNode getNext() {
        return next;
    }

    public DoubleLinkedNode getPre() {
        return pre;
    }

    public Song getSong() {
        return song;
    }

    public void setNext(DoubleLinkedNode next) {
        this.next = next;
    }

    public void setPre(DoubleLinkedNode pre) {
        this.pre = pre;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}

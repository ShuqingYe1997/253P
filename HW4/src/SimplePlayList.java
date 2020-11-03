import java.util.List;
import java.util.Random;

/**
 * @ClassName: SimplePlayList
 * @Description:
 * @Author: SQ
 * @Date: 2020-11-3
 */
public class SimplePlayList implements MusicPlayerImpl {


    protected LinkedNode head;  // The first song of playlist

    protected LinkedNode cur;  // The current song of playlist

    protected int size;  // The size of playlist

    public SimplePlayList() {
        head = null;
        cur = null;
        size = 0;
    }

    /* push: adds a song to the front of the playlist in O(1) time. */
    public void push(String string) {
        Song song = parseOneString(string);
        LinkedNode newSong = new LinkedNode(song);
        if (size == 0) {
            head = newSong;
            cur = newSong;
        }
        else {
            newSong.setNext(head);
            head = newSong;
        }
        size++;
    }

    /* queue: adds a song to the end of the playlist in O(n) time. */
    public void queue(String string) {
        Song song = parseOneString(string);
        LinkedNode newSong = new LinkedNode(song);
        if (size == 0) {
            head = newSong;
            cur = newSong;
        }
        LinkedNode linkedNode = head;
        while (linkedNode.getNext() != null) {
            linkedNode = linkedNode.getNext();
        }
        linkedNode.setNext(newSong);
        size++;
    }

    /** current: displays the current song, its previous song, and its next song in O(n) time.*/
    public void current() {
        if (size == 0)
            return ;
        else {
            String s0 = "the current song is: ";
            String s1 = "the previous song is: ";
            String s2 = "the next song is: ";
            String[] res = new String[3];

            res[0] = s0 + cur.getSong().toString();
            LinkedNode tail = head;
            while (tail.getNext() != null) {  // exit when tail.next == null
                tail = tail.getNext();
            }
            if (cur == head) {
                res[1] = s1 + tail.getSong().toString();  // prev of head is tail
                if (cur.getNext() != null)
                    res[2] = s2 + cur.getNext().getSong().toString();
                else res[2] = s2 + head.getSong().toString();   // when size == 1
            }
            else if (cur == tail) {
                LinkedNode linkedNode = head;
                while (linkedNode.getNext() != tail) {  // get the second last linkedNode
                    linkedNode = linkedNode.getNext();
                }
                res[1] = s1 + linkedNode.getSong().toString();
                res[2] = s2 + head.getSong().toString();  // next of tail is head
            }
            else {
                LinkedNode linkedNode = head;
                while (linkedNode.getNext() != cur) {
                    linkedNode = linkedNode.getNext();
                }
                res[1] = s1 + linkedNode.getSong().toString();
                res[2] = s1 + cur.getNext().getSong().toString();
            }

            for (String s: res) {
                System.out.println(s);
            }
        }
    }

    /* * delete: deletes current song in O(1) time.  Current moves to the next song.*/
    public boolean delete() {
        if (size == 0)
            return false;
        if (size == 1) {
            cur = null;
            head = null;
            size--;
        }
        else {  // when size >= 2
            if (cur.getNext() == null) {  // cur is the tail, use O(n) method
                cur = head;
                while (cur.getNext().getNext() != null) {    // the second last node
                    cur = cur.getNext();    // this is O(n), I have no choice
                }
                cur.setNext(null);
            }
            else {  // cur is not tail
                cur.setSong(cur.getNext().getSong());  // copy next and discard next
                cur.setNext(cur.getNext().getNext());
                // with this method, head don't have to change because it stays where it is
            }
            size--;
        }
        return true;
    }

    /* * prev: makes the previous song the new current song in O(n) time
    (the “prev” of the first song should be the last song).*/
    public void prev() {
        if (size == 0 || size == 1)
            return;
        if (cur == head) {
            while (cur.getNext() != null)
                cur = cur.getNext();
        }
        else {
            LinkedNode linkedNode = head;
            while (linkedNode.getNext() != cur)
                linkedNode = linkedNode.getNext();
            cur = linkedNode;
        }
    }

    /* * next: makes the next song the new current song  in O(1) time
    (the “next” of the last song should be the first song). */
    public void next() {
        if (size == 0 || size == 1)
            return;
        if (cur.getNext() == null)
            cur = head;
        else {
            cur = cur.getNext();
        }
    }

    /* * restart: makes the first song in the list the new current song in O(1) time */
    public void restart() {
        cur = head;
    }

    /* * find: in O(n) time finds the queried song, and in O(1) time from when the song is found,
    is able to display it, its previous song, and its next song.
     */
    public boolean find(String s) {
        String[] res = {};
        if (size == 0) {
            System.out.println("cannot find song!");
            return false;
        }
        Song target = parseOneString(s);
        LinkedNode curLinkedNode = head, preLinkedNode = null;
        if (search(curLinkedNode, preLinkedNode, target.getTitle())) {
            current(curLinkedNode, preLinkedNode);
            return true;
        }

        else return false;
    }


    /* * changeTo: changes current song to the song entered by the user in O(n) time */
    public boolean changeTo(String s) {
        Song target = parseOneString(s);
        LinkedNode linkedNode = head;
        if( search(linkedNode, new LinkedNode(), target.getTitle()) ){
            cur = linkedNode;
            return true;
        }
        return false;
    }


    /** addBefore: adds a song before another existing song in O(n) time*/
    public boolean addBefore(String string) {
        Song[] songs = parseTwoSongStrings(string);
        LinkedNode curLinkedNode = head, preLinkedNode = head;
        if (search(curLinkedNode, preLinkedNode, songs[0].getTitle())) {
            preLinkedNode.setNext(new LinkedNode(songs[1], curLinkedNode));
            size++;
            return true;
        }
        return false;
    }


    /* * addAfter: adds a song after another existing song in O(n) time*/
    public boolean addAfter(String string) {
        Song[] songs = parseTwoSongStrings(string);
        LinkedNode curLinkedNode = head, preLinkedNode = head;
        if (search(curLinkedNode, preLinkedNode, songs[0].getTitle())) {
            LinkedNode tmp = curLinkedNode.getNext();
            curLinkedNode.setNext(new LinkedNode(songs[1], tmp));
            size++;
            return true;
        }
        return false;
    }

    /* * random: makes a random song the new current song in O(n) time*/
    public void random() {
        Random r = new Random(System.currentTimeMillis());
        int n = r.nextInt(size);
        while (n-- > 0) {
            moveNext();
        }
    }

    /* print: displays the playlist (in order) in O(n) time*/
    public void print() {
        System.out.println("the playlist is:");
        if (size == 0)
            return;
        LinkedNode linkedNode = head;
        int cnt = 0;
        while (linkedNode != null) {
            System.out.println(cnt + ". " + linkedNode.getSong().toString());
        }
    }

    protected Song parseOneString(String string)  {
        StringBuffer sb = new StringBuffer();
        String s1 = "", s2 = "";
        for (char c : string.toCharArray()) {
            if (c != '[' && c != ']')
                sb.append(c);
            else {
                if (c == '[') {
                    s1 = sb.toString().trim();
                    sb = new StringBuffer();
                }
                else
                    s2 = sb.toString().trim();
            }
        }
        return new Song(s1, s2);
    }

    protected Song[] parseTwoSongStrings(String string) {

        StringBuffer sb = new StringBuffer();
        String s1 = "", s2 = "";
        int i = 0;
        for (; i < string.length(); i++) {
            if(string.charAt(i) == ']')
                break;
            sb.append(string.charAt(i));
        }
        sb.append(string.charAt(i));
        Song song1 = parseOneString(sb.toString());
        Song song2 = parseOneString(string.substring(i + 1));
        return new Song[]{song1, song2};
    }

    protected void current(LinkedNode curLinkedNode, LinkedNode preLinkedNode) {
        String s0 = "the current song is: ";
        String s1 = "the previous song is: ";
        String s2 = "the next song is: ";
        String[] res = new String[3];

        res[0] = s0 + curLinkedNode.getSong().toString();
        if (curLinkedNode.getNext() == null) {  // curLinkedNode is tail
            res[1] = s1 + preLinkedNode.getSong().toString();
            res[2] = s2 + head.getSong().toString();  // next of tail is head
        }
        else {
            res[1] = s1 + preLinkedNode.getSong().toString();
            res[2] = s1 + curLinkedNode.getNext().getSong().toString();
        }
        for (String s: res)
            System.out.println(s);
    }

    protected boolean search(LinkedNode curLinkedNode, LinkedNode preLinkedNode, String target) {
        
    }

    protected void moveNext() {
        if (cur.getNext() == null) {
            cur = head;
        }
        else cur = cur.getNext();
    }

}

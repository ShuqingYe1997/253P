
public interface MusicPlayerImpl {
    /* push: adds a song to the front of the playlist in O(1) time. */
    void push(String string);

    /* queue: adds a song to the end of the playlist in O(n) time. */
    void queue(String string);

    /** current: displays the current song, its previous song, and its next song in O(n) time.*/
    void current();

    /* * delete: deletes current song in O(1) time.  Current moves to the next song.*/
    boolean delete();

    /* * prev: makes the previous song the new current song in O(n) time
    (the “prev” of the first song should be the last song).*/
    void prev();

    /* * next: makes the next song the new current song  in O(1) time
    (the “next” of the last song should be the first song). */
    void next();

    /* * restart: makes the first song in the list the new current song in O(1) time */
    void restart();

    /* * find: in O(n) time finds the queried song, and in O(1) time from when the song is found,
    is able to display it, its previous song, and its next song.
     */
    boolean find(String string);

    /* * changeTo: changes current song to the song entered by the user in O(n) time */
    boolean changeTo(String string);

    /** addBefore: adds a song before another existing song in O(n) time*/
    boolean addBefore(String string);

    /* * addAfter: adds a song after another existing song in O(n) time*/
    boolean addAfter(String string);

    /* * random: makes a random song the new current song in O(n) time*/
    void random();

    /* print: displays the playlist (in order) in O(n) time*/
    void print();
}

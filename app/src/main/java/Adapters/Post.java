package Adapters;

import android.media.Image;

import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class Post implements Comparable {
    private String title, content, author;
    private long date;
    private Image img;
    private ArrayList<Reply> replies;

    public Post(DocumentSnapshot doc) {
        try {
            title = (String) doc.get("title");
            content = (String) doc.get("content");
            author = (String) doc.get("email");
            date = (long) doc.get("date");
            replies = new ArrayList<>();
        } catch (Exception e) {
        }
        /*
        Access to image in storage and convert to Image object here.
         */
        /*
        for loop with making replies.
         */
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public long getDate() {
        return date;
    }

    public String getDateToString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Israel"));
        return dateFormat.format(date);
    }

    public Image getImg() {
        return img;
    }

    public ArrayList<Reply> getReplies() {
        return replies;
    }

    public void addReply(Reply r) {
        replies.add(r);
    }

    @Override
    public int compareTo(Object p) {
        return (int) (((Post) p).date - date);
    }
}

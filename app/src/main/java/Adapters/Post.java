package Adapters;

import android.media.Image;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class Post implements Comparable {
    private String title, content, author;
    private long date;
    private Image img;
    private ArrayList<Reply> replies;
    private DocumentSnapshot post;
    private int id;

    public Post(DocumentSnapshot doc) {
        try {
            title = (String) doc.get("title");
            content = (String) doc.get("content");
            author = (String) doc.get("email");
            date = (long) doc.get("date");
            replies = new ArrayList<>();
            post = doc;
            id = (int) doc.get("postID");
            setReplies();
        } catch (Exception e) {
        }
        /*
        Access to image in storage and convert to Image object here.
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

    public DocumentSnapshot getPost() {
        return post;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Reply> getReplies() {
        return replies;
    }

    public void addReply(Reply r) {
        replies.add(r);
    }

    private void setReplies() {
        post.getReference().collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) // iterate over the replies
                    for (DocumentSnapshot reply : task.getResult())
                        replies.add(new Reply(reply));
            }
        });
    }

    @Override
    public int compareTo(Object p) {
        return (int) (((Post) p).date - date);
    }
}

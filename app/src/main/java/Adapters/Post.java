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
    private String title, content, username;
    private long date;
    private ArrayList<Reply> replies;
    private DocumentSnapshot post;
    private int id;

    public Post(DocumentSnapshot doc) {
        try {
            title = (String) doc.get("title");
            content = (String) doc.get("content");
            username = ((String) doc.get("email")).split("@")[0];
            date = (long) doc.get("date");
            replies = new ArrayList<>();
            post = doc;
            id = doc.getDouble("postID").intValue();
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
        return username;
    }

    public long getDate() {
        return date;
    }

    public String getDateToString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Israel"));
        return dateFormat.format(date);
    }

    public DocumentSnapshot getPost() {
        return post;
    }

    public int getPostID() {
        return id;
    }

    public ArrayList<Reply> getReplies() {
        return replies;
    }

    public void setReplies() {
        replies.removeAll(replies); // clear the list
        post.getReference().collection("replies").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) // iterate over the replies
                    for (DocumentSnapshot reply : task.getResult())
                        if(!reply.getReference().getPath().contains("reply_counter")) // if it's a reply and not the counter
                             replies.add(new Reply(reply, id));
            }
        });
    }

    @Override
    public int compareTo(Object p) {
        return (int) (((Post) p).date - date);
    }
}

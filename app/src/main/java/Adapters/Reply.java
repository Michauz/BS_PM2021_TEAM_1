package Adapters;

import android.media.Image;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

public class Reply {
    private String username, content;
    private Date date;
    private Image img;
    private int id;

    public Reply(DocumentSnapshot reply) {
        username = (String) reply.get("username");
        content = (String) reply.get("content");
        date = (Date) reply.get("date");
        id = (int) reply.get("ID");
        /*
        Insert Image processing code here.
         */
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public Image getImg() {
        return img;
    }

    public int getId() {
        return id;
    }

}

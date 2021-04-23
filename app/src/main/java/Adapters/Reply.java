package Adapters;

import android.media.Image;

import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Reply {
    private String username, content;
    private long date;
    private Image img;
    private int id;

    public Reply(DocumentSnapshot reply) {
        username = (String) reply.get("username");
        content = (String) reply.get("content");
        date = (long) reply.get("date");
        id = reply.getDouble("ID").intValue();
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

    public long getDate() {
        return date;
    }

    public Image getImg() {
        return img;
    }

    public int getId() {
        return id;
    }

    public String getDateToString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Israel"));
        return dateFormat.format(date);
    }

}

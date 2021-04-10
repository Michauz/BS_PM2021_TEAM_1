package Adapters;

import android.media.Image;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

public class Reply {
    private String author,msg;
    private Date date;
    private Image img;

    public Reply(DocumentSnapshot reply){
        author = (String) reply.get("username");
        msg = (String) reply.get("content");
        date = (Date) reply.get("date");
        /*
        Insert Image processing code here.
         */
    }
}

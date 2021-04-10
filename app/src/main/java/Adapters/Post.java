package Adapters;

import android.media.Image;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class Post {
    private String title,content,author;
    private Image img;
    private ArrayList<Reply> replies;

    public Post(DocumentSnapshot doc){
        title = (String) doc.get("title");
        content = (String) doc.get("content");
        author = (String) doc.get("username");
        /*
        Access to image in storage and convert to Image object here.
         */
        /*
        for loop with making replies.
         */
    }

    public String getTitle(){
        return title;
    }
}

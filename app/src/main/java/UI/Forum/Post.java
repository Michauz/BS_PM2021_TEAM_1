package UI.Forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import Adapters.Authentication;
import Adapters.CloudFireStore;
import Adapters.Reply_ListAdapter;
import app.msda.qna.R;

public class Post extends AppCompatActivity {
    public static Adapters.Post post;
    private EditText reply;
    private int replyID;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setPost();
        reply = findViewById(R.id.replyContent);

       post.getPost().getReference().collection("replies").document("reply_counter").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) // get the reply ID from DB
                                replyID = document.getDouble("counter").intValue();
                        }
                    }
                });

        list = findViewById(R.id.postReplies);
        updateList();
    }

    private void setPost() {
        ((TextView) findViewById(R.id.postTitle)).setText(post.getTitle());
        ((TextView) findViewById(R.id.postAuthor)).setText(post.getAuthor());
        ((TextView) findViewById(R.id.postDate)).setText(post.getDateToString());
        ((TextView) findViewById(R.id.postContent)).setText(post.getContent());
    }

    public void reply(View view) {
        if (reply.getText().toString().equals("")) {
            Toast.makeText(this, "The content is empty!", Toast.LENGTH_SHORT);
            return;
        }

        Map<String, Object> reply = new HashMap<>();
        reply.put("username", post.getAuthor());
        reply.put("content", this.reply.getText().toString());
        reply.put("ID", ++replyID);
        reply.put("date", (new Date()).getTime());
        //reply.put("image", ...);

        post.getPost().getReference().collection("replies").document("reply " + replyID).set(reply)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        updateRepliesCounter();
                        post.setReplies(); // update the replies list
                        updateList();// update the list
                    }
                });
    }

    private void updateList(){
        list.setAdapter(new Reply_ListAdapter(this, post.getReplies()));// update the list
    }

    private void updateRepliesCounter() {
        Map<String, Object> counter = new HashMap<>();
        counter.put("counter", replyID);

        post.getPost().getReference().collection("replies").document("reply_counter")
                .set(counter).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
}
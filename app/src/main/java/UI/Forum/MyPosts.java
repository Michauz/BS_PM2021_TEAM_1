package UI.Forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import Adapters.Authentication;
import Adapters.CloudFireStore;
import Adapters.Post;
import app.msda.qna.R;

public class MyPosts extends AppCompatActivity {
    private CloudFireStore db;
    private ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        posts = new ArrayList<>();
        getPosts();
    }

    private void getPosts(){
        db.getInstance().collection("posts")
                .whereEqualTo("email", Authentication.getCurrentUser().getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        posts.add(new Post(document));
                    }
                    posts.sort(null);
                    listOfPosts();
                } else {
                    finish();
                }
            }
        });
    }

    private void listOfPosts(){
        for(int i=0;i<posts.size();i++){
            //Params var for all views.
            RelativeLayout.LayoutParams params;
            //New line of a pending supporter with settings.
            RelativeLayout newline = new RelativeLayout(this);
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            newline.setLayoutParams(params);
            //Button as post
            Button post = new Button(this);
            post.setId(i);
            post.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            if(posts.get(i).getTitle().length()>100)
                post.setText(posts.get(i).getTitle().substring(0,100));
            else
                post.setText(posts.get(i).getTitle());
            post.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    UI.Forum.Post.post = posts.get(v.getId());
                    goToPost();
                }
            });
            params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            newline.addView(post,params);
            ((LinearLayout)findViewById(R.id.insideScroll)).addView(newline);
        }
    }

    private void goToPost(){
        startActivity(new Intent(this, UI.Forum.Post.class));
    }

    public CloudFireStore getDB() {
        return db;
    }
    public ArrayList getMyPostsList()
    {
        return posts;
    }
}
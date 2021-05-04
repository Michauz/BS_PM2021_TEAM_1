package UI.Forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import Adapters.Authentication;
import Adapters.CloudFireStore;
import Adapters.Post;
import app.msda.qna.R;

import static Adapters.Authentication.getCurrentUser;

public class MyPosts extends AppCompatActivity {
    private ArrayList<Post> posts, replies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        posts = new ArrayList<>();
        replies = new ArrayList<>();
        getPosts();
        ((RadioButton)findViewById(R.id.posts)).setChecked(true);
        ((RadioGroup)findViewById(R.id.postsORreplies)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(((RadioButton)findViewById(R.id.posts)).isChecked())
                        listOfPosts(posts);
                else
                        listOfPosts(replies);
            }
        });
    }

    private void getPosts(){
        CloudFireStore.getInstance().collection("posts")
                .whereEqualTo("email", Authentication.getCurrentUser().getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        posts.add(new Post(document));
                    }
                    posts.sort(null);
                    listOfPosts(posts);
                } else {
                    finish();
                }
            }
        });
        CloudFireStore.getInstance().collection("posts")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        document.getReference().collection("replies").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot reply:task.getResult()){
                                        if(!reply.getId().equals("reply_counter") && reply.get("username").toString().equals(getCurrentUser().getEmail().split("@")[0])){
                                            replies.add(new Post(document));
                                            break;
                                        }
                                    }
                                }
                            }
                        });
                    }
                    replies.sort(null);
                } else {
                    finish();
                }
            }
        });
    }

    private void listOfPosts(ArrayList<Post> posts){
        ((LinearLayout)findViewById(R.id.insideScroll)).removeAllViews();
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

    public ArrayList<Post> getReplies() {
        return replies;
    }

    public ArrayList getMyPostsList()
    {
        return posts;
    }
}
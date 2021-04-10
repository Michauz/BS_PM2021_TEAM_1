package UI.Forum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import app.msda.qna.R;

public class Post extends AppCompatActivity {
    public static Adapters.Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setPost();
    }

    private void setPost(){
        ((TextView)findViewById(R.id.postTitle)).setText(post.getTitle());
        ((TextView)findViewById(R.id.postAuthor)).setText(post.getAuthor());
        ((TextView)findViewById(R.id.postDate)).setText(post.getDateToString());
        ((TextView)findViewById(R.id.postContent)).setText(post.getContent());
    }

    public void reply(View view){

    }
}
package UI.Forum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import app.msda.qna.R;

public class MyPosts extends AppCompatActivity {

    private String[] myPosts_titles;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        initiateTitles();
        adapter=new ArrayAdapter<>(this,R.layout.activity_my_posts,myPosts_titles);
    }

    private void initiateTitles() {
    }
}
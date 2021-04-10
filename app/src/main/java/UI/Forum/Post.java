package UI.Forum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import app.msda.qna.R;

public class Post extends AppCompatActivity {
    public static Adapters.Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
    }
}
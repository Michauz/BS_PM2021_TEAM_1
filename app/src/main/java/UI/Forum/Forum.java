package UI.Forum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import app.msda.qna.R;

public class Forum extends AppCompatActivity {
    static public String forumName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
    }
}
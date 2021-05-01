package UI.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import UI.LoginActivity;
import app.msda.qna.R;

import static Adapters.Authentication.getCurrentUser;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void UserManagement(View view) {
        startActivity(new Intent(this, UserManagementActivity.class));
        Update();
    }

    private void Update(){
        /* Finish if user isn't Admin - for safety measures
        if(getCurrentUser())
        */
    }
}
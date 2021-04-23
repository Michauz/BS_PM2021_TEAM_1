package app.msda.qna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import UI.Forum.MyPosts;
import UI.Forum.UploadPost;
import UI.LoginActivity;
import UI.SignUpActivity;

import static Adapters.Authentication.getCurrentUser;
import static Adapters.Authentication.getInstance;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import Adapters.Permissions;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Permissions.begForPermissions(this);
        Update();
    }

    private void Update() {
        if (getCurrentUser() != null) {
            (findViewById(R.id.goto_sign_in)).setVisibility(View.GONE);
            (findViewById(R.id.goto_sign_up)).setVisibility(View.GONE);
            (findViewById(R.id.goto_sign_out)).setVisibility(View.VISIBLE);
            (findViewById(R.id.goto_my_posts)).setVisibility(View.VISIBLE);
            (findViewById(R.id.goto_new_post)).setVisibility(View.VISIBLE);
            TextView user = findViewById(R.id.user);
            user.setVisibility(View.VISIBLE);
            user.setText(getCurrentUser().getEmail().split("@")[0]);
        } else {
            (findViewById(R.id.goto_sign_in)).setVisibility(View.VISIBLE);
            (findViewById(R.id.goto_sign_up)).setVisibility(View.VISIBLE);
            (findViewById(R.id.goto_sign_out)).setVisibility(View.GONE);
            (findViewById(R.id.goto_my_posts)).setVisibility(View.GONE);
            (findViewById(R.id.goto_new_post)).setVisibility(View.GONE);
            (findViewById(R.id.user)).setVisibility(View.GONE);
        }
    }

    public void SignIn(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        Update();
    }

    public void SignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
        Update();
    }

    public void SignOut(View view) {
        getInstance().signOut();
        Update();
    }

    public void MyPosts(View view){
        startActivity(new Intent(this, MyPosts.class));
        Update();
    }

    public void NewPost(View view){
        startActivity(new Intent(this, UploadPost.class));
        Update();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE)
            // output for user (to inform him if the permission is granted or denied)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT);
    }
}
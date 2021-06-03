package app.msda.qna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import Adapters.Authentication;
import Adapters.CloudFireStore;
import Adapters.Post;
import UI.Admin.AdminActivity;
import UI.Forum.Forum;
import UI.Forum.MyPosts;
import UI.Forum.UploadPost;
import UI.LoginActivity;
import UI.SignUpActivity;

import static Adapters.Authentication.getCurrentUser;
import static Adapters.Authentication.getInstance;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.rpc.context.AttributeContext;

import java.util.ArrayList;

import Adapters.Permissions;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private boolean isAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Permissions.begForPermissions(this);
        isAdmin=false;
        Update();
    }

    private void Update() {
        buttonsUpdate();
        if(isAdmin)
            addForums(new ArrayList<String>() {{
                add("Math");
                add("Physics");
                add("Literature");
            }});
        else
            forumUpdate();
    }

    private void buttonsUpdate(){
        (findViewById(R.id.goto_admin)).setVisibility(View.GONE);
        if (getCurrentUser() != null) {
            CloudFireStore.getInstance().collection("users").document(Authentication.getCurrentUser().getEmail()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists() && document.getLong("permission")==2) // get the reply ID from DB
                                {
                                    (findViewById(R.id.goto_admin)).setVisibility(View.VISIBLE);
                                    isAdmin=true;
                                }
                            }
                        }
                    });
            (findViewById(R.id.goto_sign_in)).setVisibility(View.GONE);
            (findViewById(R.id.goto_sign_up)).setVisibility(View.GONE);
            (findViewById(R.id.goto_sign_out)).setVisibility(View.VISIBLE);
            (findViewById(R.id.goto_my_posts)).setVisibility(View.VISIBLE);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone((ConstraintLayout) findViewById(R.id.main));
            constraintSet.connect(R.id.ForumListScroll,ConstraintSet.TOP,R.id.goto_sign_out,ConstraintSet.BOTTOM,16);
            constraintSet.applyTo(findViewById(R.id.main));
            TextView user = findViewById(R.id.user);
            user.setVisibility(View.VISIBLE);
            user.setText(getCurrentUser().getEmail().split("@")[0]);
        } else {
            (findViewById(R.id.goto_sign_in)).setVisibility(View.VISIBLE);
            (findViewById(R.id.goto_sign_up)).setVisibility(View.VISIBLE);
            (findViewById(R.id.goto_sign_out)).setVisibility(View.GONE);
            (findViewById(R.id.goto_my_posts)).setVisibility(View.GONE);
            (findViewById(R.id.user)).setVisibility(View.GONE);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone((ConstraintLayout) findViewById(R.id.main));
            constraintSet.connect(R.id.ForumListScroll,ConstraintSet.TOP,R.id.goto_sign_in,ConstraintSet.BOTTOM,16);
            constraintSet.applyTo(findViewById(R.id.main));
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
        //((LinearLayout)findViewById(R.id.ForumList)).removeAllViews();
        finish();
        startActivity(new Intent(this, MainActivity.class));
        //Update();
    }

    public void MyPosts(View view){
        startActivity(new Intent(this, MyPosts.class));
        Update();
    }

    public void Admin(View view){
        startActivity(new Intent(this, AdminActivity.class));
        Update();
    }

    public void Forum(){
        startActivity(new Intent(this, Forum.class));
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
package UI.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

import Adapters.CloudFireStore;
import app.msda.qna.R;

public class UserManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        Update();
    }

    private void Update() {
        ArrayList<String> userList = new ArrayList<>();
        CloudFireStore.getInstance().collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        userList.add(doc.getId());
                    }
                    Collections.sort(userList);
                    addUserButtons(userList);
                }
            }
        });
    }

    public void addUserButtons(ArrayList<String> userList) {
        try {
            LinearLayout userListLayout = ((LinearLayout) findViewById(R.id.userList));
            for (String user : userList) {
                Button userButton = new Button(this);
                userButton.setText(user);
                userButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        UserEditActivity.email = user;
                        goToUserEdit();
                    }
                });
                userListLayout.addView(userButton);
            }
        } catch (Exception e) {
        }
    }

    private void goToUserEdit() {
        startActivity(new Intent(this, UserEditActivity.class));
    }
}
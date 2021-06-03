package UI.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import Adapters.Authentication;
import Adapters.CloudFireStore;
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
        CloudFireStore.getInstance().collection("users").document(Authentication.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists() && doc.getLong("permission")!=2)
                            finish();
                }
            }
        });
    }
}
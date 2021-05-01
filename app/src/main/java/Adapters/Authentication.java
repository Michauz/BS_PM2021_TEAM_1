package Adapters;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Adapters.CloudFireStore.getInstance;

public class Authentication {


    public static FirebaseAuth getInstance() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getCurrentUser() {
        return getInstance().getCurrentUser();
    }

    public static String getUserID() {
        return getCurrentUser().getUid();
    }

    public static String getUsername() {
        return getCurrentUser().getDisplayName();
    }

    public static void signUp_Email_Password(Activity activity, String email, String password) {
        getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(activity, "Created user successfully", Toast.LENGTH_LONG).show();
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("uid", getUserID());
                            userData.put("subjects", new ArrayList());
                            userData.put("permission", 0);
                            CloudFireStore.getInstance().collection("users").document(email).set(userData);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(activity, "Authentication failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public static void signIn_Email_Password(Activity activity, String email, String password) {
        getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(activity, "Logged in successfully", Toast.LENGTH_LONG).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(activity, "Couldn't login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

package app.msda.qna;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import org.junit.Test;

import static Adapters.Authentication.getCurrentUser;
import static Adapters.Authentication.getInstance;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class LoginUT {
    private static boolean task_isSuccessful;
    @Test
    public void createUser_isSuccessful() {
        getInstance().createUserWithEmailAndPassword("test@gmail.com", "test")
                .addOnCompleteListener(new Activity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        task_isSuccessful=task.isSuccessful();
                    }
                });
        getCurrentUser().delete();
        assertEquals(task_isSuccessful,true);
    }
}

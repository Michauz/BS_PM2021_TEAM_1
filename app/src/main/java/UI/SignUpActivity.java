package UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import app.msda.qna.R;

import static Adapters.Authentication.getCurrentUser;
import static Adapters.Authentication.signIn_Email_Password;
import static Adapters.Authentication.signUp_Email_Password;

public class SignUpActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if(getCurrentUser()!=null)
            finish();
        setListenersAndValidators();
    }

    private void setListenersAndValidators(){
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.sign_up);
        //btn.setClickable(true);
        email.addTextChangedListener (new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2){
                checkFields();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        password.addTextChangedListener (new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2){
                checkFields();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void checkFields(){
        if(!TextUtils.isEmpty(email.getText()) && Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()&&!TextUtils.isEmpty(password.getText()))
            btn.setEnabled(true);
        else
            btn.setEnabled(false);
    }

    public void SignUp(View view){
        String email = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        signUp_Email_Password(this,email,password);
        if(getCurrentUser()!=null)
            finish();
    }
}
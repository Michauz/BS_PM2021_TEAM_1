package app.msda.qna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import UI.LoginActivity;
import UI.SignUpActivity;

import static Adapters.Authentication.getCurrentUser;
import static Adapters.Authentication.getInstance;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Update();
    }

    private void Update(){
        if(getCurrentUser()!=null){
            ((Button)findViewById(R.id.goto_sign_in)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.goto_sign_up)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.goto_sign_out)).setVisibility(View.VISIBLE);
            TextView user = (TextView)findViewById(R.id.user);
            user.setVisibility(View.VISIBLE);
            user.setText("Hey "+getCurrentUser().getEmail());
        }
        else{
            ((Button)findViewById(R.id.goto_sign_in)).setVisibility(View.VISIBLE);
            ((Button)findViewById(R.id.goto_sign_up)).setVisibility(View.VISIBLE);
            ((Button)findViewById(R.id.goto_sign_out)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.user)).setVisibility(View.GONE);
        }
    }
    public void SignIn(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void SignUp(View view){
        startActivity(new Intent(this, SignUpActivity.class));
    }
    public void SignOut(View view){
        getInstance().signOut();
        Update();
    }
}
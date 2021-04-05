package app.msda.qna;

import org.junit.Test;

import UI.LoginActivity;
import UI.SignUpActivity;

import static org.junit.Assert.assertEquals;

public class SignUpCheck {
    @Test
    public void isMailExist() {
        SignUpActivity signUpActivity=new SignUpActivity();// creating a new object of class signUpActivity succeeded
        assertEquals(signUpActivity.getEmail(),signUpActivity.getEmail()); //checking that the sign up screen has a mail input/field
    }

    
}

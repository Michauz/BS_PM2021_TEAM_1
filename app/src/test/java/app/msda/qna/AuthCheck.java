package app.msda.qna;

import org.junit.Test;

import Adapters.Authentication;

import static org.junit.Assert.assertEquals;

public class AuthCheck {
    @Test
    public void AuthObj_Check() {
        Authentication authentication=new Authentication();// creating a new object of class Authentication succeeded
        assertEquals(true,authentication.getClass().equals(Authentication.class)); //checking that the object is a kind of Authentication
    }
}

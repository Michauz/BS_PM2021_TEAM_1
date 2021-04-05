package app.msda.qna;

import org.junit.Test;

import Adapters.Authentication;
import Adapters.Permissions;

import static org.junit.Assert.assertEquals;

public class PermissionUnitTest {
    @Test
    public void PermObj_Check() {
        Permissions authentication=new Permissions();// creating a new object of class Permissions succeeded
        assertEquals(true,authentication.getClass().equals(Permissions.class)); //checking that the object is a kind of Permissions
    }

    
}

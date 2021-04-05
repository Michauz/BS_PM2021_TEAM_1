package app.msda.qna;

import org.junit.Test;

import java.security.Permission;

import Adapters.Authentication;
import Adapters.Permissions;

import static org.junit.Assert.assertEquals;

public class PermissionUnitTest {
    private int Actual_REQUESTCODE = 1;

    @Test
    public void PermObj_Check() {
        Permissions permissions = new Permissions();// creating a new object of class Permissions succeeded
        assertEquals(true, permissions.getClass().equals(Permissions.class)); //checking that the object is a kind of Permissions
    }

    @Test
    public void RequestCode_Check() {
        Permissions permissions = new Permissions();// creating a new object of class Permissions succeeded
        assertEquals(permissions.getRequestCode(), Actual_REQUESTCODE); // check the request code for asking permissions of camera and files
    }

}

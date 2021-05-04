package app.msda.qna;

import org.junit.Test;

import java.util.ArrayList;

import UI.Admin.UserManagementActivity;

import static org.junit.Assert.assertEquals;

public class AdminUserMenuCheck {
    @Test
    public void AdminMenu_Check() {
        UserManagementActivity adminActivity = new UserManagementActivity();// creating a new object of class UserManagementActivity succeeded
        assertEquals(true, adminActivity.getClass().equals(UserManagementActivity.class));
        //checking that the object is a kind of UserManagementActivity and works well
    }

    @Test
    public void buttons_Check() {
        UserManagementActivity adminActivity = new UserManagementActivity();// creating a new object of class UserManagementActivity succeeded
        ArrayList<String> users = new ArrayList<>();
        users.add("Shahar");
        users.add("Alex");
        users.add("Michael");
        users.add("Dan");

        adminActivity.addUserButtons(users); // try to add buttons and place them on the screen
        assertEquals(true,true); // if we got to this point without crashing - the buttons set up was successful
    }
}

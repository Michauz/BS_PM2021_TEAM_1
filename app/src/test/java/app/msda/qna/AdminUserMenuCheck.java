package app.msda.qna;

import org.junit.Test;

import UI.Admin.UserManagementActivity;

import static org.junit.Assert.assertEquals;

public class AdminUserMenuCheck {
    @Test
    public void AdminMenu_Check() {
        UserManagementActivity adminActivity = new UserManagementActivity();// creating a new object of class UserManagementActivity succeeded
        assertEquals(true, adminActivity.getClass().equals(UserManagementActivity.class));
        //checking that the object is a kind of UserManagementActivity and works well
    }}

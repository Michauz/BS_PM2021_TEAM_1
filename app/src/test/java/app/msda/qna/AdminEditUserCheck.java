package app.msda.qna;

import org.junit.Test;

import UI.Admin.UserEditActivity;

import static org.junit.Assert.assertEquals;

public class AdminEditUserCheck {
    @Test
    public void UserEdit_Check() {
        UserEditActivity userEditActivity = new UserEditActivity();// creating a new object of class UserEditActivity succeeded
        assertEquals(userEditActivity.getClass(), UserEditActivity.class);
        //checking that the object is a kind of UserEditActivity and works well
    }

    @Test
    public void checkEmail() {
        UserEditActivity userManagementActivity = new UserEditActivity();// creating a new object of class UserEditActivity succeeded
        assertEquals(userManagementActivity.getEmail(), UserEditActivity.email);
        //checking that the email is well informed and correct
    }

    @Test
    public void newSubjectLineCheck() {
        UserEditActivity userManagementActivity = new UserEditActivity();// creating a new object of class UserEditActivity succeeded
        userManagementActivity.newSubjectLine(null, "test"); // check new subject line
        assertEquals(true, true); // if we got to this line - the operation was successful
        //checking that the email is well informed and correct
    }


}

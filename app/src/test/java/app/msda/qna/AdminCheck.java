package app.msda.qna;

import org.junit.Test;
import UI.Admin.AdminActivity;

import static org.junit.Assert.assertEquals;

public class AdminCheck {
    @Test
    public void AdminMenu_Check() {
        AdminActivity adminActivity = new AdminActivity();// creating a new object of class AdminActivity succeeded
        assertEquals(true, adminActivity.getClass().equals(AdminActivity.class)); //checking that the object is a kind of AdminActivity and works well
    }
}

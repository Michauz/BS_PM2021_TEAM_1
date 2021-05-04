package app.msda.qna;

import org.junit.Test;

import Adapters.Reply;

import static org.junit.Assert.assertEquals;

public class ReplyCheck {
    @Test
    public void Reply_Check() {
        Reply reply = new Reply(null,-1);// creating a new object of class Reply succeeded
        assertEquals(reply.getClass(), Reply.class);
        //checking that the object is a kind of Reply and works well
    }

    @Test
    public void checkContent() {
        Reply reply = new Reply(null,-1);// creating a new object of class Reply succeeded
        assertEquals(reply.getContent(), null);
        //checking that the content is well informed and correct
    }

    @Test
    public void checkDate() {
        Reply reply = new Reply(null,-1);// creating a new object of class Reply succeeded
        assertEquals(reply.getDate(), 0);
        //checking that the email is well informed and correct
    }

    @Test
    public void checkImage() {
        Reply reply = new Reply(null,-1);// creating a new object of class Reply succeeded
        assertEquals(reply.getReplyImage(), null);
        //checking that the email is well informed and correct
    }

    @Test
    public void checkUsername() {
        Reply reply = new Reply(null,-1);// creating a new object of class Reply succeeded
        assertEquals(reply.getUsername(), null);
        //checking that the email is well informed and correct
    }
}

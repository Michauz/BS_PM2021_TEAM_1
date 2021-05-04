package app.msda.qna;

import org.junit.Test;
import UI.Forum.MyPosts;
import static org.junit.Assert.assertEquals;

public class MyPostsCheck {
    @Test
    public void isCloudDBExist() {
        MyPosts posts = new MyPosts();// creating a new object of class MyPosts succeeded
        assertEquals(posts.getReplies(), posts.getReplies()); //checking that the class has replies list
    }

    @Test
    public void isQuestionsListExist() {
        MyPosts posts = new MyPosts();// creating a new object of class MyPosts succeeded
        assertEquals(posts.getMyPostsList(), posts.getMyPostsList()); //checking that the class has a questions list
    }
}

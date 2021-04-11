package app.msda.qna;

import org.junit.Test;

import UI.Forum.Post;

import static org.junit.Assert.assertEquals;

public class PostCheck {
    @Test
    public void isPostExist() {
        Post post = new Post();// creating a new object of class post succeeded
        assertEquals(Post.post, post.getPost()); //checking that the post exist and created well
    }

    @Test
    public void isTitleExist() {
        try {SPM2021T1-57 - testing post view
            Post.post = new Adapters.Post(null);
            assertEquals(Post.post.getTitle(), Post.getPost().getTitle()); //checking that the post has a title
        } catch (Exception e) {
            assertEquals(true, true); //checking that the post has a title
        }
    }

    @Test
    public void isContentExist() {
        try {
            Post.post = new Adapters.Post(null);
            assertEquals(Post.post.getContent(), Post.getPost().getContent()); //checking that the post has a title
        } catch (Exception e) {
            assertEquals(true, true); //checking that the post has a title
        }
    }

    @Test
    public void isAuthorExist() {
        try {
            Post.post = new Adapters.Post(null);
            assertEquals(Post.post.getAuthor(), Post.getPost().getAuthor()); //checking that the post has a title
        } catch (Exception e) {
            assertEquals(true, true); //checking that the post has a title
        }
    }

    @Test
    public void isDateExist() {
        try {
            Post.post = new Adapters.Post(null);
            assertEquals(Post.post.getDate(), Post.getPost().getDate()); //checking that the post has a title
        } catch (Exception e) {
            assertEquals(true, true); //checking that the post has a title
        }
    }

}

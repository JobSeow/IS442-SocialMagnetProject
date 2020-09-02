package socialmagnet.social;

import socialmagnet.controller.PostController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostTest {
    PostController postController = new PostController();

    @Test
    public void testMessageParsing1(){
        String message = "@daryl < will become daryl and @random < will stay as @random";
        Post post = new Post(message, "","");
        postController.populateTaggedUsers(post);
        assertEquals("daryl < will become daryl and @random < will stay as @random", post.getMessage());
    }

    @Test
    public void testPopulateTaggedUsers(){
        String message = "@daryl @job @random";
        Post post = new Post(message, "","");
        postController.populateTaggedUsers(post);
        assertEquals(2, post.getTaggedUsers().size());
    }
}
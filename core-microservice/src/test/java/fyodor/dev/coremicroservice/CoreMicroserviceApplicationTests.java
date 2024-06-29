package fyodor.dev.coremicroservice;

import fyodor.dev.coremicroservice.rest.controller.PostController;
import fyodor.dev.coremicroservice.service.CommentService;
import fyodor.dev.coremicroservice.service.PostService;
import fyodor.dev.coremicroservice.service.ProfileImageService;
import fyodor.dev.coremicroservice.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CoreMicroserviceApplicationTests { // поднимать контейнеры ручками

    @Autowired
    private PostController postController;

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ProfileImageService profileImageService;


    @BeforeAll
    static void setup() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }

    @Test
    void contextLoads() {
        assertThat(postController).isNotNull();
        assertThat(postService).isNotNull();
        assertThat(userService).isNotNull();
        assertThat(commentService).isNotNull();
        assertThat(profileImageService).isNotNull();
    }

}

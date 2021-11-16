package nevaland.springboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nevaland.springboard.controller.form.PostForm;
import nevaland.springboard.domain.Post;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void entry_points_test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board"));

        mockMvc.perform(MockMvcRequestBuilders.get("/board"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/board/write"))
                .andExpect(status().isOk());
    }

    @Test
    public void post_integration_test() throws Exception {
        // given
        String id = "1";
        String title = "test title";
        String content = "test content";
        String editedTitle = "edited title";
        String editedContent = "edited content";

        // Write post and Redirect to detail
        mockMvc.perform(MockMvcRequestBuilders.post("/board/write")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("title", title)
                        .param("content", content))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/post?id=" + id));

        // See post in list and detail
        mockMvc.perform(MockMvcRequestBuilders.get("/board"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(title)));

        mockMvc.perform(MockMvcRequestBuilders.get("/board/post?id=" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(title)))
                .andExpect(content().string(containsString(content)));

        // Edit post and Redirect changed detail
        mockMvc.perform(MockMvcRequestBuilders.get("/board/edit?id=" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(title)))
                .andExpect(content().string(containsString(content)));

        mockMvc.perform(MockMvcRequestBuilders.post("/board/edit?id=" + id)
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("title", editedTitle)
                        .param("content", editedContent))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/post?id=" + id));

        mockMvc.perform(MockMvcRequestBuilders.get("/board/post?id=" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(editedTitle)))
                .andExpect(content().string(containsString(editedContent)));

        // Delete post and Redirect board
        mockMvc.perform(MockMvcRequestBuilders.get("/board/delete?id=" + id))
                .andExpect(redirectedUrl("/board"));

        mockMvc.perform(MockMvcRequestBuilders.get("/board"))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString(editedTitle))));
    }
}
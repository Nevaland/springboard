package nevaland.springboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nevaland.springboard.controller.form.PostForm;
import nevaland.springboard.domain.Post;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void entry_points_test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board"));

        mockMvc.perform(MockMvcRequestBuilders.get("/board"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/board/write"))
                .andExpect(MockMvcResultMatchers.status().isOk());
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
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board/post?id=" + id));

        // See post in list and detail
        mockMvc.perform(MockMvcRequestBuilders.get("/board"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(title)));

        mockMvc.perform(MockMvcRequestBuilders.get("/board/post?id=" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(title)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(content)));

        // Edit post and Redirect changed detail
        mockMvc.perform(MockMvcRequestBuilders.get("/board/edit?id=" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(title)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(content)));

        mockMvc.perform(MockMvcRequestBuilders.post("/board/edit?id=" + id)
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("title", editedTitle)
                        .param("content", editedContent))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board/post?id=" + id));

        mockMvc.perform(MockMvcRequestBuilders.get("/board/post?id=" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(editedTitle)))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(editedContent)));

        // Delete post and Redirect board
        mockMvc.perform(MockMvcRequestBuilders.get("/board/delete?id=" + id))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board"));

        mockMvc.perform(MockMvcRequestBuilders.get("/board"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.containsString(editedTitle))));
    }
}
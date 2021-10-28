package nevaland.springboard.controller;

import nevaland.springboard.domain.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BoardController {

    @GetMapping("/board")
    public String postList(Model model) {
        List<Post> posts = new ArrayList<>();
        model.addAttribute("posts", posts);
        return "board/postList";
    }

    @GetMapping("/board/post")
    public String postDetail(@RequestParam Long id, Model model) {
        Post post = new Post();
        post.setId(id);
        post.setTitle("title");
        post.setContent(("contentconent"));
        model.addAttribute("post", post);
        return "board/postDetail";
    }
}

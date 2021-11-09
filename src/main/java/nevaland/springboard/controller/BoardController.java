package nevaland.springboard.controller;

import nevaland.springboard.controller.form.PostForm;
import nevaland.springboard.domain.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        // TODO: Get post from db instead of this
        Post post = new Post();
        post.setId(id);
        post.setTitle("title");
        post.setContent(("contentconent"));
        model.addAttribute("post", post);
        return "board/postDetail";
    }

    @GetMapping("/board/write")
    public String writePostForm() {
        return "board/writePost";
    }

    @PostMapping("/board/write")
    public String writePost(PostForm postForm) {
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setContent(postForm.getContent());
        // TODO: Add post to db
        return "redirect:/board";
    }
}

package nevaland.springboard.controller;

import nevaland.springboard.controller.form.PostForm;
import nevaland.springboard.domain.Post;
import nevaland.springboard.repository.PostRepository;
import nevaland.springboard.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class BoardController {

    private final PostService postService;

    @Autowired
    public BoardController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/board")
    public String postList(Model model) {
        List<Post> posts = postService.findPosts();
        model.addAttribute("posts", posts);
        return "board/postList";
    }

    @GetMapping("/board/post")
    public String postDetail(@RequestParam Long id, Model model) {
        Optional<Post> post = postService.findOne(id);
        model.addAttribute("post", post.orElseThrow(() -> new NoSuchElementException()));
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
        postService.write(post);
        return "redirect:/board";
    }

    @GetMapping("/board/delete")
    public String deletePost(@RequestParam Long id) {
        postService.delete(id);
        return "redirect:/board";
    }
}

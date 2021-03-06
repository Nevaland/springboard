package nevaland.springboard.service;

import nevaland.springboard.domain.Post;
import nevaland.springboard.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     *  Write the post
     */
    public Long write(Post post) {
        Post newPost = postRepository.save(post);
        return newPost.getId();
    }

    /**
     *  Retrieve all post
     */
    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> findOne(Long postId) {
        return postRepository.findById(postId);
    }

    /**
     *  Delete the post
     */
    public Optional<Post> delete(Long postId) {
        return postRepository.remove(postId);
    }

    /**
     *  Edit the post
     */
    public Optional<Post> edit(Long postId, Post post) {
        return postRepository.update(postId, post);
    }
}

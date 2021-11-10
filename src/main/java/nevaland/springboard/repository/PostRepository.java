package nevaland.springboard.repository;

import nevaland.springboard.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    Optional<Post> findById(Long id);
    Optional<Post> findByTitle(String title);
    List<Post> findAll();

    Optional<Post> remove(Long id);
    Optional<Post> update(Long id, Post post);
}

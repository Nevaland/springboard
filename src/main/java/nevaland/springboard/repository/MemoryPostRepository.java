package nevaland.springboard.repository;

import nevaland.springboard.domain.Post;

import java.util.*;

public class MemoryPostRepository implements PostRepository {

    private static Map<Long, Post> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Post save(Post post) {
        post.setId(++sequence);
        store.put(post.getId(), post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Post> findByTitle(String title) {
        return store.values().stream()
                .filter(post -> post.getTitle().equals(title))
                .findAny();
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Post> remove(Long id) {
        return Optional.ofNullable(store.remove(id));
    }

    @Override
    public Optional<Post> update(Long id, Post post) {
        if (store.containsKey(id)) {
            Post originPost = store.get(id);
            originPost.setTitle(post.getTitle());
            originPost.setContent(post.getContent());
            store.put(id, originPost);
            return Optional.of(originPost);
        } else {
            return Optional.empty();
        }
    }
}

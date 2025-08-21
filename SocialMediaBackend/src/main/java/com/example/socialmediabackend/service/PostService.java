package com.example.socialmediabackend.service;

import com.example.socialmediabackend.domain.*;
import com.example.socialmediabackend.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service @RequiredArgsConstructor
@CacheConfig(cacheNames = { "timeline", "postsByUser", "trending" })
public class PostService {
    private final PostRepository posts;
    private final UserRepository users;
    private final CommentRepository comments;

    // --- Queries ---
    @Cacheable(value = "timeline", key = "#page + ':' + #size")
    public Page<Post> timeline(int page, int size) {
        return posts.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
    }

    @Cacheable(value = "postsByUser", key = "#userId + ':' + #page + ':' + #size")
    public Page<Post> postsByUser(Long userId, int page, int size) {
        var user = users.findById(userId).orElseThrow();
        return posts.findByAuthor(user, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    @Cacheable(value = "trending", key = "#hours + ':' + #limit")
    public List<Post> trending(int hours, int limit) {
        var since = Instant.now().minus(hours, ChronoUnit.HOURS);
        return posts.findTrending(since, PageRequest.of(0, limit)).getContent();
    }

    // --- Mutations (evict caches) ---
    @Caching(evict = {
            @CacheEvict(value = "timeline", allEntries = true),
            @CacheEvict(value = "postsByUser", allEntries = true),
            @CacheEvict(value = "trending", allEntries = true)
    })
    public Post createPost(Long userId, String content) {
        var user = users.findById(userId).orElseThrow();
        var post = Post.builder().author(user).content(content).build();
        return posts.save(post);
    }

    @Caching(evict = {
            @CacheEvict(value = "timeline", allEntries = true),
            @CacheEvict(value = "postsByUser", allEntries = true),
            @CacheEvict(value = "trending", allEntries = true)
    })
    public Comment createComment(Long userId, Long postId, String content) {
        var user = users.findById(userId).orElseThrow();
        var post = posts.findById(postId).orElseThrow();
        var comment = Comment.builder().post(post).author(user).content(content).build();
        return comments.save(comment);
    }

    @Caching(evict = {
            @CacheEvict(value = "timeline", allEntries = true),
            @CacheEvict(value = "postsByUser", allEntries = true),
            @CacheEvict(value = "trending", allEntries = true)
    })
    public Post likePost(Long postId) {
        var post = posts.findById(postId).orElseThrow();
        post.setLikeCount(post.getLikeCount() + 1);
        return posts.save(post);
    }

    public List<Comment> commentsOf(Post post) {
        return comments.findByPostOrderByCreatedAtAsc(post);
    }
}
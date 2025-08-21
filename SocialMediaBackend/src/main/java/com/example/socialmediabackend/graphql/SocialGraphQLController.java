package com.example.socialmediabackend.graphql;

import com.example.socialmediabackend.domain.*;
import com.example.socialmediabackend.graphql.dto.PostPage;
import com.example.socialmediabackend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller @RequiredArgsConstructor
public class SocialGraphQLController {

    private final UserService userService;
    private final PostService postService;

    // --- Queries ---
    @QueryMapping
    public User user(@Argument Long id) { return userService.get(id); }

    @QueryMapping
    public PostPage postsByUser(@Argument Long userId, @Argument int page, @Argument int size) {
        return PostPage.of(postService.postsByUser(userId, page, size));
    }

    @QueryMapping
    public PostPage timeline(@Argument int page, @Argument int size) {
        return PostPage.of(postService.timeline(page, size));
    }

    @QueryMapping
    public List<Post> trending(@Argument int hours, @Argument int limit) {
        return postService.trending(hours, limit);
    }

    // --- Mutations ---
    @MutationMapping
    public User createUser(@Argument String username, @Argument String name) {
        return userService.create(username, name);
    }

    @MutationMapping
    public Post createPost(@Argument Long userId, @Argument String content) {
        return postService.createPost(userId, content);
    }

    @MutationMapping
    public Comment createComment(@Argument Long userId, @Argument Long postId, @Argument String content) {
        return postService.createComment(userId, postId, content);
    }

    @MutationMapping
    public Post likePost(@Argument Long postId) {
        return postService.likePost(postId);
    }

    // --- Field resolvers ---
    @SchemaMapping(typeName = "Post", field = "author")
    public User author(Post post) { return post.getAuthor(); }

    @SchemaMapping(typeName = "Post", field = "comments")
    public List<Comment> comments(Post post) { return postService.commentsOf(post); }
}
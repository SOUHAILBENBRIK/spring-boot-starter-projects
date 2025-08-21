package com.example.socialmediabackend.repo;

import com.example.socialmediabackend.domain.Comment;
import com.example.socialmediabackend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByCreatedAtAsc(Post post);
}

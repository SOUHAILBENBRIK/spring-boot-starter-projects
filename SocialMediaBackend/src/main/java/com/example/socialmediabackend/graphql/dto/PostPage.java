package com.example.socialmediabackend.graphql.dto;


import com.example.socialmediabackend.domain.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public record PostPage(List<Post> items, int page, int size, int totalPages, long totalElements, boolean hasNext) {
    public static PostPage of(Page<Post> p) {
        return new PostPage(p.getContent(), p.getNumber(), p.getSize(), p.getTotalPages(), p.getTotalElements(), p.hasNext());
    }
}
package com.example.socialmediabackend.repo;

import com.example.socialmediabackend.domain.Post;
import com.example.socialmediabackend.domain.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByAuthor(User author, Pageable pageable);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("""
         select p from Post p
         left join Comment c on c.post = p
         where p.createdAt >= :since
         group by p
         order by count(c) desc, p.createdAt desc
         """)
    Page<Post> findTrending(@Param("since") Instant since, Pageable pageable);
}
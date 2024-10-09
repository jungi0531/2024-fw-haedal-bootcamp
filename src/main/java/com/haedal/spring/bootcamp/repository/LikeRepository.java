package com.haedal.spring.bootcamp.repository;

//import haedal.Bootcamp2024_2.domain.Like;
//import haedal.Bootcamp2024_2.domain.Post;
//import haedal.Bootcamp2024_2.domain.User;
import com.haedal.spring.bootcamp.domain.Like;
import com.haedal.spring.bootcamp.domain.Post;
import com.haedal.spring.bootcamp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByPost(Post post);
    Optional<Like> findByUserAndPost(User user, Post post);
    boolean existsByUserAndPost(User user, Post post);
    List<Like> findByPost(Post post);
}
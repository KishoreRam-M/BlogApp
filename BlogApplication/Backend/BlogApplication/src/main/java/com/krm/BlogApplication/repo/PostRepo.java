package com.krm.BlogApplication.repo;

import com.krm.BlogApplication.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findAllByNameContaining(String name);
}

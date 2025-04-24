package com.krm.BlogApplication.service.impl;

import com.krm.BlogApplication.model.Post;
import com.krm.BlogApplication.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements com.krm.BlogApplication.service.PostService {

    @Autowired
    private PostRepo postRepo;

    @Override
    public Post savePost(Post post) {
        post.setDate(new Date());
        post.setLikeCount(0);
        return postRepo.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Override
    public Post likePost(Long id) {
        Post post = getPostById(id);
        post.setLikeCount(post.getLikeCount() + 1);
        return postRepo.save(post);
    }

    @Override
    public List<Post> searchByName(String name) {
        return postRepo.findAllByNameContaining(name);
    }
}

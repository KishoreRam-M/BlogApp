package com.krm.BlogApplication.service;

import com.krm.BlogApplication.model.Post;
import java.util.List;

public interface PostService {
    Post savePost(Post post);
    List<Post> getAllPosts();
    Post getPostById(Long id);
    Post likePost(Long id);
    List<Post> searchByName(String name);
}

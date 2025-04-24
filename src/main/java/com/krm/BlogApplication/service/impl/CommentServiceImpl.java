package com.krm.BlogApplication.service.impl;

import com.krm.BlogApplication.model.Comment;
import com.krm.BlogApplication.model.Post;
import com.krm.BlogApplication.repo.CommentRepo;
import com.krm.BlogApplication.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements com.krm.BlogApplication.service.CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Override
    public Comment createComment(Long postId, String postBy, String content) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setPostBy(postBy);
        comment.setContent(content);
        comment.setCreatedAt(new Date());
        return commentRepo.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepo.findByPostId(postId);
    }
}

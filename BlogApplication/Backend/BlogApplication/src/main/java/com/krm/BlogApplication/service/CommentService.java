package com.krm.BlogApplication.service;

import com.krm.BlogApplication.model.Comment;
import java.util.List;

public interface CommentService {
    Comment createComment(Long postId, String postBy, String content);
    List<Comment> getCommentsByPostId(Long postId);
}

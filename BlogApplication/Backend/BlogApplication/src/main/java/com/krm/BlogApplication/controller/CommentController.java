package com.krm.BlogApplication.controller;

import com.krm.BlogApplication.model.Comment;
import com.krm.BlogApplication.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestParam Long postId, @RequestParam String postBy, @RequestParam String content) {
        try {
            Comment comment = commentService.createComment(postId, postBy, content);
            return ResponseEntity.ok(comment);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating comment: " + e.getMessage());
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
        try {
            List<Comment> comments = commentService.getCommentsByPostId(postId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching comments: " + e.getMessage());
        }
    }
}

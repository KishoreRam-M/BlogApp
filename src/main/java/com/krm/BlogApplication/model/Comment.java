package com.krm.BlogApplication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Date createdAt;
    private String postBy;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private com.krm.BlogApplication.model.Post post;
}

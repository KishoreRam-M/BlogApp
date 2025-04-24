package com.krm.BlogApplication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String content;
    private Date date;
    private int likeCount;

    private int viewCount = 0;

}

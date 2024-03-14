package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostsController {
    private List<Posts> posts = new ArrayList<>();

    // Insert post
    @PostMapping
    public ResponseEntity<Posts> insertPost(@RequestBody Posts post) {
        post.setId(Posts.idCounter++);
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

}

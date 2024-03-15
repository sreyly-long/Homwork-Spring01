package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/posts")
public class PostsController {
    private List<Posts> posts = new ArrayList<>();

    // Insert post
    @PostMapping
    public ResponseEntity<Posts> insertPost(@RequestBody Posts post) {
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    // Read all posts
    @GetMapping
    public ResponseEntity<List<Posts>> getAllPosts() {
        return ResponseEntity.ok(posts);
    }
    // Read post by id
    @GetMapping("/{id}")
    public ResponseEntity<Posts> getPostByID(@PathVariable int id) {
        Optional<Posts> optionalPosts = posts.stream().filter(po -> po.getId() == id)
                .findFirst();
        return optionalPosts.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    // Read post by title
    @GetMapping("/searchByTitle")
    public ResponseEntity<Posts> getPostByTitle(@RequestParam String title) {
        Optional <Posts> optionalPosts = posts.stream().filter(po -> po.getTitle().equalsIgnoreCase(title))
                .findFirst();
        return optionalPosts.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
    // Read post by author
    public ResponseEntity<Posts> getPostByAuthor(@RequestParam String author) {
        List<Posts> foundPosts = new ArrayList<>();
        for (Posts posts1 : posts) {
            if (posts1.getAuthor().equalsIgnoreCase(author)) {
                foundPosts.add(posts1);
            }
        }
        if (foundPosts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok((Posts)foundPosts);
    }





}

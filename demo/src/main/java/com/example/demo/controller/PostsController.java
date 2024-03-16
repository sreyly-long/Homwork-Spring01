package com.example.demo.controller;

import com.example.demo.models.Posts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/posts")
public class PostsController {
    AtomicInteger id = new AtomicInteger(1);
    private List<Posts> posts = new ArrayList<>();

    public PostsController() {
        posts.add(new Posts(
                id.getAndIncrement(),
                "Java Programming",
                "Java programming is high-level",
                "Jonh Doe",
                LocalDateTime.now(),
                List.of("Java", "Programming", "OOP")
        ));
        posts.add(new Posts(
                id.getAndIncrement(),
                "C# Programming",
                "C# Programming is high-level",
                "Davide",
                LocalDateTime.now(),
               List.of("C#","Programming","OOP")
        ));
        posts.add(new Posts(
                id.getAndIncrement(),
                "Phython Programming",
                "Phython Programming i high-level",
                "wonstack",
                LocalDateTime.now(),
                List.of("Phython","Programming","OOP")
        ));

    }

    // Insert post
    @PostMapping
    public ResponseEntity<Posts> insertPost(@RequestBody Posts post) {
        post.setId(id.getAndIncrement());
        post.setCreationDate(LocalDateTime.now());
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
    //Read  all posts tage multiple
    @GetMapping("/{tags}")
    public ResponseEntity<List<Posts>> getAllPosts(@RequestParam(required = false) List<String> tags) {
        List<Posts> filterPosts = posts.stream().filter(posts1 -> tags == null || new HashSet<>(posts1.getTags()).containsAll(tags))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filterPosts);
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
        Optional<Posts> optionalPosts = posts.stream().filter(po -> po.getTitle().equalsIgnoreCase(title))
                .findFirst();
        return optionalPosts.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
    // Read post by author
    @GetMapping("/searchAuthor")
    public ResponseEntity<Posts> getPostByAuthor(@RequestParam String author) {
        Optional<Posts> optionalPosts = posts.stream().filter(po -> po.getAuthor().equalsIgnoreCase(author))
                .findFirst();
        return optionalPosts.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Update post by id
    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@PathVariable int id, @RequestBody Posts updatePost) {
        for (int i = 0; i < posts.size(); i++) {
           if (posts.get(i).getId() == id){
               updatePost.setId(id);
               updatePost.setCreationDate(posts.get(i).getCreationDate());
               posts.set(i, updatePost);
               return ResponseEntity.ok("You're update succrssfully!");
           }
        }
        return ResponseEntity.notFound().build();
    }


    // Delete post by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        for (Posts posts1 : posts) {
            if (posts1.getId() == id) {
                posts.remove(posts1);
                return ResponseEntity.ok("Congratulation your deleted is successfully!");
            }
        }
        return ResponseEntity.notFound().build();
    }


}

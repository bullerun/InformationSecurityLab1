package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.PostRequest;
import org.example.dto.PostResponse;
import org.example.model.User;
import org.example.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private PostService postService;

    @GetMapping("/data")
    public ResponseEntity<List<PostResponse>> getAllData() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<PostResponse> getDataById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping("/data")
    public ResponseEntity<PostResponse> createData(
            @Valid @RequestBody PostRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(postService.createPost(request, currentUser));
    }

    @DeleteMapping("/data/{id}")
    public ResponseEntity<String> deleteData(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        postService.deletePost(id, currentUser);
        return ResponseEntity.ok("Post deleted successfully");
    }
}

package com.haedal.spring.bootcamp.controller;

import com.haedal.spring.bootcamp.domain.Post;
import com.haedal.spring.bootcamp.domain.User;
import com.haedal.spring.bootcamp.dto.response.PostResponseDto;
import com.haedal.spring.bootcamp.dto.response.UserSimpleResponseDto;
import com.haedal.spring.bootcamp.service.AuthService;
import com.haedal.spring.bootcamp.service.ImageService;
import com.haedal.spring.bootcamp.service.LikeService;
import com.haedal.spring.bootcamp.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class PostController {
    private final PostService postService;
    private final LikeService likeService;
    private final AuthService authService;
    private final ImageService imageService;

    @Autowired
    public PostController(PostService postService, LikeService likeService, AuthService authService, ImageService imageService) {
        this.postService = postService;
        this.likeService = likeService;
        this.authService = authService;
        this.imageService = imageService;
    }


    @PostMapping("/posts")
    public ResponseEntity<Void> createPost(@RequestParam("image") MultipartFile image, @RequestParam("content") String content,
                                           HttpServletRequest request) throws IOException {
        User currentUser = authService.getCurrentUser(request);
        String imageUrl = imageService.savePostImage(image);
        Post post = new Post(currentUser, content, imageUrl);
        postService.savePost(post);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<PostResponseDto>> getPostsByUser(@PathVariable Long userId) {
        List<PostResponseDto> posts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(posts);
    }
    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long postId, HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);

        likeService.likePost(currentUser, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/posts/{postId}/like")
    public ResponseEntity<Void> unlikePost(HttpServletRequest request, @PathVariable Long postId) {
        User currentUser = authService.getCurrentUser(request);

        likeService.unlikePost(currentUser, postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/{postId}/like")
    public ResponseEntity<List<UserSimpleResponseDto>> getUsersWhoLikedPost(@PathVariable Long postId, HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);

        List<UserSimpleResponseDto> usersWhoLikedPost = likeService.getUsersWhoLikedPost(currentUser, postId);
        return ResponseEntity.ok(usersWhoLikedPost);
    }
}
package com.example.agrisupportandtorism.controller;

import com.example.agrisupportandtorism.dto.CommentDTO;
import com.example.agrisupportandtorism.dto.PostDTO;
import com.example.agrisupportandtorism.dto.ShortPostDTO;
import com.example.agrisupportandtorism.service.post.CommentService;
import com.example.agrisupportandtorism.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
@CrossOrigin("http://localhost:9528/")

public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public PostDTO findPostDTOById(@RequestParam @Valid Integer postId){
        return postService.findPostDTOById(postId);
    }

    @GetMapping("/all")
    public List<ShortPostDTO> findAll(){
        return postService.findAll();
    }

//    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @GetMapping("/page")
    public Page<ShortPostDTO> findAllPostsInPage(Pageable pageable){
        return postService.findAllPostsInPage(pageable);
    }

    @PostMapping
    public PostDTO createPost(@RequestBody @Valid PostDTO postDTO){
        return postService.addPost(postDTO);
    }

    @PutMapping
    public PostDTO updatePost(@RequestBody @Valid PostDTO postDTO){return postService.updatePost(postDTO);}

    @DeleteMapping
    public void deletePost(@RequestParam @Valid Integer postId){
        postService.deletePost(postId);
    }

    @GetMapping("/comment/all")
    public List<CommentDTO> findAllCommentsByPostId(@RequestParam Integer postId){
        return commentService.findAllCommentByPostId(postId);
    }

    @PostMapping("/comment")
    public CommentDTO createComment(@RequestBody @Valid CommentDTO commentDTO){
        return commentService.addComment(commentDTO);
    }

    @DeleteMapping("/comment")
    public void deleteComment(@RequestParam Integer commentId){
        commentService.deleteCommentById(commentId);
    }
}

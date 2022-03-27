package com.example.agrisupportandtorism.controller;

import com.example.agrisupportandtorism.dto.PostDTO;
import com.example.agrisupportandtorism.dto.ShortPostDTO;
import com.example.agrisupportandtorism.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
@CrossOrigin("http://localhost:9528/")

public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public PostDTO findPostDTOById(@RequestParam @Valid Integer postId){
        return postService.findPostDTOById(postId);
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

    @GetMapping("/all")
    public List<ShortPostDTO> findAll(){
        return postService.findAll();
    }
}

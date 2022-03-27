package com.example.agrisupportandtorism.service;

import com.example.agrisupportandtorism.dto.ShortPostDTO;
import com.example.agrisupportandtorism.dto.UserDTO;
import com.example.agrisupportandtorism.entity.Post;
import com.example.agrisupportandtorism.dto.PostDTO;
import com.example.agrisupportandtorism.entity.User;
import com.example.agrisupportandtorism.exception.PermissionException;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.PostRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserService userService;

    public List<ShortPostDTO> findAll(){
        return postRepo.findAll()
                .stream()
                .map(ShortPostDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public PostDTO addPost(PostDTO postDTO){
        UserDTO currentUser = userService.getCurrentUserInfo();
        postDTO.setCreatedUser(currentUser);

        Post post = Post.fromDTO(postDTO);
        post.setUpdatedDateTime(LocalDateTime.now());

        return PostDTO.fromEntity(postRepo.save(post));
    }

    public PostDTO updatePost(PostDTO postDTO){
        UserDTO currentUser = userService.getCurrentUserInfo();
        PostDTO postInRepo = findPostDTOById(postDTO.getId());

        if(postInRepo.getCreatedUser().getUsername().equals(currentUser.getUsername())){
            postDTO.setCreatedUser(currentUser);

            Post post = Post.fromDTO(postDTO);
            post.setUpdatedDateTime(LocalDateTime.now());

            return PostDTO.fromEntity(postRepo.save(post));
        }else{
            throw new PermissionException("the user trying to update a post not his own!");
        }
    }

    public void deletePost(Integer postId){
        Post post = isOwnPost(postId);
        if(post != null){
            postRepo.delete(post);
        }
    }

    private Post isOwnPost(Integer postId){
        Optional<Post> postOpt = postRepo.findPostById(postId);
        if(postOpt.isPresent()){
            Post post = postOpt.get();
            User currentUser = userService.getCurrentUser();
            if(currentUser.getUsername().equals(post.getCreatedUser().getUsername())){
                return post;
            }
        }
        return null;
    }

    public PostDTO findPostDTOById(Integer postId){
        Optional<Post> post = postRepo.findPostById(postId);
        if(post.isPresent()){
                return PostDTO.fromEntity(post.get());
        }else{
            throw new ResourceNotFoundException(String.format("Post with id=%s not exists", postId));
        }
    }

}

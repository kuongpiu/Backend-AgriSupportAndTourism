package com.example.agrisupportandtorism.service.post;

import com.example.agrisupportandtorism.dto.post.ShortPostDTO;
import com.example.agrisupportandtorism.dto.UserDTO;
import com.example.agrisupportandtorism.entity.post.Post;
import com.example.agrisupportandtorism.dto.post.PostDTO;
import com.example.agrisupportandtorism.entity.user.User;
import com.example.agrisupportandtorism.exception.PermissionException;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.post.PostRepo;
import com.example.agrisupportandtorism.service.address.AddressService;
import com.example.agrisupportandtorism.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private AddressService addressService;

    private Logger logger = LoggerFactory.getLogger(getClass());
    public List<ShortPostDTO> findAll(){
        return postRepo.findAll()
                .stream()
                .map(ShortPostDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<ShortPostDTO> findAllPostsInPage(Pageable pageable){
        Page<Post> postPage = postRepo.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "updatedDateTime")));

        List<ShortPostDTO> content = postPage.getContent().stream().map(ShortPostDTO::fromEntity).collect(Collectors.toList());

        return new PageImpl<>(content, postPage.getPageable(), postPage.getTotalElements());
    }

    @Transactional
    public PostDTO addPost(PostDTO postDTO){
        //TODO: check farm id
        UserDTO currentUser = userService.getCurrentUserInfo();
        postDTO.setCreatedUser(currentUser);

        Post post = Post.fromDTO(postDTO);
        post.setUpdatedDateTime(LocalDateTime.now());

        post.setProvince(addressService.findProvinceById(post.getProvince().getId()));
        post.setDistrict(addressService.findDistrictById(post.getDistrict().getId()));
        post.setWard(addressService.findWardById(post.getWard().getId()));

        Post result = postRepo.save(post);

        logger.info(String.format("Add post, id= [%s]", result.getId()));

        return PostDTO.fromEntity(result);
    }

    @Transactional
    public PostDTO updatePost(PostDTO postDTO){
        UserDTO currentUser = userService.getCurrentUserInfo();
        PostDTO postInRepo = findPostDTOById(postDTO.getId());

        if(postInRepo.getCreatedUser().getUsername().equals(currentUser.getUsername())){
            postDTO.setCreatedUser(currentUser);

            Post post = Post.fromDTO(postDTO);
            post.setUpdatedDateTime(LocalDateTime.now());

            logger.info(String.format("Update post, id=[%s]", post.getId()));

            return PostDTO.fromEntity(postRepo.save(post));
        }else{
            throw new PermissionException("the user trying to update a post not his own!");
        }
    }

    @Transactional
    public void deletePost(Integer postId){
        logger.info(String.format("Delete post, id=[%s]", postId));
        postRepo.delete(isOwnPost(postId));
    }

    private Post isOwnPost(Integer postId){
        Optional<Post> postOpt = postRepo.findPostById(postId);
        if(postOpt.isPresent()){
            Post post = postOpt.get();
            User currentUser = userService.getCurrentUser();
            if(currentUser.getUsername().equals(post.getCreatedUser().getUsername())){
                return post;
            }else{
                throw new PermissionException(String.format("this post owner is [%s], not [%s]",post.getCreatedUser().getUsername(), currentUser.getUsername()));
            }
        }else{
            throw new ResourceNotFoundException(String.format("a post with id= [%s] is not exists !", postId));
        }
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

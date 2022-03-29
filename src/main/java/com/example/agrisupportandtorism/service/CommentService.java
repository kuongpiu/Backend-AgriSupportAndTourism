package com.example.agrisupportandtorism.service;

import com.example.agrisupportandtorism.dto.CommentDTO;
import com.example.agrisupportandtorism.dto.UserDTO;
import com.example.agrisupportandtorism.entity.Comment;
import com.example.agrisupportandtorism.entity.User;
import com.example.agrisupportandtorism.exception.PermissionException;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserService userService;

    public List<CommentDTO> findAllCommentByPostId(Integer postId){
        return commentRepo.findByPostId(postId, Sort.by(Sort.Direction.DESC, "createdDateTime")).stream().map(CommentDTO::fromEntity).collect(Collectors.toList());
    }

    public CommentDTO addComment(CommentDTO commentDTO){
        System.out.println("commentDTO: " + commentDTO);
        UserDTO currentUser = userService.getCurrentUserInfo();

        commentDTO.setCreatedUser(currentUser);
        Comment comment = Comment.fromDTO(commentDTO);
        comment.setCreatedDateTime(LocalDateTime.now());
        System.out.println("comment: " + comment);
        return CommentDTO.fromEntity(commentRepo.save(comment));
    }

    public void deleteCommentById(Integer commentId){
        commentRepo.delete(isOwnComment(commentId));
    }

    private Comment isOwnComment(Integer commentId){
        Optional<Comment> commentOpt = commentRepo.findCommentById(commentId);

        if(commentOpt.isPresent()){
            Comment comment = commentOpt.get();
            User currentUser = userService.getCurrentUser();
            if(currentUser.getUsername().equals(comment.getCreatedUser().getUsername())){
                return comment;
            }else{
                throw new PermissionException(String.format("this comment owner is [%s], not [%s]",comment.getCreatedUser().getUsername(), currentUser.getUsername()));
            }
        }else{
            throw new ResourceNotFoundException(String.format("a comment with id= [%s] is not exists !", commentId));
        }
    }
}

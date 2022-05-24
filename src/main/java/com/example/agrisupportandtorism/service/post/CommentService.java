package com.example.agrisupportandtorism.service.post;

import com.example.agrisupportandtorism.dto.comment.CommentDTO;
import com.example.agrisupportandtorism.dto.UserDTO;
import com.example.agrisupportandtorism.entity.post.Comment;
import com.example.agrisupportandtorism.entity.user.User;
import com.example.agrisupportandtorism.exception.PermissionException;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.post.CommentRepo;
import com.example.agrisupportandtorism.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<CommentDTO> findAllCommentByPostId(Integer postId){
        return commentRepo.findByPostId(postId, Sort.by(Sort.Direction.DESC, "createdDateTime")).stream().map(CommentDTO::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO addComment(CommentDTO commentDTO){
        UserDTO currentUser = userService.getCurrentUserInfo();

        commentDTO.setCreatedUser(currentUser);
        Comment comment = Comment.fromDTO(commentDTO);
        comment.setCreatedDateTime(LocalDateTime.now());

        Comment result = commentRepo.save(comment);

        logger.info(String.format("Add comment, id=[%s]", result.getId()));

        return CommentDTO.fromEntity(result);
    }

    @Transactional
    public void deleteCommentById(Integer commentId){
        logger.info(String.format("Delete comment, id= [%s]", commentId));
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

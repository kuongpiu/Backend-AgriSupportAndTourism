package com.example.agrisupportandtorism.dto;

import com.example.agrisupportandtorism.entity.post.Comment;
import com.example.agrisupportandtorism.utils.DateTimeUtil;
import com.example.agrisupportandtorism.utils.UrlUntil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter
@Getter

public class CommentDTO {
    private Integer id;

    @NotNull(message = "post id cannot be null")
    private Integer postId;
    private String body;
    private List<String> imageUrls;
    private UserDTO createdUser;
    private String createdDate;
    private String createdTime;

    private CommentDTO(){}

    public static CommentDTO fromEntity(Comment comment){
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setBody(comment.getBody());
        commentDTO.setImageUrls(UrlUntil.convertStringToUrlList(comment.getRawStringImageUrl()));
        commentDTO.setCreatedUser(UserDTO.fromUser(comment.getCreatedUser()));
        commentDTO.setPostId(comment.getPostId());

        LocalDateTime createdDateTime = comment.getCreatedDateTime();
        commentDTO.setCreatedDate(createdDateTime.format(DateTimeUtil.DATE_FORMATTER));
        commentDTO.setCreatedTime(createdDateTime.format(DateTimeUtil.TIME_FORMATTER));

        return commentDTO;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", postId=" + postId +
                ", body='" + body + '\'' +
                ", imageUrls=" + imageUrls +
                ", createdUser=" + createdUser +
                ", createdDate='" + createdDate + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
    }
}

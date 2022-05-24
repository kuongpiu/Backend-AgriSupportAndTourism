package com.example.agrisupportandtorism.dto.comment;

import com.example.agrisupportandtorism.dto.UserDTO;
import com.example.agrisupportandtorism.entity.post.Comment;
import com.example.agrisupportandtorism.utils.DateTimeUtils;
import com.example.agrisupportandtorism.utils.UrlUtils;
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

    @NotNull(message = "Mã bình luận là thông tin bắt buộc")
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
        commentDTO.setImageUrls(UrlUtils.convertStringToUrlList(comment.getRawStringImageUrl()));
        commentDTO.setCreatedUser(UserDTO.fromUser(comment.getCreatedUser()));
        commentDTO.setPostId(comment.getPostId());

        LocalDateTime createdDateTime = comment.getCreatedDateTime();
        commentDTO.setCreatedDate(createdDateTime.format(DateTimeUtils.DATE_FORMATTER));
        commentDTO.setCreatedTime(createdDateTime.format(DateTimeUtils.TIME_FORMATTER));

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

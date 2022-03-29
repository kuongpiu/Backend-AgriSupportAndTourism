package com.example.agrisupportandtorism.dto;

import com.example.agrisupportandtorism.entity.Post;
import com.example.agrisupportandtorism.utils.DateTimeUtil;
import com.example.agrisupportandtorism.utils.UrlUntil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter
@Getter

public class PostDTO {
    private Integer id;

    @NotEmpty(message = "post title can not be empty !")
    private String title;

    @NotEmpty(message = "address can not be empty !")
    private String address;

    @NotEmpty(message = "body can not be empty !")
    private String body;

    private List<String> imageUrls;

    private UserDTO createdUser;

    private String updatedDate;
    private String updatedTime;

    private PostDTO() {
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", body='" + body + '\'' +
                ", imageUrls=" + imageUrls +
                ", createdUser=" + createdUser +
                ", updatedDate=" + updatedDate +
                ", updatedTime=" + updatedTime +
                '}';
    }

    public static PostDTO fromEntity(Post post) {
        PostDTO postDTO = new PostDTO();

        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setAddress(post.getAddress());
        postDTO.setBody(post.getBody());
        postDTO.setImageUrls(UrlUntil.convertStringToUrlList(post.getRawStringImageUrl()));
        postDTO.setCreatedUser(UserDTO.fromUser(post.getCreatedUser()));

        LocalDateTime updatedDateTime = post.getUpdatedDateTime();
        postDTO.updatedDate = updatedDateTime.format(DateTimeUtil.DATE_FORMATTER);
        postDTO.updatedTime = updatedDateTime.format(DateTimeUtil.TIME_FORMATTER);

        return postDTO;
    }
}

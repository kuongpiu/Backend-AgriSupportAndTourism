package com.example.agrisupportandtorism.dto;

import com.example.agrisupportandtorism.entity.Post;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    public final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public final static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a");

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
        postDTO.imageUrls = Post.getImageUrls(post.getImageUrls());
        postDTO.createdUser = UserDTO.fromUser(post.getCreatedUser());

        LocalDateTime updatedDateTime = post.getUpdatedDateTime();
        postDTO.updatedDate = updatedDateTime.format(PostDTO.dateFormatter);
        postDTO.updatedTime = updatedDateTime.format(PostDTO.timeFormatter);
        return postDTO;
    }
}

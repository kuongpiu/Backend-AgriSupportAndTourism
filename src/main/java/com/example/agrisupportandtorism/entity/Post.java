package com.example.agrisupportandtorism.entity;

import com.example.agrisupportandtorism.dto.PostDTO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Table(name = "post")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Post {
    private static String URL_SPLITTER = ",";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "created_user", referencedColumnName = "username")
    private User createdUser;

    @Column(name = "updated_date_time")
    private LocalDateTime updatedDateTime;

    @Column(name = "title")
    private String title;

    @Column(name = "address")
    private String address;

    @Column(name = "body")
    private String body;

    @Column(name = "urls")
    private String imageUrls;

    public static List<String> getImageUrls(String urls){
        return Arrays.asList(urls.split(Post.URL_SPLITTER));
    }

    public static Post fromDTO(PostDTO postDTO){
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setAddress(postDTO.getAddress());
        post.setBody(postDTO.getBody());
        post.setImageUrls(String.join(Post.URL_SPLITTER, postDTO.getImageUrls()));
        post.setCreatedUser(User.fromUserDTO(postDTO.getCreatedUser()));
        return post;
    }
}

package com.example.agrisupportandtorism.entity;

import com.example.agrisupportandtorism.dto.PostDTO;
import com.example.agrisupportandtorism.utils.UrlUntil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "post")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Post {
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
    private String rawStringImageUrl;

    public static Post fromDTO(PostDTO postDTO){
        Post post = new Post();

        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setAddress(postDTO.getAddress());
        post.setBody(postDTO.getBody());
        post.setRawStringImageUrl(UrlUntil.convertUrlListToString(postDTO.getImageUrls()));
        post.setCreatedUser(User.fromUserDTO(postDTO.getCreatedUser()));

        return post;
    }
}

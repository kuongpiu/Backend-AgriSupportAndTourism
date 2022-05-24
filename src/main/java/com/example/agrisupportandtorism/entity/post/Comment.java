package com.example.agrisupportandtorism.entity.post;

import com.example.agrisupportandtorism.dto.comment.CommentDTO;
import com.example.agrisupportandtorism.entity.user.User;
import com.example.agrisupportandtorism.utils.UrlUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "comment")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Setter
@Getter

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "body")
    private String body;

    @Column(name = "urls")
    private String rawStringImageUrl;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @ManyToOne
    @JoinColumn(name = "created_user", referencedColumnName = "username")
    private User createdUser;

    @Column(name = "post_id")
    private Integer postId;

    public static Comment fromDTO(CommentDTO commentDTO){
        Comment comment = new Comment();

        comment.setId(commentDTO.getId());
        comment.setBody(commentDTO.getBody());
        comment.setRawStringImageUrl(UrlUtils.convertUrlListToString(commentDTO.getImageUrls()));
        comment.setCreatedUser(User.fromUserDTO(commentDTO.getCreatedUser()));
        comment.setPostId(commentDTO.getPostId());

        return comment;
    }
}

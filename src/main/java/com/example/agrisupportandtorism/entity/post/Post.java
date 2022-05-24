package com.example.agrisupportandtorism.entity.post;

import com.example.agrisupportandtorism.dto.post.PostDTO;
import com.example.agrisupportandtorism.entity.address.District;
import com.example.agrisupportandtorism.entity.address.Province;
import com.example.agrisupportandtorism.entity.address.Ward;
import com.example.agrisupportandtorism.entity.user.User;
import com.example.agrisupportandtorism.utils.UrlUtils;
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

    @Column(name = "detail_address")
    private String detailAddress;

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "ward_id", referencedColumnName = "id")
    private Ward ward;

    @Column(name = "body")
    private String body;

    @Column(name = "urls")
    private String rawStringImageUrl;

    @Column(name = "farm_id")
    private Integer farmId;

    public static Post fromDTO(PostDTO postDTO) {
        Post post = new Post();

        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setFarmId(postDTO.getFarmId());

        post.setDetailAddress(postDTO.getDetailAddress());
        post.setProvince(postDTO.getProvince());
        post.setDistrict(postDTO.getDistrict());
        post.setWard(postDTO.getWard());

        post.setBody(postDTO.getBody());
        post.setRawStringImageUrl(UrlUtils.convertUrlListToString(postDTO.getImageUrls()));
        post.setCreatedUser(User.fromUserDTO(postDTO.getCreatedUser()));

        return post;
    }
}

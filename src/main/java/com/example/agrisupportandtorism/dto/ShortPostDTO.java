package com.example.agrisupportandtorism.dto;

import com.example.agrisupportandtorism.entity.post.Post;
import com.example.agrisupportandtorism.utils.DateTimeUtil;
import com.example.agrisupportandtorism.utils.UrlUntil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Setter
@Getter

public class ShortPostDTO {
    private Integer id;
    private String title;
    private String address;
    private String avatar;
    private String createdName;
    private String postImage;
    private String createdDate;
    private String createdTime;

    private ShortPostDTO() {
    }

    public static ShortPostDTO fromEntity(Post post) {
        ShortPostDTO sPostDTO = new ShortPostDTO();
        sPostDTO.setId(post.getId());
        sPostDTO.setTitle(post.getTitle());
        sPostDTO.setCreatedName(post.getCreatedUser().getName());
        sPostDTO.setAddress(post.getAddress());
        String avatar = post.getCreatedUser().getAvatar();

        LocalDateTime createdDateTime = post.getUpdatedDateTime();
        sPostDTO.setCreatedDate(createdDateTime.format(DateTimeUtil.DATE_FORMATTER));
        sPostDTO.setCreatedTime(createdDateTime.format(DateTimeUtil.TIME_FORMATTER));

        sPostDTO.setAvatar(Objects.requireNonNullElse(avatar, ""));

        List<String> imageUrls = UrlUntil.convertStringToUrlList(post.getRawStringImageUrl());
        if (imageUrls.size() > 0) {
            sPostDTO.setPostImage(imageUrls.get(0));
        } else {
            sPostDTO.setPostImage("");
        }
        return sPostDTO;
    }
}

package com.example.agrisupportandtorism.dto.post;

import com.example.agrisupportandtorism.entity.post.Post;
import com.example.agrisupportandtorism.utils.DateTimeUtils;
import com.example.agrisupportandtorism.utils.UrlUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Setter
@Getter

public class ShortPostDTO {
    private Integer id;
    private String title;

    private String detailAddress;
    private String province, district, ward;

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

        sPostDTO.setDetailAddress(post.getDetailAddress());
        sPostDTO.setProvince(post.getProvince().getName());
        sPostDTO.setDistrict(post.getDistrict().getName());
        sPostDTO.setWard(post.getWard().getName());

        String avatar = post.getCreatedUser().getAvatar();

        LocalDateTime createdDateTime = post.getUpdatedDateTime();
        sPostDTO.setCreatedDate(createdDateTime.format(DateTimeUtils.DATE_FORMATTER));
        sPostDTO.setCreatedTime(createdDateTime.format(DateTimeUtils.TIME_FORMATTER));

        sPostDTO.setAvatar(Objects.requireNonNullElse(avatar, ""));

        List<String> imageUrls = UrlUtils.convertStringToUrlList(post.getRawStringImageUrl());
        if (imageUrls.size() > 0) {
            sPostDTO.setPostImage(imageUrls.get(0));
        } else {
            sPostDTO.setPostImage("");
        }
        return sPostDTO;
    }
}

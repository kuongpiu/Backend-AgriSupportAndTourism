package com.example.agrisupportandtorism.dto;

import com.example.agrisupportandtorism.entity.Post;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter

public class ShortPostDTO {
    private Integer id;
    private String title;
    private String address;
    private String imageUrl;

    private ShortPostDTO() {
    }

    public static ShortPostDTO fromEntity(Post post) {
        ShortPostDTO sPostDTO = new ShortPostDTO();
        sPostDTO.setId(post.getId());
        sPostDTO.setTitle(post.getTitle());
        sPostDTO.setAddress(post.getAddress());
        sPostDTO.setImageUrl(Post.getImageUrls(post.getImageUrls()).get(0));
        return sPostDTO;
    }
}

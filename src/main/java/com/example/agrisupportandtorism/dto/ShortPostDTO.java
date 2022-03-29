package com.example.agrisupportandtorism.dto;

import com.example.agrisupportandtorism.entity.Post;
import com.example.agrisupportandtorism.utils.UrlUntil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

        List<String> imageUrls = UrlUntil.convertStringToUrlList(post.getRawStringImageUrl());
        if(imageUrls.size() > 0){
            sPostDTO.setImageUrl(imageUrls.get(0));
        }else{
            sPostDTO.setImageUrl("");
        }
        return sPostDTO;
    }
}

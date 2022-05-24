package com.example.agrisupportandtorism.dto.product;

import com.example.agrisupportandtorism.entity.product.Product;
import com.example.agrisupportandtorism.utils.UrlUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ShortProductDTO {
    private Integer id;
    private String name;
    private String mainImage;
    private Integer price;
    private String province;

    public static ShortProductDTO fromEntity(Product product) {
        ShortProductDTO sProductDTO = new ShortProductDTO();
        sProductDTO.setId(product.getId());
        sProductDTO.setName(product.getName());
        sProductDTO.setMainImage(UrlUtils.convertStringToUrlList(product.getRawStringImageUrl()).get(0));
        sProductDTO.setPrice(product.getPrice());
        sProductDTO.setProvince(product.getProvince().getName());

        return sProductDTO;
    }
}

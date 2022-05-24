package com.example.agrisupportandtorism.dto.product;

import com.example.agrisupportandtorism.entity.address.Province;
import com.example.agrisupportandtorism.entity.product.Product;
import com.example.agrisupportandtorism.utils.UrlUtils;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor

public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private String status;
    private Integer farmId;
    private Integer price;
    private List<String> imageUrls;
    private Province province;

    public static ProductDTO fromEntity(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setStatus(product.getStatus());
        productDTO.setFarmId(product.getFarmId());
        productDTO.setPrice(product.getPrice());
        productDTO.setImageUrls(UrlUtils.convertStringToUrlList(product.getRawStringImageUrl()));
        productDTO.setProvince(product.getProvince());

        return productDTO;
    }
}

package com.example.agrisupportandtorism.entity.product;

import com.example.agrisupportandtorism.dto.product.ProductDTO;
import com.example.agrisupportandtorism.entity.address.Province;
import com.example.agrisupportandtorism.utils.UrlUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Tên sản phẩm không thể trống")
    @Size(max = 100, message = "Độ dài tên sản phẩm phải nhỏ hơn 100")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Size(max = 100, message = "Độ dài trạng thái sản phẩm phải nhỏ hơn 100")
    private String status;

    @Column(name = "farm_id")
    @NotNull(message = "Mã vườn không thể thiếu")
    private Integer farmId;

    @Column(name = "price")
    private Integer price;

    @Column(name = "urls")
    private String rawStringImageUrl;

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    private Province province;

    public static Product fromDTO(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setStatus(productDTO.getStatus());
        product.setFarmId(productDTO.getFarmId());
        product.setPrice(productDTO.getPrice());
        product.setRawStringImageUrl(UrlUtils.convertUrlListToString(productDTO.getImageUrls()));
        product.setProvince(productDTO.getProvince());
        return product;
    }
}

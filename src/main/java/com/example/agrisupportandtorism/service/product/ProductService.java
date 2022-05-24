package com.example.agrisupportandtorism.service.product;

import com.example.agrisupportandtorism.dto.post.ShortPostDTO;
import com.example.agrisupportandtorism.dto.product.ProductDTO;
import com.example.agrisupportandtorism.dto.product.ShortProductDTO;
import com.example.agrisupportandtorism.entity.product.Product;
import com.example.agrisupportandtorism.exception.PermissionException;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.product.ProductRepo;
import com.example.agrisupportandtorism.service.farm.FarmService;
import com.example.agrisupportandtorism.service.user.UserService;
import com.example.agrisupportandtorism.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private FarmService farmService;

    public Page<ShortProductDTO> getAllShortProductsByFarmId(Integer farmId, Pageable pageable) {
        Page<Product> productPage = productRepo.getAllByFarmId(farmId, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        List<ShortProductDTO> content = productPage.getContent().stream().map(ShortProductDTO::fromEntity).collect(Collectors.toList());

        return new PageImpl<>(content, productPage.getPageable(), productPage.getTotalElements());
    }

    public List<ShortProductDTO> getAllShortProductsByFarmId(Integer farmId) {
        return productRepo.getAllByFarmId(farmId).stream().map(ShortProductDTO::fromEntity).collect(Collectors.toList());
    }

    public List<Product> getAllByFarmId(Integer farmId) {
        return productRepo.getAllByFarmId(farmId);
    }

    public ProductDTO findProductDTOById(Integer productId) {
        return ProductDTO.fromEntity(findById(productId));
    }

    public Product findById(Integer productId) {
        Optional<Product> productOpt = productRepo.findById(productId);
        if (productOpt.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Sản phẩm với mã=[%s] không tồn tại trong hệ thống", productId));
        }
        return productOpt.get();
    }

    @Transactional
    public ProductDTO update(ProductDTO productDTO) {
        Integer farmId = productDTO.getFarmId();
        if (!farmService.isFarmOwner(farmId)) {
            throw new PermissionException("Người dùng hiện tại không có quyền sửa sản phẩm !");
        }

        Product productInRepo = findById(productDTO.getId());
        if (!Objects.equals(productInRepo.getFarmId(), productDTO.getFarmId())) {
            throw new PermissionException("Người dùng hiện tại không có quyền sửa sản phẩm !");
        }

        productInRepo.setName(productDTO.getName());
        productInRepo.setDescription(productDTO.getDescription());
        productInRepo.setStatus(productDTO.getStatus());
        productInRepo.setPrice(productDTO.getPrice());
        productInRepo.setProvince(productDTO.getProvince());
        productInRepo.setRawStringImageUrl(UrlUtils.convertUrlListToString(productDTO.getImageUrls()));

        return ProductDTO.fromEntity(productRepo.save(productInRepo));
    }

    public ProductDTO insert(ProductDTO productDTO) {
        return ProductDTO.fromEntity(insert(Product.fromDTO(productDTO)));
    }

    @Transactional
    public Product insert(Product product) {
        Integer farmId = product.getFarmId();
        if (farmService.isFarmOwner(farmId)) {
            return productRepo.save(product);
        } else {
            throw new PermissionException("Người dùng hiện tại không có quyền tạo sản phẩm mới !");
        }
    }

    public void delete(Integer productId) {
        Product productInRepo = findById(productId);

        Integer farmId = productInRepo.getFarmId();
        if (!farmService.isFarmOwner(farmId)) {
            throw new PermissionException("Người dùng hiện tại không có quyền !");
        }

        delete(productInRepo);
    }
    @Transactional
    public void delete(Product product){
        productRepo.delete(product);
    }
}

package com.example.agrisupportandtorism.controller.product;

import com.example.agrisupportandtorism.dto.product.ProductDTO;
import com.example.agrisupportandtorism.dto.product.ShortProductDTO;
import com.example.agrisupportandtorism.entity.product.Product;
import com.example.agrisupportandtorism.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:9528/")
@RequestMapping("/product")

public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all-in-page")
    public Page<ShortProductDTO> getAllShortProductsByFarmId(@RequestParam Integer farmId, Pageable pageable) {
        return productService.getAllShortProductsByFarmId(farmId, pageable);
    }

    @GetMapping("/all")
    public List<ShortProductDTO> getAllShortProducts(@RequestParam Integer farmId){
        return productService.getAllShortProductsByFarmId(farmId);
    }

    @GetMapping
    public ProductDTO findProductById(@RequestParam Integer productId) {
        return productService.findProductDTOById(productId);
    }

    @PutMapping
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) {
        return productService.update(productDTO);
    }

    @PostMapping
    public ProductDTO insertProduct(@RequestBody ProductDTO productDTO) {
        return productService.insert(productDTO);
    }

    @DeleteMapping
    public void deleteProduct(@RequestParam Integer productId){
        productService.delete(productId);
    }
}

package com.example.agrisupportandtorism.controller.product;

import com.example.agrisupportandtorism.dto.product.CartItemDTO;
import com.example.agrisupportandtorism.entity.product.CartItem;
import com.example.agrisupportandtorism.entity.product.CartItemId;
import com.example.agrisupportandtorism.service.product.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin("http://localhost:9528/")

public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/all")
    public List<CartItemDTO> findAll() {
        return cartService.findAll();
    }

    @GetMapping
    public CartItem findById(CartItemId cartItemId) {
        return cartService.findById(cartItemId);
    }

    @PutMapping
    public CartItemDTO update(@RequestBody @Valid CartItem cartItem) {
        return cartService.updateCartItem(cartItem);
    }

    @PostMapping
    public CartItemDTO insert(@RequestBody @Valid CartItem cartItem){
        return cartService.insertCartItem(cartItem);
    }

    @DeleteMapping
    public void delete(@RequestParam Integer productId){
        cartService.delete(productId);
    }
}

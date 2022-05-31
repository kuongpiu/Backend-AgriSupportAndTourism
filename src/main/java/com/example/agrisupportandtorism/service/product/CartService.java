package com.example.agrisupportandtorism.service.product;

import com.example.agrisupportandtorism.dto.product.CartItemDTO;
import com.example.agrisupportandtorism.entity.product.CartItem;
import com.example.agrisupportandtorism.entity.product.CartItemId;
import com.example.agrisupportandtorism.entity.user.User;
import com.example.agrisupportandtorism.exception.PermissionException;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.product.CartRepo;
import com.example.agrisupportandtorism.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public List<CartItemDTO> findAll() {
        User currentUser = userService.getCurrentUser();

        return cartRepo.findAllByUsername(currentUser.getUsername())
                .stream()
                .map(CartItemDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public CartItem findById(CartItemId cartItemId) {
        Optional<CartItem> cartItemOpt = cartRepo.findById(cartItemId);
        if (cartItemOpt.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Sản phẩm có mã-sản-phẩm=[%s] không tồn tại trong giỏ hàng user=[%s]", cartItemId.getProductId(), cartItemId.getUsername()));
        }
        return cartItemOpt.get();
    }

    private void checkCartItemOwner(CartItem cartItem) {
        User currentUser = userService.getCurrentUser();

        if (!currentUser.getUsername().equals(cartItem.getCartItemId().getUsername())) {
            throw new PermissionException(String.format("User-truy-cập-hiện-tại=[%s] không có quyền insert cho user=[%s]", currentUser.getUsername(), cartItem.getCartItemId().getUsername()));
        }
    }

    public CartItemDTO insertCartItem(CartItem cartItem) {
        checkCartItemOwner(cartItem);

        Optional<CartItem> itemInRepoOpt = cartRepo.findById(cartItem.getCartItemId());
        itemInRepoOpt.ifPresent(item -> cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity()));

        return update(cartItem, true);
    }

    public CartItemDTO updateCartItem(CartItem cartItem) {
        return update(cartItem, false);
    }

    @Transactional
    public CartItemDTO update(CartItem cartItem, boolean isOwnerChecked) {
        if (!isOwnerChecked) checkCartItemOwner(cartItem);

        CartItem updatedCartItem = cartRepo.saveAndFlush(cartItem);
        if (Objects.isNull(updatedCartItem.getProduct())) {
            updatedCartItem.setProduct(productService.findById(cartItem.getCartItemId().getProductId()));
        }
        return CartItemDTO.fromEntity(updatedCartItem);
    }

    @Transactional
    public void deleteAll(List<Integer> productIds) {
        for (Integer productId : productIds) {
            delete(productId);
        }
    }

    @Transactional
    public void delete(Integer productId) {
        User currentUser = userService.getCurrentUser();
        CartItemId cartItemId = new CartItemId(currentUser.getUsername(), productId);

        CartItem cartItem = findById(cartItemId);
        checkCartItemOwner(cartItem);

        cartRepo.deleteById(cartItemId);
    }
}

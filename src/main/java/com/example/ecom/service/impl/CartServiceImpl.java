package com.example.ecom.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.ecom.model.Cart;
import com.example.ecom.model.Product;
import com.example.ecom.model.User;
import com.example.ecom.repository.CartRepository;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.repository.UserRepository;
import com.example.ecom.service.CartService;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart saveCart(Integer productId, Integer userId) {
		User user = userRepository.findById(userId).orElse(null);
		Product product = productRepository.findById(productId).orElse(null);

		if (user == null || product == null) {
			return null;
		}

		Cart cartStatus = cartRepository.findByProductIdAndUserId(productId, userId);

		Cart cart = null;

		if (ObjectUtils.isEmpty(cartStatus)) {
			cart = new Cart();
			cart.setProduct(product);
			cart.setUser(user);
			cart.setQuantity(1);
			Double price = product.getDiscountPrice();
			if (price == null) {
				price = product.getPrice();
			}
			cart.setTotalPrice(1 * price);
		} else {
			cart = cartStatus;
			cart.setQuantity(cart.getQuantity() + 1);

			Double sellingPrice = product.getDiscountPrice();

			if (sellingPrice == null) {
				sellingPrice = product.getPrice();
			}

			cart.setTotalPrice(cart.getQuantity() * sellingPrice);		}
			Cart saveCart = cartRepository.save(cart);

		return saveCart;
    }

	@Override
	public List<Cart> getCartsByUser(Integer userId) {
		List<Cart> carts = cartRepository.findByUserId(userId);

		Double totalOrderPrice = 0.0;
		List<Cart> updateCarts = new ArrayList<>();

		for (Cart c : carts) {
			// 1. Kiểm tra xem Product có tồn tại không (phòng hờ dữ liệu lỗi)
			if (c.getProduct() == null) {
				continue; // Bỏ qua nếu giỏ hàng lỗi không có sản phẩm
			}

			// 2. Lấy giá tiền an toàn
			Double price = c.getProduct().getDiscountPrice();

			// Nếu giá giảm chưa nhập (null), thì lấy giá gốc
			if (price == null) {
				price = c.getProduct().getPrice();
			}

			// Phòng hờ cả giá gốc cũng null (dù hiếm) -> gán bằng 0
			if (price == null) {
				price = 0.0;
			}

			// 3. Tính toán (lúc này price chắc chắn là số, không null)
			Double totalPrice = price * c.getQuantity();

			c.setTotalPrice(totalPrice);

			totalOrderPrice = totalOrderPrice + totalPrice;
			c.setTotalOrderPrice(totalOrderPrice);

			updateCarts.add(c);
		}
		return updateCarts;
	}

    @Override
    public Integer getCountCart(Integer userId) {
        Integer countByUserId = cartRepository.countByUserId(userId);
		return countByUserId;

    }

    @Override
    public void updateQuantity(String sy, Integer cid) {
       
		Cart cart = cartRepository.findById(cid).get();
		int updateQuantity;

		if (sy.equalsIgnoreCase("de")) {
			updateQuantity = cart.getQuantity() - 1;

			if (updateQuantity <= 0) {
				cartRepository.delete(cart);
			} else {
				cart.setQuantity(updateQuantity);
				cartRepository.save(cart);
			}

		} else {
			updateQuantity = cart.getQuantity() + 1;
			cart.setQuantity(updateQuantity);
			cartRepository.save(cart);
		}
    }

}

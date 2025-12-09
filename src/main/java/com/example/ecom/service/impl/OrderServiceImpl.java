package com.example.ecom.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.example.ecom.model.Cart;
import com.example.ecom.model.OrderAddress;
import com.example.ecom.model.OrderRequest;
import com.example.ecom.model.ProductOrder;
import com.example.ecom.repository.CartRepository;
import com.example.ecom.repository.ProductOrderRepository;
import com.example.ecom.service.OrderService;
import com.example.ecom.utils.OrderStatus;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductOrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void saveOrder(Integer userid, OrderRequest orderRequest) {

        List<Cart> carts = cartRepository.findByUserId(userid);

        for (Cart cart : carts) {

            ProductOrder order = new ProductOrder();
            
            order.setOrderId(UUID.randomUUID().toString());
            order.setOrderDate(LocalDate.now());
            
            order.setProduct(cart.getProduct());
            order.setPrice(cart.getProduct().getDiscountPrice());

            order.setQuantity(cart.getQuantity());
            order.setUser(cart.getUser());

            order.setStatus(OrderStatus.IN_PROGRESS.name());
            order.setPaymentType(orderRequest.getPaymentType());

            OrderAddress address = new OrderAddress();
            address.setFirstName(orderRequest.getFirstName());
            address.setLastName(orderRequest.getLastName());
            address.setEmail(orderRequest.getEmail());
            address.setMobileNo(orderRequest.getMobileNo());
            address.setAddress(orderRequest.getAddress());
            address.setCity(orderRequest.getCity());
            address.setState(orderRequest.getState());
            address.setPincode(orderRequest.getPincode());

            order.setOrderAddress(address);

            orderRepository.save(order);
        }
    }

    @Override
    public List<ProductOrder> getOrdersByUser(Integer userId) {
        List<ProductOrder> orders = orderRepository.findByUserId(userId);
		return orders;
    }

    @Override
    public Boolean updateOrderStatus(Integer id, String status) {
        Optional<ProductOrder> findById = orderRepository.findById(id);
		if (findById.isPresent()) {
			ProductOrder productOrder = findById.get();
			productOrder.setStatus(status);
			orderRepository.save(productOrder);
			return true;
		}
		return false;
    }

    @Override
	public List<ProductOrder> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public Page<ProductOrder> getAllOrdersPagination(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return orderRepository.findAll(pageable);

	}

	@Override
	public ProductOrder getOrdersByOrderId(String orderId) {
		return orderRepository.findByOrderId(orderId);
	}

}

package com.example.ecom.service;

import java.util.List;

import com.example.ecom.model.OrderRequest;
import com.example.ecom.model.ProductOrder;

public interface OrderService {

    public void saveOrder(Integer userid,OrderRequest orderRequest);
	
	public List<ProductOrder> getOrdersByUser(Integer userId);
	
	public Boolean updateOrderStatus(Integer id,String status);
}
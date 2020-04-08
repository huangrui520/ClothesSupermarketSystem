package com.system.services;

import com.system.bean.Clothes;
import com.system.bean.Order;
import com.system.bean.OrderItem;
import com.system.utils.BusinessExpection;

import java.util.List;

public interface OrderService {
    public void buyProducts(Order order) throws BusinessExpection;
    public List<Order> list() throws BusinessExpection;
    public Order findById(int orderId) throws BusinessExpection;
    public void update(int orderId) throws BusinessExpection;
}

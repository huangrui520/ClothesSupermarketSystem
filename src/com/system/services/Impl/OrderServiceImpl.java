package com.system.services.Impl;

import com.system.bean.Order;
import com.system.bean.OrderItem;
import com.system.services.OrderService;
import com.system.utils.BusinessExpection;
import com.system.utils.OrderIO;
import com.system.utils.OrderStatusType;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderIO orderIO=new OrderIO();
    List<OrderItem> orderItemList=new ArrayList<>();

    @Override
    public void buyProducts(Order order) throws BusinessExpection {
        orderIO.add(order);

    }

    @Override
    public List<Order> list() throws BusinessExpection {
        return orderIO.list();
    }

    @Override
    public Order findById(int orderId) throws BusinessExpection {
        return orderIO.findByOrderId(orderId);
    }

    @Override
    public void update(int orderId) throws BusinessExpection {
        List<Order> orders=list();
        for(Order o:orders){
            if (o.getOrderId()==orderId){
                o.setStatus(OrderStatusType.PAID);
                break;
            }
        }
        orderIO.writeOrders(orders);
    }
}

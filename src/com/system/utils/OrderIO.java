package com.system.utils;

import com.system.bean.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderIO {
    private static List<Order> orders=new ArrayList<>();
    private static final String ORDER_FILE="orders.obj";

    public void add(Order order) throws BusinessExpection{
        readOrders();
        orders.add(order);
        writeOrders(orders);
    }

    public List<Order> list() throws BusinessExpection{
        readOrders();
        return orders;
    }

    public Order findByOrderId(int orderId) throws BusinessExpection{

        Order order=null;
        int oid;
        for (Order o:orders){
            oid=o.getOrderId();
            if (oid==orderId){
                order=o;
                return order;
            }
        }
        return null;
    }

    public boolean writeOrders(List<Order> orders){
        try {
            ObjectOutputStream out=new ObjectOutputStream(
                    new FileOutputStream(ORDER_FILE));
            out.writeObject(orders);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean readOrders() {
        ObjectInputStream  in=null;
        try {
            File file=new File(ORDER_FILE);
            if (!file.exists()){
                file.createNewFile();
            }
            in=new ObjectInputStream(new FileInputStream(ORDER_FILE));
            orders= (List<Order>) in.readObject();
            in.close();
            return true;
        }catch (EOFException e){
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }


}

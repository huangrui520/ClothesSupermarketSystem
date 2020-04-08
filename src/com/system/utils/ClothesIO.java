package com.system.utils;

import com.system.bean.Clothes;
import com.system.bean.Order;
import com.system.bean.OrderItem;

import java.util.List;

public class ClothesIO {
    private List<Clothes> clothesList= ProductsXmlUtils.parserProductsXml();

    public Clothes findById(String id) throws BusinessExpection{
        for (Clothes c:clothesList){
            if (id.equals(c.getId())){
                return c;
            }
        }
        return null;
    }

    public List<Clothes> list(){
        return clothesList;
    }

    public void update(Order order){
        List<OrderItem> orderItemList=order.getOrderItemList();
        for (OrderItem o:orderItemList){
            for (Clothes clothes:clothesList){
                if (o.getClothes().getId().equals(clothes.getId())){
                    clothes.setNum(clothes.getNum()-o.getShoppingNum());
                }
            }
        }
        ProductsXmlUtils.writeProductsToXml(clothesList);
    }


}

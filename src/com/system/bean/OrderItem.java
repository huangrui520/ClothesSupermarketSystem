package com.system.bean;

import com.system.bean.Clothes;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private int itemId;//订单编号
    private Clothes clothes;//商品
    private int shoppingNum;//数量
    private float sum;//总价钱

    public OrderItem() {
    }

    public OrderItem(int itemId, Clothes clothes, int shoppingNum, float sum) {
        this.itemId = itemId;
        this.clothes = clothes;
        this.shoppingNum = shoppingNum;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemId=" + itemId +
                ", clothes=" + clothes +
                ", shoppingNum=" + shoppingNum +
                ", sum=" + sum +
                '}';
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Clothes getClothes() {
        return clothes;
    }

    public void setClothes(Clothes clothes) {
        this.clothes = clothes;
    }

    public int getShoppingNum() {
        return shoppingNum;
    }

    public void setShoppingNum(int shoppingNum) {
        this.shoppingNum = shoppingNum;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}

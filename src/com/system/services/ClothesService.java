package com.system.services;

import com.system.bean.Clothes;
import com.system.bean.Order;
import com.system.utils.BusinessExpection;

import java.util.List;

public interface ClothesService  {
    public List<Clothes> list() throws BusinessExpection;
    public Clothes findById(String id) throws BusinessExpection;
    public void update(Order order) throws BusinessExpection;
}

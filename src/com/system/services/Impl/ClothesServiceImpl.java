package com.system.services.Impl;

import com.system.bean.Clothes;
import com.system.bean.Order;
import com.system.utils.BusinessExpection;
import com.system.utils.ClothesIO;
import com.system.utils.ProductsXmlUtils;

import java.util.List;

public class ClothesServiceImpl implements com.system.services.ClothesService {
    private ClothesIO clothesIO=new ClothesIO();
    @Override
    public List<Clothes> list() throws BusinessExpection {
        List<Clothes> clothesList=clothesIO.list();
        return clothesList;
    }

    @Override
    public Clothes findById(String id) throws BusinessExpection {
        Clothes clothes=clothesIO.findById(id);
        return clothes;
    }

    @Override
    public void update(Order order) throws BusinessExpection {
        clothesIO.update(order);
    }
}

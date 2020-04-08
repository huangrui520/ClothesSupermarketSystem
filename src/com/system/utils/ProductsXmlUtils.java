package com.system.utils;

import com.sun.org.apache.xerces.internal.xni.parser.XMLPullParserConfiguration;
import com.system.bean.Clothes;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsXmlUtils {

    public static List<Clothes> parserProductsXml(){
        List<Clothes> products=new ArrayList<>();
        XStream xStream=new XStream(new Xpp3Driver());   //要导入xstream和xpp3_min包和xmlpull，缺一不可
        //XStream xStream=new XStream(new Dom4JDriver());//或者new别的类
        xStream.alias("list",products.getClass());
        xStream.alias("clothes",Clothes.class);
        xStream.useAttributeFor(Clothes.class,"id");
        try {
            BufferedInputStream inputStream=new BufferedInputStream(
                    new FileInputStream("products.xml"));
            products= (List<Clothes>) xStream.fromXML(inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static void writeProductsToXml(List<Clothes> products){
        XStream xStream=new XStream(new Xpp3Driver());
        xStream.alias("list",products.getClass());
        xStream.alias("clothes",Clothes.class);
        xStream.useAttributeFor(Clothes.class,"id");
        try {
            BufferedOutputStream outputStream=new BufferedOutputStream(
                    new FileOutputStream("products.xml"));
            outputStream.write("<?xml version=\"1.0\" encoding=\"utf-8\" ?>".getBytes());
            xStream.toXML(products,outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

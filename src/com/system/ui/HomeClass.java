package com.system.ui;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.system.bean.Clothes;
import com.system.bean.Order;
import com.system.bean.OrderItem;
import com.system.services.ClothesService;
import com.system.services.Impl.ClothesServiceImpl;
import com.system.services.Impl.OrderServiceImpl;
import com.system.services.Impl.PayServiceImpl;
import com.system.services.OrderService;
import com.system.services.PayService;
import com.system.utils.DateUtils;
import com.system.utils.OrderIO;
import com.system.utils.OrderStatusType;
import com.system.utils.ProductsXmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeClass extends BaseClass{

    private OrderService orderService=new OrderServiceImpl();
    private ClothesService clothesService=new ClothesServiceImpl();

    public void show(){
        boolean flag=true;
        String select=null,s;
        while (flag){
            println(getString("home.function"));
            println(getString("info.select"));
            if (select=="3"||select=="5"){
                s=input.nextLine();
            }
            select=input.nextLine();
            switch (select){
                case "1"://查询全部订单
                    findList();
                    break;
                case "2"://查找订单
                    findOrderById();
                    s=input.nextLine();
                    break;
                case "3"://购买
                    buyProducts();
                    break;
                case "4":
                    showProducts();
                    break;
                case "5":
                    if (showUnpaied()){
                        pay();
                    }
                    break;
                case "6"://退出
                    flag=false;
                    System.exit(0);
                    break;
                default:
                    println(getString("info.error"));
                    break;
            }
        }

    }

    private static boolean showUnpaied(){
        int id=currentUser.getId();
        OrderIO orderIO=new OrderIO();
        List<Order> orderList=orderIO.list();
        for (Order o:orderList){
            if (id==o.getUserId()&&o.getStatus()==OrderStatusType.UNPAID){
                showOrderHead();
                showOrder(o);
                return true;
            }
        }
        println(getString("pay.empty"));
        return false;
    }

    private void pay() {
        println(getString("pay.input"));
        String select=input.nextLine();
        while (!"1".equals(select)&&!"2".equals(select)){
            println(getString("product.input.selectError"));
            println(getString("pay.input"));
            select=input.nextLine();
        }
        switch (select){
            case "1":
                PayService payService=new PayServiceImpl();
                int id=currentUser.getId();
                OrderIO orderIO=new OrderIO();
                List<Order> orderList=orderIO.list();
                List<Order> orders=new ArrayList<>();
                for (Order o:orderList){
                    if (id==o.getUserId()&&o.getStatus()==OrderStatusType.UNPAID){
                        orders.add(o);
                    }
                }
                boolean flag=true;
                Order order=null;
                while(flag){
                    println(getString("find.input"));
                    int orderId= Integer.parseInt((input.nextLine()));
                    order=findById(orderId,orders);
                    if (order!=null){
                        if (payService.payAction(order)){
                            println(getString("pay.success"));
                            orderService.update(order.getOrderId());
                            orders.remove(order);
                        }else {
                            println(getString("pay.error"));
                        }
                    }else {
                        println(getString("pay.iderror"));
                    }
                    println(getString("pay.input"));
                    select=input.nextLine();
                    while (!"1".equals(select)&&!"2".equals(select)){
                        println(getString("product.input.selectError"));
                        println(getString("pay.input"));
                        select=input.nextLine();
                    }
                    if ("2".equals(select)){
                        flag=false;
                    }
                }
                break;
            case "2":
                return;
        }

    }

    private Order findById(int orderId,List<Order> orders) {
        for (Order o:orders){
            if (o.getOrderId()==orderId){
                return o;
            }
        }
        return null;
    }

    private void showProducts(){
        showHead();
        List<Clothes> clothesList=new ClothesServiceImpl().list();
        showProduct(clothesList);
    }

    private void buyProducts() {

        //生成订单
        boolean flag=true;
        int count=0;
        Order order=new Order();
        float sum=0;
        String s;//用来吸收回车
        int select=0;//判断是否继续购买
        while (flag){
            println(getString("product.input.id"));
            if (select==1){
                s=input.nextLine();
            }
            String id=input.nextLine();
            Clothes clothes=null;
            while ((clothes=clothesService.findById(id))==null){
                println(getString("product.input.idError"));
                id=input.nextLine();
            }
            println(getString("product.input.shoppingNum"));
            int shoppingNum=input.nextInt();
            while (shoppingNum>clothesService.findById(id).getNum()){
                println(getString("product.input.shoppingNumError"));
                shoppingNum=input.nextInt();
            }
            //一条订单明细
            OrderItem orderItem=new OrderItem();
            orderItem.setShoppingNum(shoppingNum);
            orderItem.setClothes(clothes);
            orderItem.setSum(shoppingNum*clothes.getPrice());
            orderItem.setItemId(count++);
            order.getOrderItemList().add(orderItem);
            sum=sum+shoppingNum*clothes.getPrice();
            println(getString("product.input.buy"));
            select=input.nextInt();
            while (select!=1&&select!=2){
                println(getString("product.input.selectError"));
                select=input.nextInt();
            }
            if (select==2){
                flag=false;
            }
        }
        s=input.nextLine();
        order.setCreateDate(DateUtils.toDate(new Date()));
        order.setUserId(currentUser.getId());
        order.setSum(sum);
        order.setOrderId(orderService.list().size()+1);
        order.setStatus(OrderStatusType.UNPAID);
        orderService.buyProducts(order);
        clothesService.update(order);
    }

    private static void findOrderById(){
        println(getString("find.input"));
        int id=input.nextInt();
        OrderIO orderIO=new OrderIO();
        List<Order> orderList=orderIO.list();
        for (Order o:orderList){
            if (id==o.getOrderId()){
                showOrderHead();
                showOrder(o);
                return;
            }
        }
        println(getString("find.empty"));
    }

    private static void findList() {
        OrderIO orderIO=new OrderIO();
        List<Order> orderList=orderIO.list();
        if (orderList.size()==0){
            println(getString("find.empty"));
            return;
        }
        showOrderHead();
        for (Order o:orderList){
            showOrder(o);
        }
    }

    private static void showOrder(Order order){
        for(OrderItem orderItem: order.getOrderItemList()){
            System.out.printf("%04d",order.getOrderId());
            System.out.print("  "+order.getCreateDate()+"    ");
            System.out.print(order.getSum()+"   ");
            System.out.print(order.getStatus()+"    ");
            System.out.print(order.getUserId()+"       ");
            System.out.print(orderItem.getClothes().getId()+"     ");
            System.out.print(orderItem.getShoppingNum()+"    ");
            System.out.print(orderItem.getSum()+"    ");
            System.out.println(orderItem.getClothes().getBrand());
        }
    }

    private static void showOrderHead(){
        System.out.print("Id");
        System.out.print("    createDate");
        System.out.print("          orderSum");
        System.out.print("  status");
        System.out.print("  userId");
        System.out.print("  clothesId");
        System.out.print("  Num");
        System.out.print("  Itemsum");
        System.out.println("  brand");
    }


//    public static void showProduct(List<Clothes> clothesList){
//        //不用纠结对不齐，调一下空格可能就可以
//        //在cmd能够对齐，一个中文占两个位置
//        //判断字符是否是中文是判断字节码：char c； if(c>=0x4E00&&c<=0x9FA5) 则c是中文字符
//        for(Clothes c:clothesList){
//            System.out.printf(c.getId()+"  ");
//            System.out.printf(c.getBrand());
//            if (c.getBrand().length()>=4){
//                System.out.print("   ");
//            }
//            if (c.getBrand().length()<=3){
//                System.out.print("    ");
//            }
//            if (c.getBrand().length()<=2){
//                System.out.print("  ");
//            }
//            if (c.getBrand().length()>=3&&c.getBrand().length()<=4&&c.getBrand().getBytes().length<=4){
//                System.out.print("   ");
//            }
//            System.out.printf(c.getStyle()+"    ");
//            if (c.getStyle().length()==2){
//                System.out.print("  ");
//            }
//            System.out.print(c.getColor()+"    ");
//            if (c.getColor().length()==2){
//                System.out.print("  ");
//            }
//            System.out.print(c.getSize()+"     ");
//            if (c.getSize().getBytes().length==1){
//                System.out.print("   ");
//            }
//            System.out.print(c.getNum()+"      ");
//            if (c.getNum()<10){
//                System.out.print(" ");
//            }
//            System.out.print(c.getPrice()+"     ");
//            if (c.getPrice()<100){
//                System.out.print(" ");
//            }
//            System.out.println(c.getDescription());
//        }
//    }
//
//    public static void showHead(){
//        System.out.print("id     ");
//        System.out.print("brand     ");
//        System.out.print("style     ");
//        System.out.print("color     ");
//        System.out.print("size     ");
//        System.out.print("num     ");
//        System.out.print("price     ");
//        System.out.println("description");
//    }
//在控制台打印比较好
    public static void showProduct(List<Clothes> clothesList){
        //不用纠结对不齐，调一下\t或者空格可能就可以了
        for(Clothes c:clothesList){
            System.out.printf(c.getId()+"\t\t");
            System.out.printf(c.getBrand()+"\t\t\t");
            if (c.getBrand().getBytes().length<=3){
                System.out.print("    ");
            }
            System.out.printf(c.getStyle()+"\t\t\t");
            System.out.print(c.getColor()+"\t\t\t");
            System.out.print(c.getSize()+"\t\t\t");
            if (c.getSize().getBytes().length==1){
                System.out.print("\t");
            }
            System.out.print(c.getNum()+"\t\t\t\t");
            System.out.print(c.getPrice()+"\t\t\t");
            System.out.println(c.getDescription());
        }
    }

    public static void showHead(){
        System.out.print("id\t\t\t");
        System.out.print("brand\t\t\t");
        System.out.print("style\t\t\t");
        System.out.print("color\t\t\t");
        System.out.print("size\t\t\t");
        System.out.print("num\t\t\t\t");
        System.out.print("price\t\t\t");
        System.out.println("description\t\t\t");
    }
}

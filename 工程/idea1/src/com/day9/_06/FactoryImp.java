package com.day9._06;

import java.util.ArrayList;
import java.util.Random;

public class FactoryImp implements FactoryFactory {
    @Override
    public void describe(Hat hat){
        if(hat.getColor()%2==1){
            System.out.println("颜色为红色，价格为："+hat.getPrice()+"的"+hat.getType());
        }else {
            System.out.println("颜色为黄色，价格为："+hat.getPrice()+"的"+hat.getType());
        }
    }
    @Override
    public ArrayList<Hat> piliang(int num){
        ArrayList<Hat> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Hat hat = new Hat();
            hat.setType("太阳帽");
            hat.setColor(new Random().nextInt(81)+20);
            hat.setPrice(new Random().nextInt(81)+20);
            list.add(hat);
        }
        return list;
    }
}

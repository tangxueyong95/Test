package sortingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class iteration {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list,"3","4","5");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if(next.equals("4")){
                list.remove(next);
            }
        }
//        for (String s : list) {
//            if(s.equals("5")){
//                list.remove(s);
//            }
//        }
        System.out.println(list);
    }
}
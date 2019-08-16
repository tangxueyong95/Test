package com.扑克牌;

import java.util.ArrayList;
import java.util.Collections;

public class cardText {
    public static void main(String[] args) {
        ArrayList<card> list = new ArrayList<>();
        ArrayList<String> colors = new ArrayList<>();
        ArrayList<String> numbers = new ArrayList<String>();
        Collections.addAll(colors, "♥", "♦", "♠", "♣");
        addNumber(numbers);
        addCards(list, colors, numbers);
        printList(list);
        System.out.println();
    }

    public static void addNumber(ArrayList<String> numbers) {
        for (int i = 2; i <= 10; i++) {
            numbers.add(String.valueOf(i));
        }
        Collections.addAll(numbers, "J", "Q", "K", "A");
    }

    public static void addCards(ArrayList<card> list, ArrayList<String> colors, ArrayList<String> numbers) {
        for (String color : colors) {
            for (String number : numbers) {
                card cards = new card();
                cards.setColor(color);
                cards.setId(number);
                list.add(cards);
            }
        }
        list.add(new card("小王", ""));
        list.add(new card("大王", ""));
    }

    public static void printList(ArrayList<card> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + "\t");
            if (i != list.size() - 1) {
                if (!list.get(i).getColor().equalsIgnoreCase(list.get(i + 1).getColor())) {
                    System.out.println();
                }
            }
        }
    }
}

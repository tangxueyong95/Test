package com.day_07.work_08;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService s = Executors.newFixedThreadPool(2);
        MyRunnable myRunnable = new MyRunnable();
        s.submit(myRunnable);
        s.submit(myRunnable);
    }
}

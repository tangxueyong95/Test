package com.day_06;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Work_09 {
    public static void main(String[] args) {

        A a = new A();
        new Thread(a).start();
        new Thread(a).start();
        new Thread(a).start();
        new Thread(a).start();
    }
}

class A implements Runnable {

    private static int cont = 0;
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (cont < 10) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cont++;
                    System.out.println(Thread.currentThread().getName() + "--" + cont);
                }
            }

            //coll();

            /*lock.lock();
            if (cont < 10) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cont++;
                System.out.println(Thread.currentThread().getName() + "--" + cont);
            }
            lock.unlock();*/
            if (cont >= 10) {
                break;
            }
        }
    }

   /* public static synchronized void coll() {

       if (cont < 10) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cont++;
            System.out.println(Thread.currentThread().getName() + "--" + cont);


        }
    }*/
}
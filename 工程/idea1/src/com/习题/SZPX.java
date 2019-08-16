package com.习题;

public class SZPX {
    public static void main(String[] args) {
        int a[] = {6, 2, 9, 15, 1, 5, 20, 7, 18};
        int i, j;
        for (i = 0; i < a.length; i++) {
            int t;
            for (j = 0; j < a.length - 1; j++) {
                if (a[j] > a[j + 1]) {
                    t = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = t;
                }
            }
            /*for(j=i+1;j<a.length;j++){
                if(a[i]>a[j]){
                    t=a[i];
                    a[i]=a[j];
                    a[j]=t;
                }
            }
            System.out.print(a[i]+"\t");*/  //比较排序法
        }
        for (i = 0; i < a.length; i++) {
            System.out.print(a[i] + "\t");    //冒泡排序法
        }
    }
}

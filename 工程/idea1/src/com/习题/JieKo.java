package com.习题;

public class JieKo {
    public static void main(String[] args) {
        XS a = new XS();
        a.setname("哈哈");
        a.setage(20);
    }
}

interface A {
    /*public void b();
    public abstract void C();     抽象方法*/
   /* public default void d() {      //用default修饰方法,供子类调用和重写

    }*/
    /*public static void e(){

    }                                //供接口直接调用*/
    public abstract void CY();

    public default void HJ() {

    }
}

class Ren {
    private String name;
    private int ige;

    public Ren() {

    }

    public Ren(String name, int ige) {

    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getige() {
        return name;
    }

    public void setage(int age) {
        this.name = name;
    }

    public void eat() {

    }

    public void sleep() {

    }
}

class LS extends Ren implements A {
    public LS() {

    }

    public LS(String name, int age) {
        super();
    }

    public void JK() {

    }

    @Override
    public void CY() {

    }
}

class XS extends Ren implements A {
    public XS() {

    }

    public XS(String name, int age) {
        super();
    }

    public void XX() {

    }

    @Override
    public void CY() {

    }

    @Override
    public void HJ() {

    }
}
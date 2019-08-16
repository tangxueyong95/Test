package com.day9._08;

public class MobileGame extends Game {
    public MobileGame() {
    }

    public MobileGame(String type, String name) {
        super(type, name);
    }

    private void efforts() {
        System.out.println("晚上不睡觉玩" + getName());
    }

    private void damage() {
        System.out.println("导致白天上课困，毕业后找不到工作");
    }

    @Override
    public void play() {
        System.out.println("名称为" + getName() + "的" + getType() + "类游戏非常流行");
    }

    public void prepare() {
        efforts();
        damage();
    }
}

package com.boot.bootdemo;

import cn.hutool.core.util.RandomUtil;

import java.math.RoundingMode;

public class RandomTest {

    public static void main(String[] args) {
        String str = RandomUtil.randomString(6);
        System.out.println(str);

        double price = RandomUtil.randomDouble(4000,100000,2, RoundingMode.HALF_UP);
        System.out.println(price);

    }
}

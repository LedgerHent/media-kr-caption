package com.kr.caption.java8.movingparam;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FilterSomething {


    //泛型  函数式接口：只有一个抽象方法，所以说肯定就知道了 你要实现的是哪个
    private static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T e : list) {
            if (predicate.test(e)) {
                result.add(e);
            }
        }

        return result;
    }


    public static <T> void forEach(List<T> list, Consumer<T> c){
        for(T single:list){
            c.accept(single);
        }
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> function){
        List<R> result = new ArrayList<>();
        for(T single:list){
            result.add(function.apply(single));
        }
        return result;
    }



    public static void main(String[] args) {

        List<Apple> list = new ArrayList<>();

        List<String> strList = new ArrayList<>();


        //行为参数化 ？？ 类似于策略模式 封装一个行为（一段代码），并通过传递和使用创建的行为将方法的行为参数化。
        List<Apple> apples = filter(list, (Apple apple) -> "red".equals(apple.getColor()));

        List<String> stringList = filter(strList, StringUtils::isNotEmpty);

        forEach(Arrays.asList(1,2,3,4), System.out::println);

        List<Integer> mapResult = map(Arrays.asList("Lambda", "very", "amazing"), String::length);

        System.out.println(mapResult);


        list.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });


        list.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world");
            }
        });

        Thread thread1 = new Thread(() -> System.out.println("Hello world"));

    }
}

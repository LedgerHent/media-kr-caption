package com.kr.caption.java8.movingparam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LearnUseStream {


    public static void main(String[] args) {


        List<Apple> appleList = new ArrayList<>();

        List<Apple> red = appleList.stream()
                                   .filter(apple -> apple.getColor().equals("red"))
                                   .distinct()
                                   .limit(3).collect(Collectors.toList());


        List<String> appleNameList = appleList.stream().map(Apple::getColor).collect(Collectors.toList());

        Optional<String> anyObject = appleList.stream().map(Apple::getColor).findAny();

        boolean isAllHeavy = appleList.stream().allMatch(apple -> apple.getWeight() > 100);

        //归约
        List<Integer> numbers = new ArrayList<>();
        Integer all = numbers.stream().reduce(0, (a, b) -> a + b);

        Integer allInteger = numbers.stream().reduce(0, Integer::sum);

        //无初始值
        Optional<Integer> reduce = numbers.stream().reduce(Integer::sum);

        Optional<Integer> max = numbers.stream().reduce(Integer::max);




    }

}
package com.mhc.base;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {


    @Test
    public void store(){

        List<ErrorType> errorTypes = Arrays.asList(new ErrorType[]{ErrorType.First, ErrorType.Three,ErrorType.Second});

        errorTypes = errorTypes.stream().sorted(Comparator.comparingInt(ErrorType::getIndex)).collect(Collectors.toList());

        System.out.println(errorTypes);

    }
}

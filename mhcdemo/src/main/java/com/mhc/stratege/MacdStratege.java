package com.mhc.stratege;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.apache.poi.ss.formula.functions.T;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MacdStratege {

    public static void main(String[] args) {
        List<Float> collect = Stream.generate(Math::random).limit(20).map(Double::floatValue).collect(Collectors.toList());

        float[] floats = new float[collect.size()];
        for (int i = 0; i < collect.size(); i++) {
            floats[i] = collect.get(i);
        }

        MInteger mInteger = new MInteger();
        mInteger.value=1;

        double[] macdd = new double[20];
        double[] dif = new double[20];
        double[] dea = new double[20];



        Core talibCore = new Core();
        RetCode macd = talibCore.macd(1,10,floats,20,20,20,
                mInteger,mInteger,macdd,dif,dea);
        System.out.println(macd);
        System.out.println(Arrays.asList(macdd));
        System.out.println(Arrays.asList(dif));
        System.out.println(Arrays.asList(dea));

    }



}

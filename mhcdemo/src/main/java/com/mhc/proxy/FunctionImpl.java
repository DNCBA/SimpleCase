package com.mhc.proxy;

import java.util.function.Supplier;

public class FunctionImpl implements Supplier {


    @Override
    public Object get() {
        return "----> ";
    }
}

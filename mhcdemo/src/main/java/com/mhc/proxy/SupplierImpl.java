package com.mhc.proxy;

import java.util.function.Supplier;

public class SupplierImpl implements Supplier {


    @Override
    public Object get() {
        return "----> ";
    }
}

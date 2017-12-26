package com.tetraandroid.finalappexample.tweets;

import com.squareup.otto.Bus;

/**
 * Created by MMT5544 on 25-12-2017.
 */

public class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    public BusProvider(){}
}

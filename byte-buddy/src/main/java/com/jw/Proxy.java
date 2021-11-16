package com.jw;

public class Proxy {

    public String out1(String param1) {
        return "Proxy " + param1;
    }

    public String out2(String param1, String param2) {
        return "Proxy p1" + param1 + " p2" + param2;
    }
}

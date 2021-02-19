package com.mhc.mockito;

public class MockTarget {

    private TestService testService;


    public String say(String name) {
        return "Hello " + name;
    }


    public String doTest(String test) {
        return testService.test(test);
    }
}

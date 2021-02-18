package com.mhc.mockito;


import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.stubbing.OngoingStubbing;
import org.mockito.verification.VerificationMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class MockitoDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockitoDemo.class);


    @Test
    public void create() {
        //不会调用真实的对象直接返回null
        MockTarget mock = Mockito.mock(MockTarget.class);
        String jame = mock.say("jame");
        LOGGER.info("mock result {}", jame);


        //调用真实的方法
        MockTarget target = new MockTarget();
        MockTarget spyMock = Mockito.spy(target);
        String tom = spyMock.say("tom");
        LOGGER.info("mock spyMock {}", tom);
    }


    @Test
    public void verificationMode() {
        List list = Mockito.mock(List.class);


        list.toArray();
        VerificationMode only = VerificationModeFactory.only();
        Mockito.verify(list, only).toArray();
        LOGGER.info("only");

        //指定验证方法的调用次数
        list.add(1);
        Times times = VerificationModeFactory.times(1);
        Mockito.verify(list, times).add(Mockito.any());
        LOGGER.info("times");

        //最少调用一次方法
        list.clear();
        list.clear();
        VerificationMode atLeastOnce = VerificationModeFactory.atLeastOnce();
        Mockito.verify(list, atLeastOnce).clear();
        LOGGER.info("atLeastOnce");

        //做少调用n次
        list.size();
        list.size();
        list.size();
        VerificationMode atLeast = VerificationModeFactory.atLeast(2);
        Mockito.verify(list, atLeast).size();
        LOGGER.info("atLeast");

        //最多调用一次
        VerificationMode mostOnce = VerificationModeFactory.atMostOnce();
        Mockito.verify(list, mostOnce).toArray();
        LOGGER.info("atMostOnce");

        //最多调用n次
        list.hashCode();
        VerificationMode atMost = VerificationModeFactory.atMost(2);
        Mockito.verify(list, atMost).hashCode();
        LOGGER.info("atMost");
    }


    @Test
    public void result() {
        MockTarget mockTarget = Mockito.mock(MockTarget.class);
        Mockito.when(mockTarget.say("tom")).then(iv -> "inspect method");
        Mockito.when(mockTarget.say("exception")).thenThrow(new IllegalStateException());
        Mockito.when(mockTarget.say("return")).thenReturn("return");
        Mockito.when(mockTarget.say("answer")).thenAnswer(iv -> "answer");
        Mockito.when(mockTarget.say("real")).thenCallRealMethod();


        String result = "";
        result = mockTarget.say("tom");
        LOGGER.info("say tom {}", result);
        result = mockTarget.say("return");
        LOGGER.info("say return {}", result);
        result = mockTarget.say("answer");
        LOGGER.info("say answer {}", result);
        result = mockTarget.say("real");
        LOGGER.info("say real {}", result);
        try {
            mockTarget.say("exception");
        } catch (Exception e) {
            LOGGER.error("say exception ", e);
        }


        Mockito.when(mockTarget.say("three")).thenReturn("1", "2", "3");
        result = mockTarget.say("three");
        LOGGER.info(" {} ", result);
        result = mockTarget.say("three");
        LOGGER.info(" {} ", result);
        result = mockTarget.say("three");
        LOGGER.info(" {} ", result);

    }


    @Test
    private void argsMatcher() {
        MockTarget mockTarget = Mockito.mock(MockTarget.class);

        String result = "";

        Mockito.when(mockTarget.say(Mockito.anyString())).thenReturn("argumentMatcher any");
        result = mockTarget.say("any");
        LOGGER.info("any {}", result);


        Mockito.when(mockTarget.say(Mockito.eq("jame"))).thenReturn("is Jame?");
        result = mockTarget.say("jame");
        LOGGER.info("eq {}", result);


        Mockito.when(mockTarget.say(Mockito.argThat(argument -> "tom".equals(argument)))).thenReturn("oh tom");
        result = mockTarget.say("tom");
        LOGGER.info("matcher {}", result);
    }


    @Test
    public void argsCollect() {
        MockTarget mockTarget = Mockito.mock(MockTarget.class);
        mockTarget.say("1");
        mockTarget.say("2");
        mockTarget.say("3");
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mockTarget, Mockito.atMost(3)).say(argumentCaptor.capture());
        List<String> allValues = argumentCaptor.getAllValues();
        LOGGER.info("all value {}", allValues);

    }


}

package com.mhc.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class MockitoAnnotationDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockitoAnnotationDemo.class);


    @InjectMocks
    private MockTarget mockTarget;

//    @Spy
//    private TestService testService = new TestService();

    @Mock
    private TestService testService;


    @Captor
    private ArgumentCaptor<String> argumentCaptor;


    @Test
    public void testAnnotation() {
        String doTest = mockTarget.doTest("");
        LOGGER.info("doTest {}", doTest);
        String say = mockTarget.say("tom");
        LOGGER.info("say {}", say);

        Mockito.when(testService.test("")).thenReturn("what ?");
        LOGGER.info("doTest {}", mockTarget.doTest(""));
    }


    @Test
    public void testArgCaptor() {
        mockTarget.doTest("1");
        mockTarget.doTest("2");
        mockTarget.doTest("3");

        Mockito.verify(testService, Mockito.times(3)).test(argumentCaptor.capture());

        List<String> result = argumentCaptor.getAllValues();
        LOGGER.info("capture {}", result);
    }


}

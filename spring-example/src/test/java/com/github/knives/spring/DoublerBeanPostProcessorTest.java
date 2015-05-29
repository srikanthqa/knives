package com.github.knives.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/doubler-bean-post-processor.xml")
public class DoublerBeanPostProcessorTest {
    @Inject
    DoublerBean bean;

    @Test
    public void test() {
        assertEquals(4, bean.getValue());
    }
}

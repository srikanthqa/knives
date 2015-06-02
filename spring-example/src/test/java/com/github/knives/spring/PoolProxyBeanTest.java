package com.github.knives.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.target.CommonsPoolTargetSource;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.inject.Named;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/pool-proxy-bean.xml")
public class PoolProxyBeanTest {

    private PoolBean poolBean;

    private CommonsPoolTargetSource pool;

    private ApplicationContext applicationContext;

    //@Test(expected = Exception.class)
    public void testCommonsPool() throws Exception {
        for (int i = 0; i < 10; i++)
            System.out.println(pool.getTarget().hashCode());
    }

    @Test
    public void testCommonsPoolThroughApplicationContext() {
        for (int i = 0; i < 10; i++)
            applicationContext.getBean("poolBean");
    }

    @Inject
    public void setPoolBean(PoolBean poolBean) {
        this.poolBean = poolBean;
    }

    @Inject
    public void setPool(CommonsPoolTargetSource pool) {
        this.pool = pool;
    }

    @Inject
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}

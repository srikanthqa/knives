package com.github.knives.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Double the value before initialization (@PostConstructor)
 */
public class DoublerBeanPostProcessor implements BeanPostProcessor, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(DoublerBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        logger.debug("Doubler before initialization of '" + beanName + "' bean.");

        Class clazz = bean.getClass();

        ReflectionUtils.doWithMethods(clazz, new ReflectionUtils.MethodCallback() {
            public void doWith(Method method) {
                if (method.isAnnotationPresent(Double.class)) {
                    try {
                        PropertyDescriptor pd = BeanUtils.findPropertyForMethod(method);

                        int originalValue = (Integer) pd.getReadMethod().invoke(bean, null);
                        int doubledValue = originalValue * 2;

                        // set doubled value
                        pd.getWriteMethod().invoke(bean, new Object[]{doubledValue});

                        logger.debug("Doubled value." +
                                "  originalValue=" + originalValue +
                                "  doubledValue=" + doubledValue);
                    } catch (Throwable e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        });

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.debug("Doubler after initialization of '" + beanName + "' bean.");
        return bean;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}

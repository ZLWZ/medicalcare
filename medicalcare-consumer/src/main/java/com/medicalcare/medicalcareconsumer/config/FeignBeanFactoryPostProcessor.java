package com.medicalcare.medicalcareconsumer.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 *  当关闭ApplicationContext时，它将销毁所有单例bean，
 *  首先销毁eurekaAutoServiceRegistration，然后销毁feignContext。
 *  当销毁feignContext时，它将关闭与每个FeignClient相关联的ApplicationContext。
 *  因为eurekaAutoServiceRegistration侦听ContextClosedEvent，所以这些事件将被发送到该bean。不幸的是，
 *  因为它已经被销毁了，所以我们得到了上面的异常(尝试在销毁中创建bean)。
 */
@Component
public class FeignBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (containsBeanDefinition(beanFactory, "feignContext", "eurekaAutoServiceRegistration")) {
            BeanDefinition bd = beanFactory.getBeanDefinition("feignContext");
            bd.setDependsOn("eurekaAutoServiceRegistration");
        }
    }

    private boolean containsBeanDefinition(ConfigurableListableBeanFactory beanFactory, String... beans) {
        return Arrays.stream(beans).allMatch(b -> beanFactory.containsBeanDefinition(b));
    }
}

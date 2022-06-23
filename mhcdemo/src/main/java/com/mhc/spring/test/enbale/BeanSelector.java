package com.mhc.spring.test.enbale;

import com.mhc.spring.test.IService;
import com.mhc.spring.test.ServiceAImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class BeanSelector implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(ServiceAImpl.class).getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("serviceAImpl", beanDefinition);
    }

//    @Bean
//    public IService getService() {
//        return new ServiceAImpl();
//    }
}

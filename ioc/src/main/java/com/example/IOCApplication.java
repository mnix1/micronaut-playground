package com.example;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class IOCApplication {

    private static final Logger LOG = LoggerFactory.getLogger(IOCApplication.class);

    public static void main(String[] args) {
        args = new String[] { "-Dvehicles.suv=true" };
        ApplicationContext run = Micronaut.run(IOCApplication.class, args);
        LOG.info(run.getBean(VehicleService.class).vehicles().toString());
        //TODO check beans
        //        logBeans(run);
    }

    private static void logBeans(ApplicationContext context) {
        context
            .getAllBeanDefinitions()
            .forEach(beanDefinition -> {
                LOG.info(beanDefinition.getName() + "        " + beanDefinition);
            });
    }
}

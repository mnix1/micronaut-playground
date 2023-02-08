package com.example;

import com.example.repository.VehicleRepository;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class IOCApplication {

    private static final Logger LOG = LoggerFactory.getLogger(IOCApplication.class);

    public static void main(String[] args) {
        args = new String[] { "-Dvehicles.suv=true" };
        ApplicationContext context = Micronaut.run(IOCApplication.class, args);
        LOG.info(context.getBean(VehicleService.class).vehicles().toString());
        //TODO check beans
        //        logBeans(context);
    }

    private static void logBeans(ApplicationContext context) {
        context
            .getAllBeanDefinitions()
            .forEach(beanDefinition -> {
                LOG.info(beanDefinition.getName() + "        " + beanDefinition + "        " + beanDefinition.getBeanType().getName());
            });
        VehicleRepository vehicleRepository = context.getBean(VehicleRepository.class);
    }
}

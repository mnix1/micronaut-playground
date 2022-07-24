package com.example;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@Singleton
@InterceptorBean(Timed.class)
class TimedInterceptor implements MethodInterceptor<Object, Object> {
    private static final Logger LOG = LoggerFactory.getLogger(TimedInterceptor.class);

    @Nullable
    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        long startNanos = System.nanoTime();
        Object result = context.proceed();
        LOG.info(context.getName() + " execution took {}", TimeUnit.MILLISECONDS.convert(System.nanoTime() - startNanos, TimeUnit.NANOSECONDS));
        return result;
    }
}
package com.example;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Singleton
@InterceptorBean(Timed.class)
class TimedInterceptor implements MethodInterceptor<Object, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(TimedInterceptor.class);

    @Nullable
    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        Instant now = Instant.now();
        long startNanos = System.nanoTime();
        Object result = context.proceed();
        long diff = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startNanos, TimeUnit.NANOSECONDS);
        LOG.info(context.getName() + " execution took {} and started at {}", diff, now);
        return result;
    }
    //    @Override
    //    public int getOrder() {
    //        return InterceptPhase.RETRY.getPosition() - 1;
    //    }
}

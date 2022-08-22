package com.example;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Singleton;

@Singleton
@InterceptorBean(Stub.class)
class StubIntroduction implements MethodInterceptor<Object, Object> {

    @Nullable
    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        return context.getValue(Stub.class, context.getReturnType().getType()).orElse(null);
    }
}

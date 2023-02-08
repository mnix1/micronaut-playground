## [aop](https://docs.micronaut.io/latest/guide/index.html#aop)
- @Around
- @InterceptorBean(NotNull.class)
- @Introduction
- @Adapter
- @CacheConfig("slowMethod")
- @Cacheable
- @Retryable
- @CircuitBreaker(attempts = "1", delay = "100ms", reset = "1s")
- @Scheduled(fixedRate = "1s")
- [@EventListener](https://docs.micronaut.io/latest/guide/index.html#contextEvents)




- Omówić dwa typy AOP Around i Introduction
- Testy
  - Custom 
    - NotNullTest //AOP-test-1
    - TimedTest //AOP-test-2
    - StubTest //AOP-test-3
  - Built
    - ScheduleTest //AOP-test-4
    - EventsTest //AOP-test-5
    - RetryTest //AOP-test-6
    - CacheTest //AOP-test-7
    - CircuitBreakerTest //AOP-test-8
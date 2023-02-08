## [ioc](https://docs.micronaut.io/latest/guide/index.html#ioc)
- @Inject
- @Bean
- [Scopes](https://docs.micronaut.io/latest/guide/index.html#scopes)
  - @Singleton - indicates only one instance of the bean will exist. Note that when starting an ApplicationContext, by default @Singleton-scoped beans are created lazily and on-demand. This is by design to optimize startup time. Annotate any @Singleton-scoped bean with @Parallel which allows parallel initialization of your bean without impacting overall startup time. Eager initialization of @Singleton beans maybe desirable in certain scenarios, such as on AWS Lambda where more CPU resources are assigned to Lambda construction than execution.
  - @Context - indicates that the bean will be created at the same time as the ApplicationContext (eager initialization)
  - @Prototype - indicates that a new instance of the bean is created each time it is injected
  - @Infrastructure - represents a bean that cannot be overridden or replaced using @Replaces because it is critical to the functioning of the system.
  - @ThreadLocal - custom scope that associates a bean per thread via a ThreadLocal
  - @Refreshable - custom scope that allows a bean’s state to be refreshed via the /refresh endpoint.
  - @RequestScope - custom scope that indicates a new instance of the bean is created and associated with each HTTP request
- [@Factory](https://docs.micronaut.io/latest/guide/index.html#factories)
- @Any
- @Primary
- [@Named("v8")](https://docs.micronaut.io/latest/guide/index.html#qualifiers)
- [@Requires(property = "vehicles.expensive", value = "true")](https://docs.micronaut.io/latest/guide/index.html#conditionalBeans)
- [Life-Cycle](https://docs.micronaut.io/latest/guide/index.html#lifecycle)
  - @PostConstruct
  - @PreDestroy
- [@Introspected](https://docs.micronaut.io/latest/guide/index.html#introspection)



- Omówić Application Context
- Omówić beany i scopy
- Omówić apkę produkcyjną
- Omówić zarządzanie wstrzykiwaniem
- Testy
  - Test integracyjny - zmiana parametrów w testach //IOC-test-1
  - @Primary i @Any PrimaryTest //IOC-test-2
  - @Replaces ReplaceImplementationTest //IOC-test-3
  - @Inject InjectAllTypesTest //IOC-test-4
  - @Inject List HandlersTest //IOC-test-5
  - @Named QualifiersTest //IOC-test-6
  - @RequestScope RequestScopeTest //IOC-test-7
  - @Bean vs @Singleton BeanScopeTest //IOC-test-8
  - @Introspected BeanIntrospectionTest //IOC-test-9
  - Qualifiers BeanAnnotationMetadataTest //IOC-test-10
## Micronaut 3.5.3 Documentation

- [User Guide](https://docs.micronaut.io/3.5.3/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.5.3/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.5.3/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

---

- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

---
## ANNOTATIONS:


## http
- @Controller("/companies")
- @Get("/{id}")
- @Post("/random")
- @Client("/vehicles") HttpClient client;

## ioc
- @Inject
- @Bean
- Scopes
  - @Singleton - indicates only one instance of the bean will exist. Note that when starting an ApplicationContext, by default @Singleton-scoped beans are created lazily and on-demand. This is by design to optimize startup time. Annotate any @Singleton-scoped bean with @Parallel which allows parallel initialization of your bean without impacting overall startup time. Eager initialization of @Singleton beans maybe desirable in certain scenarios, such as on AWS Lambda where more CPU resources are assigned to Lambda construction than execution.
  - @Context - indicates that the bean will be created at the same time as the ApplicationContext (eager initialization)
  - @Prototype - indicates that a new instance of the bean is created each time it is injected
  - @Infrastructure - represents a bean that cannot be overridden or replaced using @Replaces because it is critical to the functioning of the system.
  - @ThreadLocal - custom scope that associates a bean per thread via a ThreadLocal
  - @Refreshable - custom scope that allows a bean’s state to be refreshed via the /refresh endpoint.
  - @RequestScope - custom scope that indicates a new instance of the bean is created and associated with each HTTP request
- @Factory
- @Any
- @Primary
- @Named("v8")
- @Requires(property = "vehicles.expensive", value = "true")
- @Requires(missingBeans = VehicleRepository.class)

## properties


## test
- @MicronautTest
- @ExtendWith(AlwaysRebuildContextJunit5Extension.class)
- @Property(name = "vehicles.expensive", value = "true")
- @Replaces

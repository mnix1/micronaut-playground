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


## [http](https://docs.micronaut.io/3.5.3/guide/index.html#httpServer)
- @Controller("/companies")
- HTTP Routing Annotations
  - @Delete - DELETE
  - @Get - GET
  - @Head - HEAD
  - @Options - OPTIONS
  - @Patch - PATCH
  - @Put - PUT
  - @Post - POST
  - @Trace - TRACE
- [@Client("/vehicles") HttpClient client](https://docs.micronaut.io/3.5.3/guide/index.html#clientAnnotation);
- Parameter Binding Annotation
  - @Body - Specifies the parameter for the body of the request - @Body String body
  - @CookieValue - Specifies parameters to be sent as cookies - @CookieValue String myCookie
  - @Header - Specifies parameters to be sent as HTTP headers - @Header String requestId
  - @QueryValue - Customizes the name of the URI parameter to bind from - @QueryValue("userAge") Integer age
  - @PathVariable - Binds a parameter exclusively from a Path Variable. - @PathVariable Long id
  - @RequestAttribute - Specifies parameters to be set as request attributes - @RequestAttribute Integer locationId
- [@Filter("/hello/**")](https://docs.micronaut.io/3.5.3/guide/index.html#filters)
- [@Version](https://docs.micronaut.io/3.5.3/guide/index.html#apiVersioning)
- [OpenApi]()
- [Management](https://docs.micronaut.io/3.5.3/guide/index.html#providedEndpoints)
## [ioc](https://docs.micronaut.io/3.5.3/guide/index.html#ioc)
- @Inject
- @Bean
- [Scopes](https://docs.micronaut.io/3.5.3/guide/index.html#scopes)
  - @Singleton - indicates only one instance of the bean will exist. Note that when starting an ApplicationContext, by default @Singleton-scoped beans are created lazily and on-demand. This is by design to optimize startup time. Annotate any @Singleton-scoped bean with @Parallel which allows parallel initialization of your bean without impacting overall startup time. Eager initialization of @Singleton beans maybe desirable in certain scenarios, such as on AWS Lambda where more CPU resources are assigned to Lambda construction than execution.
  - @Context - indicates that the bean will be created at the same time as the ApplicationContext (eager initialization)
  - @Prototype - indicates that a new instance of the bean is created each time it is injected
  - @Infrastructure - represents a bean that cannot be overridden or replaced using @Replaces because it is critical to the functioning of the system.
  - @ThreadLocal - custom scope that associates a bean per thread via a ThreadLocal
  - @Refreshable - custom scope that allows a bean’s state to be refreshed via the /refresh endpoint.
  - @RequestScope - custom scope that indicates a new instance of the bean is created and associated with each HTTP request
- [@Factory](https://docs.micronaut.io/3.5.3/guide/index.html#factories)
- @Any
- @Primary
- [@Named("v8")](https://docs.micronaut.io/3.5.3/guide/index.html#qualifiers)
- [@Requires(property = "vehicles.expensive", value = "true")](https://docs.micronaut.io/3.5.3/guide/index.html#conditionalBeans)
- [Life-Cycle](https://docs.micronaut.io/3.5.3/guide/index.html#lifecycle)
  - @PostConstruct
  - @PreDestroy
- [@Introspected](https://docs.micronaut.io/3.5.3/guide/index.html#introspection)


## [configuration](https://docs.micronaut.io/3.5.3/guide/index.html#config)
The convention is to search for a file named application.yml, application.properties, application.json or application.groovy.
In addition, like Spring and Grails, Micronaut allows overriding any property via system properties or environment variables.


Micronaut by default contains [PropertySourceLoader](https://docs.micronaut.io/3.5.3/guide/index.html#propertySource) implementations that load properties from the given locations and priority:

1. Command line arguments
2. Properties from SPRING_APPLICATION_JSON (for Spring compatibility)
3. Properties from MICRONAUT_APPLICATION_JSON
4. Java System Properties
5. OS environment variables
6. Configuration files loaded in order from the system property 'micronaut.config.files' or the environment variable MICRONAUT_CONFIG_FILES. The value can be a comma-separated list of paths with the last file having precedence. The files can be referenced from the file system as a path, or the classpath with a classpath: prefix.
7. Environment-specific properties from application-{environment}.{extension}
8. Application-specific properties from application.{extension}

## [aop](https://docs.micronaut.io/3.5.3/guide/index.html#aop)
- @Around
- @InterceptorBean(NotNull.class)
- @Introduction
- @Adapter
- @CacheConfig("slowMethod")
- @Cacheable
- @Retryable
- @CircuitBreaker(attempts = "1", delay = "100ms", reset = "1s")
- @Scheduled(fixedRate = "1s")
- [@EventListener](https://docs.micronaut.io/3.5.3/guide/index.html#contextEvents)

## test
- @MicronautTest
- @ExtendWith(AlwaysRebuildContextJunit5Extension.class)
- @Property(name = "vehicles.expensive", value = "true")
- [@Replaces](https://docs.micronaut.io/3.5.3/guide/index.html#replaces)


## DOCUMENTATION:
## [data](https://micronaut-projects.github.io/micronaut-data/latest/guide/)
- https://guides.micronaut.io/latest/micronaut-jpa-hibernate-gradle-java.html
- https://guides.micronaut.io/latest/micronaut-data-jdbc-repository-gradle-java.html


## [aws](https://micronaut-projects.github.io/micronaut-aws/3.5.1/guide/index.html)
- https://guides.micronaut.io/latest/mn-serverless-function-aws-lambda-gradle-java.html

## [graalvm](https://www.graalvm.org/)
- https://guides.micronaut.io/latest/mn-serverless-function-aws-lambda-graalvm-gradle-java.html
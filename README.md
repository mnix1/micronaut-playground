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
- @Singleton
- @Bean
- @Prototype
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

## [configuration](https://docs.micronaut.io/latest/guide/index.html#config)
The convention is to search for a file named application.yml, application.properties, application.json or application.groovy.
In addition, like Spring and Grails, Micronaut allows overriding any property via system properties or environment variables.


Micronaut by default contains [PropertySourceLoader](https://docs.micronaut.io/latest/guide/index.html#propertySource) implementations that load properties from the given locations and priority:

1. Command line arguments
2. Properties from SPRING_APPLICATION_JSON (for Spring compatibility)
3. Properties from MICRONAUT_APPLICATION_JSON
4. Java System Properties
5. OS environment variables
6. Configuration files loaded in order from the system property 'micronaut.config.files' or the environment variable MICRONAUT_CONFIG_FILES. The value can be a comma-separated list of paths with the last file having precedence. The files can be referenced from the file system as a path, or the classpath with a classpath: prefix.
7. Environment-specific properties from application-{environment}.{extension}
8. Application-specific properties from application.{extension}

(https://guides.micronaut.io/latest/micronaut-aws-parameter-store-gradle-java.html)

- Omówić środowiska
- Omówić jak priorytet propertisów
- Testy
  - EachPropertyTest
  - EachBeanTest
  - InjectValueTest
- Omówić połączenie z AWS parameter store
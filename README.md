# A simple REST API to manage books

![alt-text-16](https://live.staticflickr.com/65535/51300284866_8e9803d8ac_n.jpg "Digital Innovation One")![alt-text-17](https://live.staticflickr.com/65535/51301300290_ef0929d019_n.jpg "Órbi")![alt-text-18](https://live.staticflickr.com/65535/51300468168_db2772602b_n.jpg "GFT")![alt-text-19](https://live.staticflickr.com/65535/51300468243_6a63bccf2c_n.jpg "Avanade")![alt-text-20](https://live.staticflickr.com/65535/51300468188_b8cd498486_n.jpg "Everis")![alt-text-21](https://live.staticflickr.com/65535/51300992864_3f0e123f26_n.jpg "Impulso")![alt-text-22](https://live.staticflickr.com/65535/51300284786_5b4e4feb48_n.jpg "Banco Inter")![alt-text-23](https://live.staticflickr.com/65535/51299541472_9621efd000_n.jpg "Banco Carrefour")![alt-text-24](https://live.staticflickr.com/65535/51300992809_111bf9d021_n.jpg "MRV")![alt-text-25](https://live.staticflickr.com/65535/51300992829_a859e76404_n.jpg "Localiza Labs")![alt-text-26](https://live.staticflickr.com/65535/51299541332_79b40158c8_n.jpg "Banco Santander")

First of all, I would like to thank all the employees of [Digital Innovation One](https://web.digitalinnovation.one) (DIO), represented by Mr. Iglá Generoso, all  DIO supporters and the entire software developer community. Thanks also to [Prof. Rodrigo Peleias](https://github.com/rpeleias), who inspired me and taught me how to do this work.

#### Objectives

Build a simple Rest API to manage books applying TDD (Test Driven Development) and deploy it to Heroku

#### Full documentation available at https://bruno-books-management.herokuapp.com/swagger-ui.html

It may take some time to open because it is hosted for free on Heroku.

#### The API status is monitored by the Spring Actuator.

#### https://bruno-books-management.herokuapp.com/actuator/health

#### Stack

![alt-text-1](https://live.staticflickr.com/65535/51300833708_75a800768f_n.jpg "Java")![alt-text-2](https://live.staticflickr.com/65535/51300994079_471d0a2a75_n.jpg "Spring")![alt-text-3](https://live.staticflickr.com/65535/51300994179_a899b218f0_n.jpg "Heroku")![alt-text-4](https://live.staticflickr.com/65535/51299906262_98e489b59a_n.jpg "Github")![alt-text-5](https://live.staticflickr.com/65535/51301301835_3b614ef1dc_n.jpg "Git")![alt-text-6](https://live.staticflickr.com/65535/51300286091_47feff87ed_n.jpg "PostgreSQL")![alt-text-7](https://live.staticflickr.com/65535/51300649436_ca50fd6815_n.jpg "H2")![alt-text-8](https://live.staticflickr.com/65535/51301663380_cb6be126b4_n.jpg "Project Lombok")![alt-text-9](https://live.staticflickr.com/65535/51300994044_39e424118d_n.jpg "Swagger")![alt-text-10](https://live.staticflickr.com/65535/51301360574_d5ff7350f7_n.jpg "JUnit")![alt-text-11](https://live.staticflickr.com/65535/51301140479_382d485de8_n.jpg "Mockito")![alt-text-12](https://live.staticflickr.com/65535/51299906232_f06df32fee_n.jpg "Hamcrest")![alt-text-13](https://live.staticflickr.com/65535/51300681046_fda4dfa22b_n.jpg "IntelliJ IDEA")![alt-text-14](https://live.staticflickr.com/65535/51300681056_8aac67b995_n.jpg "Gradle")![alt-text-15](https://live.staticflickr.com/65535/51299937717_7bd7171088_n.jpg "Postman")

Java 11

Spring Boot version 2.5.1

- Project dependencies

  ```
     implementation 'org.springframework.boot:spring-boot-starter-actuator'
     implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
     implementation 'org.springframework.boot:spring-boot-starter-validation'
     implementation 'org.springframework.boot:spring-boot-starter-web'
     implementation 'org.springframework.boot:spring-boot-starter-security'
     testImplementation 'org.springframework.security:spring-security-test'
     testImplementation 'org.springframework.boot:spring-boot-starter-test'
     implementation group: 'org.postgresql', name: 'postgresql', version: '42.2.18'
     implementation group: 'io.springfox', name: 'springfox-swagger2', version: '2.9.2'
     implementation group: 'io.springfox', name: 'springfox-swagger-ui', version: '2.9.2'
     testImplementation group: 'org.mockito', name: 'mockito-core', version: '3.11.0'
     testImplementation group: 'org.hamcrest', name: 'hamcrest-all', version: '1.3'
     testImplementation group: 'org.junit.jupiter', name: 'junit-jupiter-api', version: '5.7.2'
     compileOnly 'org.projectlombok:lombok'
     runtimeOnly 'com.h2database:h2'
     annotationProcessor 'org.projectlombok:lombok'
  ```

#### Tools

Intellj IDEA Community Edition

Gradle

Postman

GIT / GITHUB 

![alt-text-24](https://live.staticflickr.com/65535/51300464828_710c99858c_n.jpg "StackOverFlow")![alt-text-25](https://live.staticflickr.com/65535/51300464883_4e65e30dc1_n.jpg "Baeldung")![alt-text-26](https://live.staticflickr.com/65535/51301297305_5ea9209ecf_n.jpg "Medium")

#### TDD

Tests for all service and resource methods 

#### Entities "Author" and "Book"

I used lombok to write less and produce more

JPA annotations

@ManyToMany

SpringFox Swagger 2 annotations on endpoints

#### "application.properties" file

```
spring.profiles.active=${APP_PROFILE:dev}
heroku.swagger.url=${SWAGGER_URL:http://localhost:8080}
spring.jpa.open-in-view=false

management.endpoints.web.exposure.include=health
management.endpoints.enabled-by-default=false
management.endpoint.health.enabled=true
```

#### Layers

 - Resource

   - returning ResponseEntity
     	- JSON

 - Service

   - Custom Exceptions

 - Repository

   ```
   @Transactional(readOnly = true)
   @Query(value = "SELECT DISTINCT book " +
           "FROM #{#entityName} book " +
           "INNER JOIN book.authors aut " +
           "WHERE aut.name " +
           "LIKE %?#{escape([0])}% escape ?#{escapeCharacter()} " +
           "AND aut.name " +
           "LIKE %:name%")
   Page<Book> findBooksByAuthorName(@Param("name") String name, Pageable pageable);
   ```

#### DTO (Data Transfer Object)

Attribute validation is here

#### 

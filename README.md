![display](https://repository-images.githubusercontent.com/432807613/fe6d8678-4642-4b0a-815e-e97bcebe57ea)

# Spring boot 2 - JPA DataTable

This guide walks you through the process of building a Spring boot 2 application that uses JPA DataTable.

Check this tutorial on my [Blog](https://dev.djamelkorei.me/spring-boot-jpa-datatable) 👋
## What You Will build
You will build a Spring Boot application with quick datatable fully configurable.

## What You Need
- A favorite text editor or IDE
- JDK 1.8 or later
- Gradle 4+ or Maven 3.2+

## Setup Project With Spring Initializr   

- Navigate to https://start.spring.io.

- define the project name example: `spring-boot-jpa-datatable`
- Choose Project **Maven** and the language  **Java**.
- Choose Your **Java** version ex: **17**
- Click add dependencies and select:
  - Spring Web
  - Thymeleaf
  - Lombok
  - Spring Data JPA
  - H2 Database

- Click Generate.

Unzip the Downloaded Zip and open the Project using your favorite text editor or IDE

## DataTable Dependency
Add the following Maven snippet to the `pom.xml`
```xml
<dependency>
    <groupId>com.github.darrachequesne</groupId>
    <artifactId>spring-data-jpa-datatables</artifactId>
    <version>5.1.0</version>
</dependency>
```

## Create the Backend Ressource
Define the User Entity
```java
@Entity(name = "users")
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long userId;

  private String firstName;

  private String lastName;

  private String username;

  private String email;

  private boolean status;

}
```
Define the User Repository
```java
@Repository
public interface UserRepository extends DataTablesRepository<User, Long> {}
```
Define the User Service
```java
@Service
@RequiredArgsConstructor
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public DataTablesOutput<User> findAllDatatable(DataTablesInput input) {
        log.debug("Request to get all Users");
        return userRepository.findAll(input);
    }

}
```
Define the User Rest API
```java
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

  private final Logger log = LoggerFactory.getLogger(UserResource.class);
  private final UserService userService;

  /**
   * {@code GET  /users} : get all the users.
   *
   * @return the {@link DataTablesOutput} with status {@code 200 (OK)} and the list of users in body.
   */
  @GetMapping("/users")
  public DataTablesOutput<User> findAllDatatable(@Valid DataTablesInput input) {
    log.debug("REST request to get a page of Users");
    return userService.findAllDatatable(input);
  }

}
```
Define the User View
```java
@Controller
public class UserView {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
```
Set The `RepositoryFactoryBean` to `DataTablesRepositoryFactory` and the base package to scan your repositories
```java
@Configuration
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class, basePackages = "com.example.demo")
public class DataTablesConfiguration {}
```
Create `data.sql` under the `resources` folder to populate our table
```sql
INSERT INTO USERS (USER_ID, FIRST_NAME, LAST_NAME, EMAIL, USERNAME, STATUS)
VALUES (1, 'Andre', 'Denesik', 'tressa.hamill@example.com', 'bruen.fletcher', true),
       (2, 'Shanel', 'Fahey', 'will.eleonore@example.com', 'kgleichner', true),
       (3, 'Rosanna', 'Kilback', 'ganderson@example.org', 'jamarcus20', false),
       (4, 'Americo', 'Franecki', 'andres67@example.com', 'donnelly.napoleon', true),
       (5, 'Ova', 'Gusikowski', 'stehr.cruz@example.net', 'dschneider', true),
       (6, 'Rhiannon', 'Schmitt', 'brycen.klein@example.net', 'crystel.kilback', true),
       (7, 'Eriberto', 'Frami', 'hillard85@example.com', 'corwin.jeffrey', false),
       (8, 'Ebba', 'Krajcik', 'neoma38@example.org', 'mozelle.bernier', true),
       (9, 'Beth', 'Balistreri', 'tstehr@example.com', 'olson.meagan', true),
       (10, 'Jesse', 'Wehner', 'kristoffer.wiza@example.org', 'jada12', false),
       (11, 'Samanta', 'Kautzer', 'dina.kuhic@example.net', 'katelin.strosin', true),
       (12, 'Lauretta', 'Deckow', 'fshields@example.com', 'skylar.macejkovic', true),
       (13, 'Vella', 'Dibbert', 'vonrueden.harmon@example.com', 'pcrona', true),
       (14, 'Kay', 'Haley', 'slangworth@example.com', 'shields.malika', true),
       (15, 'Brandon', 'Russel', 'quigley.danny@example.com', 'donnelly.dane', true),
       (16, 'Juvenal', 'Wolf', 'shana41@example.net', 'yazmin.strosin', false),
       (17, 'Tad', 'Kuhic', 'reinger.everardo@example.org', 'jstracke', true),
       (18, 'Pink', 'Block', 'hettinger.otha@example.org', 'sanford.lysanne', true),
       (19, 'Kacie', 'Daugherty', 'mayer.florian@example.com', 'ghaag', true),
       (20, 'Helmer', 'Ziemann', 'kayli.block@example.net', 'kbechtelar', false);
```
When runing the application, by default the `data.sql` will be executed before the entity will be created into the database, to prevent that add the following property under the `application.properties` 
```properties
spring.jpa.defer-datasource-initialization=true
```

## Create the Frontend View
Create the the `index.html` under the `resources\templates` folder
```html
<!DOCTYPE HTML>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spring Boot JPA Datatable</title>
    <!-- Bootstrap 5 css -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css    ">
    <!-- Datatable Bootstrap 5 css -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.3/css/dataTables.bootstrap5.min.css">
</head>
<body style="background-color: #6db751">

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-12">
            <h1 class="text-light fw-bold">Spring Boot JPA DataTable</h1>
            <hr width="100px" height="10px" size="10px" class="bg-light">
        </div>
        <div class="col-6-md">
            <div class="card border-0 shadow-lg mt-4">
                <div class="card-header d-flex align-items-center">
                    <h4>List User</h4>
                </div>
                <div class="card-body">
                    <table id="userTable" class="table table-striped display shadow-sm" style="width:100%">
                        <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Jquery js -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<!-- Jquery Datatable js -->
<script type="text/javascript" src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
<!-- Datatable Bootstrap 5 js -->
<script type="text/javascript" src="https://cdn.datatables.net/1.11.3/js/dataTables.bootstrap5.min.js"></script>
<!-- Datatable Serverside Spring fixer js -->
<script type="text/javascript" th:src="@{/js/jquery.spring-friendly.js}"></script>
<!-- Script js - initialize datable -->
<script>
    $('#userTable').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": "/api/users",
        columns: [
            {
                data: 'firstName',
            },
            {
                data: 'lastName',
            },
            {
                data: 'username',
                render: (data) => `<span class="shadow-sm px-2 py-1 rounded-3 bg-dark text-light">@${data}</span>`
            },
            {
                data: 'email',
                render: (data) => `<a class="text-dark" href="mailto: ${data}"  target="_blank">${data}</a>`
            },
            {
                data: 'status',
                render: (data) => `<span class="badge bg-${data ? 'success' : 'warning'}">${data ? 'active' : 'inactive'}</span>`
            },
        ]
    });
</script>
</body>
</html>
```
Create the the `jquery.spring-friendly.js` under the `resources\static` folder
```js
// From https://github.com/jquery/jquery/blob/master/src/serialize.js
// Overrides data serialization to allow Spring MVC to correctly map input parameters : column[0][data] now becomes column[0].data
(function ($) {
    let r20 = /%20/g, rbracket = /\[\]$/, rCRLF = /\r?\n/g, rsubmitterTypes = /^(?:submit|button|image|reset|file)$/i,
        rsubmittable = /^(?:input|select|textarea|keygen)/i;

    function customBuildParams(prefix, obj, traditional, add) {
        let name;

        if (jQuery.isArray(obj)) {
            // Serialize array item.
            jQuery.each(obj, function (i, v) {
                if (traditional || rbracket.test(prefix)) {
                    // Treat each array item as a scalar.
                    add(prefix, v);

                } else {
                    // Item is non-scalar (array or object), encode its numeric
                    // index.
                    customBuildParams(prefix + "["
                        + (typeof v === "object" ? i : "") + "]", v,
                        traditional, add);
                }
            });

        } else if (!traditional && jQuery.type(obj) === "object") {
            // Serialize object item.
            for (name in obj) {
                // This is where the magic happens
                customBuildParams(prefix + "." + name, obj[name], traditional,
                    add);
            }

        } else {
            // Serialize scalar item.
            add(prefix, obj);
        }
    }

    $.param = function (a, traditional) {
        let prefix, s = [], add = function (key, value) {
            // If value is a function, invoke it and return its value
            value = jQuery.isFunction(value) ? value() : (value == null ? ""
                : value);
            s[s.length] = encodeURIComponent(key) + "="
                + encodeURIComponent(value);
        };

        // Set traditional to true for jQuery <= 1.3.2 behavior.
        if (traditional === undefined) {
            traditional = jQuery.ajaxSettings
                && jQuery.ajaxSettings.traditional;
        }

        // If an array was passed in, assume that it is an array of form
        // elements.
        if (jQuery.isArray(a) || (a.jquery && !jQuery.isPlainObject(a))) {
            // Serialize the form elements
            jQuery.each(a, function () {
                add(this.name, this.value);
            });

        } else {
            // If traditional, encode the "old" way (the way 1.3.2 or older
            // did it), otherwise encode params recursively.
            for (prefix in a) {
                customBuildParams(prefix, a[prefix], traditional, add);
            }
        }

        // Return the resulting serialization
        return s.join("&").replace(r20, "+");
    };
})(jQuery);
```
## Test
Run the Java application as a `SpringBootApplication` with your IDE or use the following command line

```shell
 ./mvnw spring-boot:run
```
Now, you can open the URL below on your browser, default port is `8080` you can set it under the `application.properties`
```
http://localhost:8080
```
## Summary

Congratulations 🎉 ! You've created quick datatable using Spring Boot 2, JPA & DataTable
## Blog

Check new tutorials on my [Blog](https://dev.djamelkorei.me/) 👋
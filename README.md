<a href="README-RU.md">RU</a> | <a>EN</a>

# Audit Starter

**Audit Starter** - is a library for auditing user actions. Saves actions to the database

## How to use
The project is not yet available in Maven Central, so it requires manual installation.

### Requirements
Make sure that you have the following components installed:
- Oracle Java 17
- Apache Maven 3.9.8

### Installation

1. Clone the repository and go to the project folder
   ```shell
   git clone https://github.com/sabitovka/audit-starter.git && cd audit-starter
   ```
2. Compile and install the library in a local repository
   ```shell
   mvn clean compile install
   ```
3. Connect the dependency in your project
   ```xml
   <dependency>
      <groupId>io.sabitovka.starter</groupId>
      <artifactId>audit-starter</artifactId>
      <version>1.0.0</version>
   </dependency>
   ```

### Usage
Enable auditing using the `@EnableAudit` annotation

```java
import io.sabitovka.audit.annotation.EnableAudit;

@EnableAudit
public class Application {
   public static void main(String[] args) {
      // ...
   }
}
```

Audit Starter uses the database connection settings from your application. Make sure that they are specified correctly.
Also, to create tables, you need to perform a Liquibase migration.
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/database-name
    username: <username>
    password: <password>
    driverClassName: org.postgresql.Driver
  liquibase:
    change-log: classpath:db/changelog/changelog.xml
```

To perform the migration in the main changelog file, import the migration file
```xml
<include file="audit-changelog.xml" relativeToChangelogFile="true" />
```

Migration will create a table named `audit` and a structure:

| Field Name  | Data type | Description                                   |
|-------------|-----------|-----------------------------------------------|
| id (PK)     | bigint    | Primary key                                   |
| username    | varchar   | The name of the user who triggered the action |
| ip          | varchar   | The user's IP address                         |
| action      | varchar   | The action that was executed                  |
| arguments   | varchar   | The arguments that were passed                |
| timestamp   | datetime  | timestamp                                     |

By default, empty lines will be saved for the username and ip fields. If you need to specify the username and his ip in the audit,
you need to implement the `AuditUserService` class.
```java
import io.sabitovka.audit.service.AuditUserService;

@Service
public class AuditUserServiceImpl implements AuditUserService {
   @Override
   public String getUsername() {
      Optional<UserDetails> userDetails = Optional.ofNullable(AuthInMemoryContext.getContext().isLoggedIn()
              ? AuthInMemoryContext.getContext().getAuthentication() : null);

      return userDetails.map(UserDetails::getUsername).orElse("Not authorized");
   }

   @Override
   public String getIp() {
      return AuthInMemoryContext.getContext().getIp();
   }
}
```

Use the `@Audit` annotation with the text of the completed action. In order to escape arguments (most often when entering a password),
you can annotate the parameter using `@IgnoreAudit`

```java
import io.sabitovka.audit.annotation.Audit;
import io.sabitovka.audit.annotation.IgnoreAudit;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
   // Repositories, other fields of the service

   @Audit(action = "Logged in to the system")
   @Override
   public String login(@IgnoreAudit UserLoginDto userLoginDto) {
      // ...
   }
}
```

## Contribution
Contributions are what make the open source developer community a wonderful place to learn,
inspire, and create. **I will highly appreciate any of your contributions**.

If you have suggestions for improving the system, make a Fork and create a Merge Request. You can also create an issue with the tag "`enhancement`"

1. Make a Fork of the project
2. Create a new feature branch (git checkout -b feature/your-feature)
3. Commit the changes (git commit -m 'New feature added')
4. Record the changes (git push origin feature/your-feature)
5. Create a merge request

## License
Distributed under the MIT license. For more information, see the file [LICENSE](LICENSE).

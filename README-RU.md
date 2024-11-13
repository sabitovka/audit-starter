<a>RU</a> | <a href="README.md">EN</a>

# Audit Starter

**Audit Starter** - это библиотека для аудита действий пользователя. Сохраняет действия в базу данных

## Как использовать
Проект пока недоступен в Maven Central, поэтому требует ручной установки.

### Требования
Убедитесь, что у вас установлены следующие компоненты:
- Oracle Java 17
- Apache Maven 3.9.8

### Установка

1. Клонируйте репозиторий и перейдите в папку с проектом
   ```shell
   git clone https://github.com/sabitovka/audit-starter.git && cd audit-starter
   ```
2. Скомпилируйте и установите библиотеку в локальный репозиторий
   ```shell
   mvn clean compile install
   ```
3. Подключите зависимость в своем проекте
   ```xml
   <dependency>
      <groupId>io.sabitovka.starter</groupId>
      <artifactId>audit-starter</artifactId>
      <version>1.0.0</version>
   </dependency>
   ```

### Использование
Включите аудит с помощью аннотации `@EnableAudit`

```java
import io.sabitovka.audit.annotation.EnableAudit;

@EnableAudit
public class Application {
   public static void main(String[] args) {
      // ...
   }
}
```

Audit Starter использует настройки подключения к базе данных из вашего приложения. Убедитесь, что они указаны верно.
Также для создания таблиц необходимо выполнить миграцию Liquibase.
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

Для выполнения миграции в главном changelog-файле импортируйте файл миграции
```xml
<include file="audit-changelog.xml" relativeToChangelogFile="true" />
```

Миграция создаст таблицу с именем `audit` и структурой:

| Имя поля   | Тип данных | Описание                              |
|------------|------------|---------------------------------------|
| id (PK)    | bigint     | Первичный ключ                        |
| username   | varchar    | Имя пользователя, вызвавшего действие |
| ip         | varchar    | IP адрес пользователя                 |
| action     | varchar    | Событие, которое было выполнено       |
| arguments  | varchar    | Аргументы, которые были переданы      |
| timestamp  | timestamp  | Время выполнения обработки            |

По-умолчанию будут сохраняться пустые строки для полей username и ip. Если вам нужно указывать в аудите пользователя и его ip,
нужно реализовать класс `AuditUserService`.

```java
import io.sabitovka.audit.service.AuditUserService;

@Service
public class AuditUserServiceImpl implements AuditUserService {
   @Override
   public String getUsername() {
      Optional<UserDetails> userDetails = Optional.ofNullable(AuthInMemoryContext.getContext().isLoggedIn()
              ? AuthInMemoryContext.getContext().getAuthentication() : null);

      return userDetails.map(UserDetails::getUsername).orElse("Не авторизован");
   }

   @Override
   public String getIp() {
      return AuthInMemoryContext.getContext().getIp();
   }
}
```

Используйте аннотацию `@Audit` с текстом выполненного действия. Для того чтобы экранировать аргументы (чаще всего при вводе пароля),
можно аннотировать параметр с помощью `@IgnoreAudit`

```java
import io.sabitovka.audit.annotation.Audit;
import io.sabitovka.audit.annotation.IgnoreAudit;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
   // Репозиторий, другие поля сервиса

   @Audit(action = "Выполнен вход в систему")
   @Override
   public String login(@IgnoreAudit UserLoginDto userLoginDto) {
      // ...
   }
}
```

## Вклад
Вклады - это то, что делает сообщество разработчиков с открытым исходным кодом замечательным местом для обучения,
вдохновения и творчества. **Я высоко оценю любой ваш вклад**.

Если у вас есть предложения по улучшению системы, сделайте Форк и создайте Запрос на слияние. Также вы можете создать issue с тегом "`enhancement`"

1. Сделайте Форк проекта
2. Создайте новую ветку фичи (git checkout -b feature/your-feature)
3. Закоммитте изменения (git commit -m 'Добавлена новая фича')
4. Запуште изменения (git push origin feature/your-feature)
5. Создайте запрос на слияние

## Лицензия
Распространяется по лицензии MIT. Дополнительную информацию смотрите в файле [LICENSE](LICENSE).

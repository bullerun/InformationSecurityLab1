# Лабораторная работа 1: Разработка защищенного REST API с интеграцией в CI/CD

## Описание

Безопасное backend-приложение на Spring Boot с автоматизированной проверкой кода на уязвимости.

**Стек:** Java 17, Spring Boot 3.2.0, PostgreSQL, Maven

## Разработанные эндпоинты

### POST /auth/register — регистрация пользователя

Доступен без аутентификации.

```bash
curl --location 'localhost:8080/auth/register' \
--header 'Content-Type: application/json' \
--data '{
    "username": "user1",
    "password": "password123",
    "email": "user1@example.com"
}'
```

### POST /auth/login — аутентификация пользователя

Доступен без аутентификации. Возвращает JWT токен.

```bash
curl --location 'localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "username": "user1",
    "password": "password123"
}'
```

### GET /api/data — получение данных (постов)

Доступен только для аутентифицированных пользователей.

```bash
curl --location 'localhost:8080/api/data' \
--header 'Authorization: Bearer <JWT_TOKEN>'
```

### POST /api/data — создание поста

Доступен только для аутентифицированных пользователей.

```bash
curl --location 'localhost:8080/api/data' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <JWT_TOKEN>' \
--data '{
    "title": "My Post",
    "content": "Post content here"
}'
```

### GET /api/admin/users — получение всех пользователей

Доступен только для пользователей с ролью ADMIN.

```bash
curl --location 'localhost:8080/api/admin/users' \
--header 'Authorization: Bearer <ADMIN_JWT_TOKEN>'
```

### DELETE /api/admin/data/{id} — удаление любого поста

Доступен только для пользователей с ролью ADMIN.

```bash
curl --location --request DELETE 'localhost:8080/api/admin/data/1' \
--header 'Authorization: Bearer <ADMIN_JWT_TOKEN>'
```

## Меры защиты

### 1. Аутентификация (JWT)

При успешной регистрации или авторизации генерируется JWT токен. Класс `JwtAuthenticationFilter` проверяет токен на всех защищённых эндпоинтах.

```java
if (jwtService.isTokenValid(jwt, userDetails)) {
    SecurityContextHolder.getContext().setAuthentication(authToken);
}
```

### 2. Защита от SQL-инъекций

Используется ORM Hibernate (Spring Data JPA), который создаёт параметризированные запросы к БД вместо прямых SQL-запросов.

```java
Optional<User> findByUsername(String username);
```

### 3. Защита от XSS

Экранирование пользовательских данных с помощью `HtmlUtils.htmlEscape()`:

```java
private String sanitize(String input) {
    if (input == null) return null;
    return HtmlUtils.htmlEscape(input);
}
```

### 4. Защита паролей

Все пароли хешируются алгоритмом BCrypt перед сохранением в БД:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

## CI/CD Pipeline

Pipeline настроен в `.github/workflows/ci.yml`:

- **build** — сборка проекта
- **sast** — SpotBugs (статический анализ кода)
- **sca** — OWASP Dependency-Check (анализ зависимостей)
- **test** — запуск тестов

## Запуск проекта

### 1. Запустить PostgreSQL

```bash
docker-compose up -d
```

### 2. Запустить приложение

```bash
mvn spring-boot:run
```

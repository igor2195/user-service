# User Service

REST API сервис для управления пользователями и адресами с JWT авторизацией, валидацией данных, Swagger документацией и SPA frontend (JavaScript).

---

## Описание проекта

Проект реализует CRUD систему для:

- Пользователей (Users)
- Адресов (Addresses)

С поддержкой:

- JWT authentication
- Role-based access (ADMIN / ANONYMOUS)
- Валидации Hibernate Validator
- Swagger/OpenAPI документации
- SPA frontend (vanilla JavaScript + REST)
- Flyway миграций
- In-memory / embedded database (HSQLDB)

---

## Технологический стек

- Java 17+
- Spring Boot
- Spring MVC
- Spring Data JPA (Hibernate)
- Spring Security + JWT
- MapStruct
- Flyway
- HSQLDB 
- Swagger (SpringDoc OpenAPI)
- Maven
- Vanilla JavaScript (frontend SPA)

---

## Функциональность

### Users
- CRUD пользователей
- Поиск по имени / фамилии / отчеству
- Связь с Address (One-to-One)

### Addresses
- CRUD адресов
- Поиск по региону / городу / улице
- Привязка к пользователю

---

## Безопасность
Проект использует JWT авторизацию.

### Роли:
- `ROLE_ADMIN` — полный доступ(admin/admin)
- `ANONYMOUS` — доступ только на чтение (GET /v1/**)

---

## Локальный запуск проекта 
1. Клонирование репозитория
   - git clone https://github.com/igor2195/user-service.git
   - cd user-service
2. Сборка проекта
   - Собрать проект без запуска:
   - mvn clean install
3. Запуск приложения
   - через Maven: mvn spring-boot:run
4. Доступ к приложению
   - Авторизация: http://localhost:8080/user-service/login.html
   - После запуска сервис будет доступен: http://localhost:8080/user-service/
   - Swagger доступен: http://localhost:8080/user-service/swagger-ui/index.html

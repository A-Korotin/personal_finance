# Описание
Приложение для учета личных финансов, разработанное в рамках учебной практики
# Развертывание и запуск
Развертывание и запуск производятся при помощи docker compose.

Для запуска нужно выполнить команду:
```shell
$ docker compose -f .\devops\docker-compose.yml up -d 
```
После исполнения поднимется БД приложения, сервер авторизации Keycloak, БД Keycloak 
и само приложение (будет произведена компиляция из исходного кода).
# Документация
После запуска Swagger-документация будет доступна по URL
`http://localhost:8081/api/swagger-ui/index.html`

Сервер авторизации Keycloak будет доступен по URL
`http://localhost:8080/`
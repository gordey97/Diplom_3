# Stellar Burgers — UI tests

Автотесты задания 3 дипломной работы. Проект использует Java 11, JUnit 4,
Selenium WebDriver, Page Object и Allure.

## Покрытые сценарии

- успешная регистрация;
- сообщение об ошибке при пароле короче 6 символов;
- вход по кнопке «Войти в аккаунт»;
- вход через «Личный Кабинет»;
- вход через форму регистрации;
- вход через форму восстановления пароля;
- переходы между разделами «Булки», «Соусы» и «Начинки».

Тестовые пользователи создаются с уникальными адресами и удаляются через API.

## Запуск

Google Chrome:

```bash
mvn clean test -Dbrowser=chrome
```

Яндекс Браузер (укажи пути к браузеру и совместимому ChromeDriver):

```bash
mvn clean test -Dbrowser=yandex \
  -Dyandex.binary="/path/to/yandex-browser" \
  -Dyandex.driver="/path/to/chromedriver"
```

Если совместимый драйвер доступен Selenium Manager, параметр
`yandex.driver` можно не передавать.

Allure:

```bash
mvn allure:serve
```

Результаты тестов сохраняются в `target/allure-results`.

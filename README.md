## ibanktest запуск

RunnerClassGenerator отвечает за генерацию тестовых данных для тестов
CucumberRunner за запуск тестов, IBankTest.feature - сама фича с тестами.
Строка запуска: 
- mvn clean compile exec:java -Denvironment=service "-Dcucumber.options=--tags @ibank-login-test" test
- runApp test


*chromedriver.exe в проекте работает с последней (30.05.2022) версией хрома - 102.0.5005

## Сомнительное поведение SUT

В этом задании оно не столько сомнительное, сколько не до конца оптимизированное:
- При создании нового пользователя API лучше возвращать код 201;
- Если статуса всего два - active и blocked, нет смысла держать для них стрингу, можно сделать boolean active и отправлять true/false - 
c ними сложнее запутаться.

## Возможности апгрейда

В дальнейшем можно отделить общую часть, которая есть у обоих заданий, в отдельный мавен-проект и коннектить его как зависимость.
Кроме того, можно прикрутить аллюр для красивых тестов, ну и настроить CI в jenkins, например. Или gitlab CI, если на то есть средства :)
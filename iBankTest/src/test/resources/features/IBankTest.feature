#language: ru
@ibank-login-test
Функционал: Проверка функционала входа в личный кабинет банка

	@ibank-login-test @positive
	Структура сценария: Авторизация в личном кабинете происходит успешно
		Дано данные пользователя подгружены из файла <file>
		И выполнен POST - запрос по url - "#{api.url}" c телом "#{jsonBody}"
		И и код ответа на запрос равен "200"
		Когда пользователь открыл главную страницу
		И загружена страница "Главная страница"
		И заполняются поля:
			| Логин  | #{login}    |
			| Пароль | #{password} |
		И выполнено нажатие на Продолжить
		Тогда загружена страница "Личный кабинет"
		Примеры:
			| file                       |
			| GeneratedUser_active0.json |

	@ibank-login-test @negative
	Структура сценария: Авторизация в личном кабинете не происходит при незаполненных обязательных полях
		Дано данные пользователя подгружены из файла <file>
		И выполнен POST - запрос по url - "#{api.url}" c телом "#{jsonBody}"
		И и код ответа на запрос равен "200"
		Когда пользователь открыл главную страницу
		И загружена страница "Главная страница"
		И заполняются поля:
			| Логин  | <login>    |
			| Пароль | <password> |
		И выполнено нажатие на Продолжить
		И поле <field> видимо
		Примеры:
			| file                       | field              | login    | password    |
			| GeneratedUser_active1.json | Не заполнен логин  |          | #{password} |
			| GeneratedUser_active2.json | Не заполнен пароль | #{login} |             |

	@ibank-login-test @negative
	Структура сценария: Авторизация в личном кабинете не происходит при ошибке в заполнении одного из полей
		Дано данные пользователя подгружены из файла <file>
		И выполнен POST - запрос по url - "#{api.url}" c телом "#{jsonBody}"
		И и код ответа на запрос равен "200"
		Когда пользователь открыл главную страницу
		И загружена страница "Главная страница"
		И заполняются поля:
			| Логин  | <login>    |
			| Пароль | <password> |
		И выполнено нажатие на Продолжить
		Тогда загружено диалоговое окно "Оповещение об ошибке"
		И значение поля Ошибка содержит Неверно указан логин или пароль
		И выполнено нажатие на Закрыть
		Примеры:
			| file                       | login     | password     |
			| GeneratedUser_active3.json | #{login}* | #{password}  |
			| GeneratedUser_active4.json | #{login}  | #{password}* |
			| GeneratedUser_active5.json | #{login}* | #{password}* |

	@ibank-login-test @negative
	Структура сценария: Авторизация в личном кабинете не происходит при использовании кредитов заблокированного пользователя
		Дано данные пользователя подгружены из файла <file>
		И выполнен POST - запрос по url - "#{api.url}" c телом "#{jsonBody}"
		И и код ответа на запрос равен "200"
		Когда пользователь открыл главную страницу
		И загружена страница "Главная страница"
		И заполняются поля:
			| Логин  | #{login}    |
			| Пароль | #{password} |
		И выполнено нажатие на Продолжить
		Тогда загружено диалоговое окно "Оповещение об ошибке"
		И значение поля Ошибка содержит Пользователь заблокирован
		И выполнено нажатие на Закрыть
		Примеры:
			| file                        |
			| GeneratedUser_blocked0.json |

	@ibank-login-test @negative
	Сценарий: : Авторизация в личном кабинете не происходит при использовании кредитов отсутствующего пользователя
		Когда пользователь открыл главную страницу
		И загружена страница "Главная страница"
		И заполняются поля:
			| Логин  | Barbara1233 |
			| Пароль | password1!  |
		И выполнено нажатие на Продолжить
		Тогда загружено диалоговое окно "Оповещение об ошибке"
		И значение поля Ошибка содержит Неверно указан логин или пароль
		И выполнено нажатие на Закрыть

	@ibank-login-test @negative
	Структура сценария: Авторизация в личном кабинете не происходит при использовании кредитов пользователя с произвольным статусом
		Дано данные пользователя подгружены из файла <file>
		И выполнен POST - запрос по url - "#{api.url}" c телом "#{jsonBody}"
		И и код ответа на запрос равен "500"
		Когда пользователь открыл главную страницу
		И загружена страница "Главная страница"
		И заполняются поля:
			| Логин  | #{login}    |
			| Пароль | #{password} |
		И выполнено нажатие на Продолжить
		Тогда загружено диалоговое окно "Оповещение об ошибке"
		И значение поля Ошибка содержит Неверно указан логин или пароль
		И выполнено нажатие на Закрыть
		Примеры:
			| file                      |
			| GeneratedUser_other0.json |
			| GeneratedUser_other1.json |
			| GeneratedUser_other2.json |
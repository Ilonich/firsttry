Hello world!
1) Для изменения юзера используются диалоговые окна (prompt, например), должны быть разрешены в браузере. После изменения кнопка save changes сохранит изменения в БД.
2) Имя БД - "test", имя и пароль "root", надеюсь не ошибся. В SQLbatch.txt и Datconfig.class они так прописаны.
3) Без XML файлов выходит нагляднее для меня, поэтому использовал Java-based container configuration (http://docs.spring.io/spring/docs/current/spring-framework-reference/html/beans.html#beans-java).
Дальше это привело к тому что и без web.xml и прочих xml захотелось, поэтому использовал Spring MVC и AbstractAnnotationConfigDispatcherServletInitializer.
Выбрал AngularJS (v1.5.7) потому что обычно о нём пишут типа "фрэймворк для создания ОДНОСТРАНИЧНЫХ вэб-приложений". Не решился использовать вторую версию, потому что до получения задания не знал ни html, ни css и javascript.
Так что, чтобы можно было найти больше примеров в гугле, выбрал версию старее (ещё из-за того что на эту версию есть простое для начала обучение на http://campus.codeschool.com/courses/shaping-up-with-angular-js/intro).
4) На сайте regex для имён юзеров(^[\\w+]$) maxlength 25.
5) Главная страница на http://localhost:8080/test/home
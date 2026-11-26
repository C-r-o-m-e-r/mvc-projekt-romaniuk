# AstroDziennik

Projekt zaliczeniowy – Programowanie aplikacji internetowych MVC  
Autor: Orest Romaniuk, nr albumu 164504

## Temat
AstroDziennik jest prostym dziennikiem obserwacji nocnego nieba. Użytkownik zapisuje nazwę obiektu, miejsce oraz rok obserwacji. W części I dane są przechowywane w pamięci uruchomionej aplikacji do czasu jej zatrzymania.

## Struktura repozytorium
- `czesc1-servlet-jsp` – aplikacja Servlet i JSP (Apache Tomcat 10.1)
- `czesc2-spring-boot` – miejsce na część Spring Boot + Thymeleaf
- `docs` – dokumentacja projektu

## Uruchomienie części I
Wymagane oprogramowanie: JDK 21, Maven 3.9 oraz Apache Tomcat 10.1.

```bash
cd czesc1-servlet-jsp
mvn clean package
```

Skopiuj wygenerowany plik `target/my-app.war` do katalogu `webapps/` serwera Tomcat i uruchom serwer (`bin/startup.sh` lub `bin\startup.bat`). Następnie otwórz w przeglądarce adres `http://localhost:8080/my-app/`.

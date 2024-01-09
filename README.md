# ZPO-project
Zaawansowane Programowanie Obiektowe

Projekt 3
Czas oddania: koniec pierwszej sesji

Projekt 3 Lista obecności – aplikacja trójwarstwowa (grupa 3-osobowa)
Korzystając z JavaFX, JPA oraz własnego protokołu (Socket, RMI) zaimplementuj listę obecności.
Student to obiekt, posiadający standardowe pola: imię, nazwisko, numer indeksu.
Student przydzielony jest do grupy. Grupa składa się ze studentów z utworzonej wcześniej listy.
Opracuj listę przypadków użycia
•	Dodaj / usuń studenta
•	Dodaj / usuń grupy
•	Dodaj / usuń studenta z grupy
•	Dodaj termin dla grupy
•	Sprawdź obecności (obecny, spóźniony, nieobecny)
•	Wyświetl dziennik obecności
Opracuj strukturę obiektową, czyli odpowiednie klasy:
•	Student
•	Grupa
•	Termin
•	Obecność
i relacje many-to-one (lub many-to-many) (przykład: http://www.mkyong.com/hibernate/hibernate-one-to-many-relationship-example/).
Baza danych powinna zawierać tabelę grup i tabelę studentów.
Realizacja ćwiczenia
Celem ćwiczenia jest wykonanie aplikacji składającej się z trzech komponentów (osobnych, działających równocześnie aplikacji):
•	klient
•	serwer
•	baza danych
Klient jest interaktywną aplikacją JavaFX. Klient łączy się z serwerem poprzez mechanizm socketów lub RMI i własny protokół, natomiast serwer łączy się z bazą danych poprzez JPA.
Baza danych jest postawiona na MySQL, np. na programie XAMPP. Można też użyć PostreSQL lub SQLite.
W ogólnym założeniu każdy z komponentów może stać na innej maszynie. Ponieważ pracę wykonujemy w zespołach trzyosobowych proszę o zaprezentowanie jej działania w ten sposób, że klient, oraz baza MySQL są na jednej maszynie a serwer na drugiej maszynie. Aby przygotować sprawną prezentację, należy zbadać, czy ruch sieciowy jest przepuszczany i ewentualnie wprowadzić tymczasowe zmiany na zaporze.
Aplikacja serwera – serwer jest aplikacją wielowątkową bazującą na Socketach lub RMI w celu połączenia się z klientem i JPA do połączenia z bazą.
Wymagania dokumentacyjne: 
dobrze zrobiony Javadoc  lub dokumentacja projektowa (UML, PU).

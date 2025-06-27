# Gym Tracker App 🏋️

Aplicația Gym Tracker App este o platformă web care permite utilizatorilor să 
își stabilească obiective de fitness, să își planifice și să urmărească sesiunile 
de antrenamente, să gestioneze exerciții și grupe musculare, totul într-un sistem 
securizat, cu roluri și autentificare.
 
![DB diagram](https://github.com/anamariapanait10/GymTrackerApp/blob/main/db_diagram.jpeg)

### I) Cerinte Proiect MVC

1. Relații între entități de toate tipurile ✔️
2. Toate operațiile CRUD ✔️
3. Folosirea de profile si doua baze de date diferite pentru testare si dev/prod ✔️
- pt testare am folosit H2, iar pt prod/dev am folosit mysql
- doua profile diferite pt test si dev
4. Unit tests/intergation tests ✔️
5. View-uri, formulare, validari, excepții ✔️
6. Loguri, aspecte ✔️
7. Paginare, sortare ✔️
8. Spring Security ✔️
 
### II) Proiect Microservicii
1. Configurarea unitara a microserviciilor ✔️

Proiectul a fost migrat la o arhitectură cu microservicii, folosind `Spring Cloud`. Cele doua microservicii
sunt `User Service` și `Workout Service`, fiecare având propriile baze de date și responsabilități.

2. Comunicarea între microservicii, service discovery ✔️

Comunicare sincrona prin HTTP folosind WebClient și Eureka pentru service discovery.

Eureka: http://localhost:8761

3. Scalabilitate si load balancing ✔️
4. Monitorizare, metrici, logging ✔️

Pentru monitorizare am folosit `Zipkin`.
```
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```
5. Securitate ✔️

Pentru autentificare am folosit Keycloak.

```
docker run -d --name keycloak -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:23.0.7 start-dev
```
6. Rezilienta, servicii disponibile in caz de erori ✔️

In cazul in care user service nu este disponibil cand vrem ca adaugam un workout session va merge pe o metoda de fallback care ia id-ul userului direct din baza de date cu un query sql.

7. Design pattern-uri ✔️
- **API Gateway Pattern** - centralizează gestionarea cererilor către microservicii
- **Service Discovery Pattern** - permite microserviciilor să se descopere reciproc
- **Centralized Configuration Pattern** - gestionează configurațiile aplicației într-un singur loc
- **Circuit Breaker Pattern** - detecteaza cand un serviciu este indisponibil si activeaza fallback logic
- **DTO Pattern** - separa modelul de domeniu de datele trimise clientului

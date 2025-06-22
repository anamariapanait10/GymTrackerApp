# Gym Tracker App

 Migrarea proiectului I la o arhitectura cu micro-servicii.
 
I) Impartire in microservicii

1.  User Service
- Tabele: user, authority, user_authority
- Funcționalitate:
	- Gestionare conturi și autentificare
	- Expunere endpointuri pentru login, register

2. Workout Service
- Tabele: workout, workout_set, workout_session

- Funcționalitate:
	- CRUD pe antrenamente, seturi și sesiuni

3. Exercise Service
- Tabele: exercise, muscle_group, exercise_muscle_group

- Funcționalitate:

	- Gestiune exerciții

4. Goal Service
- Tabel: goal

- Funcționalitate:

	- Gestiune obiective personale ale utilizatorului


### Microservicii Suplimentare:
🔹 Config Server
Centralizează toate configurațiile .yml ale microserviciilor.

🔹 Discovery Server (Eureka)
Permite localizarea dinamică între servicii fără hardcoding de URL-uri.

🔹 API Gateway
Routează toate cererile către servicii

Aplică autentificare și rate limiting





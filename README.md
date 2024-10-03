# mysql-spring-vue

Denne skabelon indeholder en [`compose.yaml`](./compose.yaml) som starter tre services:

- `db`: En MySQL database
- `todo-backend`: En Spring Boot backend med en "to do"-applikation (REST, CRUD)
- `todo-frontend`: En reaktiv Vue.js frontend som kontakter back-end'en

## Kørsel

```
docker compose down
docker compose build
docker compose up -d
```

Man kan også nøjes med at skrive `docker compose up` uden `-d`, hvilket får
alle tre apps til at printe deres output i samme terminal. Det kan være lidt
rodet, men det er en nem måde at stoppe det hele på én gang.

Man kan også udkommentere enten backend, frontend, eller begge, hvis man vil
udvikle på dem og køre en live-server fra ens IDE i stedet. I så fald skal man
starte dem med:

```
# Start backend
./mvnw compile
./mvnw spring-boot:run

# Start frontend
cd frontend
npm install
npm run dev
```

## Overblik

### `db`

- Benytter MariaDB i stedet for MySQL, selvom der ikke er meget forskel
- Får sine sikkerhedsparametre konfigureret vha. miljøvariable i compose.yaml

### `todo-backend`

- Benytter MySQL ved hjælp af JPA
- Har slået CORS/CSRF-sikkerhed fra
- API-baseret (returnerer JSON og ikke HTML)

### todo-frontend

- Benytter `axios` til at lave AJAX-forespørgsler
- 

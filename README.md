1. Build project: `mvn clean install`

2. Run: `java -jar target/cinema-main-0.0.1-SNAPSHOT.jar`

3. REST manipulations:
- Get ADMIN token:
```bash
curl -X "POST" "http://localhost:8085/public/token" \
     -H 'Content-Type: application/json' \
     -d $'{
  "email": "admin@cinema.com",
  "password": "12345"
}'
```

- POST movie:
```bash
curl -X "POST" "http://localhost:8085/private/movie" \
     -H 'Content-Type: application/json' \
     -H 'Authorization: {token}' \
     -d $'{
  "name": "Rambo",
  "director": "Stallone",
  "description": "First Blood"
}'
```
`{token}` - should be changed with real token value

- PUT movie (update description):
```bash
curl -X "PUT" "http://localhost:8085/private/movie/{movie_id}" \
     -H 'Content-Type: application/json' \
     -H 'Authorization: {token}' \
     -d $'{
  "description": "First Blood III"
}'
```
`{token}` - should be changed with real token value
`{movie_id}` - should be changed with real movie id

- GET movies:
```bash
curl "http://localhost:8085/public/movie" \
     -H 'Content-Type: application/json'
```

- DELETE movie:
```bash
curl -X "DELETE" "http://localhost:8085/private/movie/{movie_id}" \
     -H 'Content-Type: application/json' \
     -H 'Authorization: {token}'
```
`{token}` - should be changed with real token value
`{movie_id}` - should be changed with real movie id
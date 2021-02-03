# ALAMI Sharia Testing Product API

### Prerequisites
   - Project : Maven
   - Java : JDK 1.8
   - Database : MySQL or PostgreSQL
   - Framework : Spring Boot
   
## Postman Documentation
```
https://www.getpostman.com/collections/eae70952d07e338338c0
```

## Connection
  - HTTP

## How To Deploy
### Database
#### MySQL
```mysql
CREATE DATABASE testing_product CHARACTER SET utf8 COLLATE utf8_general_ci;
```
#### PostgreSQL
```postgresql
CREATE SCHEMA IF NOT EXISTS testing_product AUTHORIZATION alami;
```


## Linux / Unix
### Local
Build
```sh
$ sh compile.sh
```

Run
```sh
$ sh start.sh
```

Stop
```sh
$ sh stop.sh
```

### Docker

Build
```sh
$ docker build . -t alamisharia-testing-product-api
```

Run
```sh
$ docker run --name alamisharia-testing-product-api -p <port>:8089 alamisharia-testing-product-api
```

Stop
```sh
$ docker stop alamisharia-testing-product-api
$ docker rm alamisharia-testing-product-api
```

Restart
```sh
$ docker restart alamisharia-testing-product-api
```



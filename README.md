# Spring Batch Examples

## How to run

As a storage for Spring Batch JobRepository, this project uses MySQL.  
Also, some examples use other middlewares.

Start them with Docker Compose.

```shell
$ docker-compose up -d
```

All data is stored in `./data`.  
To reset them, simply you can delete it.

```shell
$ rm -rf ./data
```

Then, you can run any examples you want.

```shell
$ ./mvn -pl :ARTIFACT_ID spring-boot:run
```

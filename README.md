# P2P Chat Application
Hi to the sample P2P chat application assignment

This is a standalone SpringBoot Docker project that can spawn its own runtime ontainers to 
present before you a seamless click and run experience

## Requirements

The project requires following prerequisites on the machine
 - `Java 21` or above 
 - `Docker Desktop Engine` `v22`+ , Up and Running

## Configurations
Before the initial run, do check and tune the project properties, mentioned in the section below, 
so that right container and networks are created initially. This includes your
system dependent things like availability of server/db ports, system disk and network firewall
settings.

### 1. Properties - `compose.yaml` 
This is a file present in the project root that is responsible for 
setting up the DB, network and other sidecars(if any) container images.
It is also responsible to pass on right environment variables and secrets for the containerized 
application to run smoothly.

## Build
The project uses embedded *Spring Maven* to compile source code, build/run ***DockerCompose*** (containers)

```cmd
./mvnw spring-boot:run
```

You can also run the application in `debug` mode by following command
```cmd
./mvnw spring-boot:run --debug
```

The first run will automatically create DB container with persistent disk volume
and will also create the database for this application. The same will not happen in subsequent run.
Therefore, in case of any mishap or corruption, carefully teardown the container stack and
simply run the maven build command again.

For more info on how the docker setup works internally with String Boot, please read
[this blog](https://spring.io/blog/2023/06/21/docker-compose-support-in-spring-boot-3-1) post


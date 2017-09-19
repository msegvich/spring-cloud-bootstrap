# Cloud Demo

# Implementations

By default Eureka is being used, but this can be changed by changing the class dependencies in each
project's build.gradle file.

# Projects

This repo contains many projects. A description of each one and the feature they use are included
below. The goal of it is to be an example of how to design your system to slowly remove a legacy
service from use and replace it with a new system.

## new-services/discovery-service

This project is a simple boilerplate Eureka discovery service instance. All it needs is the main
class to run from and some configuration to describe where it should run, etc. Run this if you
aren't running with Consul.  To build:
```
./gradlew build
```

To run:
```
java -jar build/libs/discovery-service-0.0.1-SNAPSHOT.jar
```

## new-services/configuration-service

This project is a simple boilerplate configuration server running off of Spring Cloud Config. All
it needs is to know where the discovery service is to register with it and where to find the
configuration for all the applications that will be running. You can run this with Consul if desired
by only using the spring-cloud-starter-consul-discovery dependency instead of
spring-cloud-starter-consul-all.

To use local configurations (e.g. for testing) for the configuration service, use the native profile.
For native, you'll also need to specify where your configuration lives.

```
spring.cloud.config.server.native.search-locations=${user.home}/Projects/cloud-demo/configuration
```

To build:
```
./gradlew build
```
To run:
```
java -Dspring.profiles.active=native -jar build/libs/configuration-service-0.0.1-SNAPSHOT.jar
```

## legacy-services/legacy\_user\_service

An example of a simple SOAP based CRUD service for handling user details. There is nothing new
about this service, it is just a basic example of a Spring WS app using SOAP.

To build:
```
./gradlew build
```
To run:
```
java -Dserver.port=8060 -jar build/libs/legacy_user_service-0.0.1-SNAPSHOT.jar
```

## new-services/employee-service

This project is the example replacement service of the legacy\_user\_service implemented as a REST
API with Spring. It uses its own database, and when it needs to find existing info that someone
wants, it calls the legacy service so the business logic can remain the same. It's an extension
of the legacy application, in that it also stores who the user's manager is. The application is
designed to run as a microservice on an autoscaling group in AWS or an equivalent infrastructure
on some other cloud infrastructure model. It can also connect to a Vault instance to get config
details like database login information.

This application implements Spring Security, Spring Session, Hystrix, and a basic Spring Cloud
framework design. The main information it really needs is where the discovery service exists, and
from there it can pull all other configuration details it needs from a configuration service also
connected to the discovery service.

To demo how an application can work on both a developer's machine and a real instance, take a look
at the SessionConfig.java and SessionConfigLocal.java details.

In order to build, there is a dependency on the legacy user service in order to generate the client
(JAXB-based).  Therefore, the legacy user service must be running.  To set the port:

```
./gradlew -Plegacy_user_port=8060 build
```

To run:
```
java -Dspring.profiles.active=local -jar build/libs/employee-service-0.0.1-SNAPSHOT.jar
```

## legacy-services/legacy\_adapter

This project is the example adapter for legacy clients to keep working as expected, but without
continuing to use the legacy service directly. This application showcases off various Consul and
Eureka configurations, as well as various REST clients that can be load balanced for a microservice
architecture: Feign and a `@LoadBalanced` RestTemplate.

Picking between the two is done by a configuration value, but could also be done with `@Profile` with
some slight alterations, namely marking each ServiceAdapter implementation with `@Service` and
`@Profile("someName")` and removing the ServiceAdapter `@Bean`.

In order to build, there is a dependency on the legacy user service in order to generate the client
(JAXB-based).  Therefore, the legacy user service must be running.  To set the port:

```
./gradlew -Plegacy_user_port=8060 build
```

To run:
```
java -Dspring.profiles.active=local -jar build/libs/legacy-adapter-0.0.1-SNAPSHOT.jar
```

## new-services/zuul-front and website

The Zuul project is a simple boilerplate API Gateway implementation in Spring. Zuul automatically load
balances for a cloud/microservice architecture, and all that needs to be configured is how to actually
route requests, which can be done in the YAML or Java Properties Spring configurations. This is a webapp
that would sit in the DMZ to pick up all requests to your REST APIs and legacy adapters while they
sit behind it in a private VPC or similar network area.This example acts as a simple passthrough for
information and will pass all headers to the employee-service as the employee-service is what handles
all the security.

To build:
```
./gradlew build
```
To run:
```
java -Dspring.profiles.active=local -jar ./build/libs/zuul-service-0.0.1-SNAPSHOT.jar
```

The website is a simple Angular-based UI that uses Zuul to make calls to the employee service. To
build the website you will need npm installed and run the following:

```
sudo npm install -g grunt -cli
sudo npm install -g bower -cli
npm install
bower install
```
To run the website:

```
grunt bootRun
```
## new-services/service-dashboard

This project is an example of a Hystrix Dashboard with Turbine. All it needs is configuration and
the boilerplate code from Spring.

A default cluster has been created that monitors the legacy adapter and the employee service.  To
see this go to http://localhost:9000/hystrix and enter the following into the form:

```
http://localhost:9000/turbine.stream?cluster=default
```

## new-services/ -discovery-client, -configuration-client, dicovery-demo-\*

These projects are quick little demos of using the configuration-service and discovery-service
projects and aren't related to the POC2 reference architecture.

## batch/update-legacy-db

**In Progress** This project is an example batch program utilizing Spring Batch that would run to push any new
users created in the employee-service back into the legacy database for further batch processing
to happen. Spring Batch allows for stopped jobs to run again without duplicating work that's already
been completed. You can run an example Admin webapp by following the instructions in batch/admin,
which will allow you to stop the running job and when it gets restarted with continue where it left
off.

# Consul

## Local installation

Download and install to your system path the latest version of Consul from [HashiCorp](https://www.consul.io/downloads.html)
for your system. Then setup a development server with the command `consul agent -dev`,
which will create a generic development Consul server for service discovery/etc at
http://localhost:8500. You can then start up your microservices as normal. Learn more
about Consul from [here](https://www.consul.io/intro/getting-started/install.html).

## Integration with Spring Applications

Spring handles almost all of the integrations behind the scenes. The only responsibilies
of the developer is to include the right dependencies and fill out the right configurations
so the Spring application can find your Consul servers. Spring's Consul integration documents
can be found [here](http://cloud.spring.io/spring-cloud-static/spring-cloud-consul/1.2.0.RELEASE/).

### Dependencies

There are a few options when including Consul. If you only want to use it for service
discovery, then just include the following line to your dependencies on your build.gradle.

```
compile('org.springframework.cloud:spring-cloud-starter-consul-discovery')
```

This will allow you to continue to use your own Spring Cloud Config server without any
issue.

If you'd like to use all the services Consul provides, include the following line.

```
compile('org.springframework.cloud:spring-cloud-starter-consul-all')
```

You can also mix and match what you need, but I'll leave that to the Spring documentation
to explain in more detail.

### Configuration

At minimum, you need to tell Spring where to find Consul, as below. Everything else is in
addition to this bare minimum of configuration.

```
spring:
  cloud:
    consul:
      host: localhost
      port: 8500
```

For the discovery service capabilities, you need to include any discovery configuration.
The service doesn't have to register to still access the service discovery capabilities
of Consul. If you set register to false, it will still be able to find services, but others
won't be able to find it. The healthCheck configs are optional, and have reasonable defaults.

```
spring:
  cloud:
    consul:
      ...
      discovery:
        register: true
        healthCheckPath: ${management.context-path}/health
        healthCheckInterval: 15s
```

To get Spring application config from Consul KV, there's some configuration that needs to
be done there. At minimum, you need to tell it what format to expect. You can read more
about what formats are supported and availabe options from [Spring's documentation](http://cloud.spring.io/spring-cloud-static/spring-cloud-consul/1.2.0.RELEASE/). Search
for "Distributed Configuration with Consul".

```
spring:
  cloud:
    consul:
      config:
        format: YAML
```

You can read more about everything Spring supports from Consul from their [documentation](http://cloud.spring.io/spring-cloud-static/spring-cloud-consul/1.2.0.RELEASE/).


## Consul KV

Currently, there's a bash script (and assumed to be working batch script for Windows)
that fills in Consul's KV store with the application configurations. Run do-config.(sh|bat)
to update the data there. It will need to be run at least once upon startup of Consul.

In a production environment, once initial values are put in, the command `consul kv export`
can be run to get a JSON representation of the values (encrypted), that can be later
imported with `consul kv import` for disaster recovery. More details [here](https://www.consul.io/docs/commands/kv/export.html) and [here](https://www.consul.io/docs/commands/kv/import.html).

### git2consul

There's a community driven project written in NodeJS and runnable as a docker image called
git2consul that can be used to update the KV values and used to store Spring application
configurations. Details about git2consul can be found [here](https://www.npmjs.com/package/git2consul), and details about
configuring your application to work with it in Spring can be found [here](http://cloud.spring.io/spring-cloud-static/spring-cloud-consul/1.2.0.RELEASE/#spring-cloud-consul-config) (search for
"git2consul with Config", about 2/3 down the page).

## Gotchas

If you want to use Consul, make sure you're on the Dalston release of Spring Cloud. Camden
is a Netflix release branch, I believe.

# Vault

Vault is a tool developed by Hashicorp for storing static and dynamic secrets.
Downloads for the executable can be found [at Hashicorp's site for Vault](https://www.vaultproject.io/downloads.html).

## Static Secrets

Generally, static secrets can go in the generic "secret" mount, by reading/writing
with `vault read/write secret/hello value="world"`. You can set attributes other
than value, as well, which Spring takes advantage of when you want to store secret
credentials such as database details or product keys, e.g. `vault read/write
secret/hello name.first="John" name.last="Smith"`. Something to note, when you
write, you need to set every attribute each time, or they will be overwritten and
deleted.

You can mount more paths other than secret with the generic storage as well if
desired. Read more about it [here](https://www.vaultproject.io/intro/getting-started/secret-backends.html) and [here](https://www.vaultproject.io/docs/secrets/generic/index.html).

## Dynamic Secrets

Using different backends, you can get dynamic secrets from Vault such as temporary
AWS IAM user credentials. This can allow a system admin to allow an application to
dynamically create a new AWS user as needed that lasts only as long as needed, or
limit usage to AWS for developers without needing to go to the console each time.
The same can be done for database credentials with a little configuration of Vault.
Read more about it [here](https://www.vaultproject.io/docs/secrets/databases/index.html).

## Policies

Vault allows access to secrets through the use of policies, similar to AWS IAM.
When creating a new token, you can give it an inline policy or create a named
policy ahead of time and attach it to the token. Then, that token can only do what
the policy says it can do. One example would be to only allow the EMPLOYEE-SERVICE
application to access "secret/employee-service/\*" for read/write, and nothing else.
Vault by default returns a root token on startup, but that should be treated as a
root password and not given out unless absolutely needed. Best practices would be
to create new admin user tokens, and to stop using the root token afterwards unless
needed, e.g. all other admin tokens were removed.

You can read more about writing policies [here](https://www.vaultproject.io/docs/concepts/policies.html). To create a policy,
run `vault policy-create ...`. Then to create a token with that policy, you can run
`vault token-create -policy="name"`. Include `-no-default-policy` to not include the
default Vault policy.

## Auth Backends

Vault allows users to authenticate in various ways, and you can read more about it
[here](https://www.vaultproject.io/docs/auth/index.html). Spring supports various
authentication backends as well, which you can read about their configurations [here](http://cloud.spring.io/spring-cloud-vault/spring-cloud-vault.html#vault.config.authentication).

## Integration with Spring Applications

Spring includes a package that allows you to easily integrate your application
configuration with pulling secrets from Vault. Simply include the library as a
dependency and add some configuration to your bootstrap.yml file. Configuration
looks like the below:

```
spring:
  cloud:
    vault:
      token: your_token_here
      host: localhost
      port: 8200
      scheme: http #Not recommended for production! Use https!
      connection-timeout: 5000
      read-timeout: 15000
      config:
        order: -10
```

You can read more about it [here](https://github.com/spring-cloud/spring-cloud-vault#client-side-usage)

### Employee Service PoC Application

To run the employee service PoC with Vault, you'll need to get your own Vault instance up
and running and set the Vault token in bootstrap.yml to one valid for your own instance.

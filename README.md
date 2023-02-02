# quarkus-with-postgis

This project is to Demo the challenge with issues #24430 (https://github.com/quarkusio/quarkus/issues/24430)

When using this one needs to run a dockerized version of PostGIS like this:
```shell script
docker network create quarkus-with-postgis
docker run -d --net=quarkus-with-postgis --name postgis -e POSTGRES_PASSWORD=mysecretpassword -e POSTGRES_USERNAME=postgres -p 5432:5432 postgis/postgis:14-master
```

Then Quarkus can be started:
```
./mvnw compile quarkus:dev
```

And finally one can hit the ```/hello``` example resource using a HTTP-client of ones choice. This results in the following stacktrace:
```
2023-02-02 15:05:19,165 INFO  [org.fly.cor.int.dat.bas.BaseDatabaseType] (Quarkus Main Thread) Database: jdbc:postgresql://localhost:5432/postgres (PostgreSQL 14.5)
2023-02-02 15:05:19,187 INFO  [org.fly.cor.int.com.DbValidate] (Quarkus Main Thread) Successfully validated 2 migrations (execution time 00:00.012s)
2023-02-02 15:05:19,206 INFO  [org.fly.cor.int.com.DbMigrate] (Quarkus Main Thread) Current version of schema "public": 2
2023-02-02 15:05:19,207 INFO  [org.fly.cor.int.com.DbMigrate] (Quarkus Main Thread) Schema "public" is up to date. No migration necessary.
2023-02-02 15:05:19,269 INFO  [io.quarkus] (Quarkus Main Thread) quarkus-with-postgis 1.0.0-SNAPSHOT on JVM (powered by Quarkus 2.16.1.Final) started in 1.382s. Listening on: http://localhost:8080
2023-02-02 15:05:19,269 INFO  [io.quarkus] (Quarkus Main Thread) Profile dev activated. Live Coding activated.
2023-02-02 15:05:19,270 INFO  [io.quarkus] (Quarkus Main Thread) Installed features: [agroal, cdi, flyway, jdbc-postgresql, narayana-jta, resteasy, resteasy-jackson, smallrye-context-propagation, vertx]
2023-02-02 15:05:22,881 INFO  [org.acm.GreetingResource] (executor-thread-0) Do: 1:London:(class: class org.postgresql.util.PGobject)0101000020E610000001309E4143C14940CA52EBFD463BBEBF
2023-02-02 15:05:22,886 ERROR [io.qua.ver.htt.run.QuarkusErrorHandler] (executor-thread-0) HTTP Request to /hello failed, error id: 7429cc5d-7785-40c3-b7e3-7d2252fea496-1: org.jboss.resteasy.spi.UnhandledException: java.lang.ClassCastException: class org.postgresql.util.PGobject cannot be cast to class net.postgis.jdbc.PGgeography (org.postgresql.util.PGobject is in unnamed module of loader 'app'; net.postgis.jdbc.PGgeography is in unnamed module of loader io.quarkus.bootstrap.classloading.QuarkusClassLoader @58a90037)
        at org.jboss.resteasy.core.ExceptionHandler.handleApplicationException(ExceptionHandler.java:105)
        at org.jboss.resteasy.core.ExceptionHandler.handleException(ExceptionHandler.java:359)
        at org.jboss.resteasy.core.SynchronousDispatcher.writeException(SynchronousDispatcher.java:218)
        at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:519)
        at org.jboss.resteasy.core.SynchronousDispatcher.lambda$invoke$4(SynchronousDispatcher.java:261)
        at org.jboss.resteasy.core.SynchronousDispatcher.lambda$preprocess$0(SynchronousDispatcher.java:161)
        at org.jboss.resteasy.core.interception.jaxrs.PreMatchContainerRequestContext.filter(PreMatchContainerRequestContext.java:364)
        at org.jboss.resteasy.core.SynchronousDispatcher.preprocess(SynchronousDispatcher.java:164)
        at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:247)
        at io.quarkus.resteasy.runtime.standalone.RequestDispatcher.service(RequestDispatcher.java:82)
        at io.quarkus.resteasy.runtime.standalone.VertxRequestHandler.dispatch(VertxRequestHandler.java:147)
        at io.quarkus.resteasy.runtime.standalone.VertxRequestHandler$1.run(VertxRequestHandler.java:93)
        at io.quarkus.vertx.core.runtime.VertxCoreRecorder$14.runWith(VertxCoreRecorder.java:576)
        at org.jboss.threads.EnhancedQueueExecutor$Task.run(EnhancedQueueExecutor.java:2449)
        at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1478)
        at org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:29)
        at org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:29)
        at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
        at java.base/java.lang.Thread.run(Thread.java:833)
Caused by: java.lang.ClassCastException: class org.postgresql.util.PGobject cannot be cast to class net.postgis.jdbc.PGgeography (org.postgresql.util.PGobject is in unnamed module of loader 'app'; net.postgis.jdbc.PGgeography is in unnamed module of loader io.quarkus.bootstrap.classloading.QuarkusClassLoader @58a90037)
        at org.acme.GreetingResource.dumpGeolocation(GreetingResource.java:59)
        at org.acme.GreetingResource.doSomeJDBC(GreetingResource.java:44)
        at org.acme.GreetingResource.hello(GreetingResource.java:35)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.jboss.resteasy.core.MethodInjectorImpl.invoke(MethodInjectorImpl.java:170)
        at org.jboss.resteasy.core.MethodInjectorImpl.invoke(MethodInjectorImpl.java:130)
        at org.jboss.resteasy.core.ResourceMethodInvoker.internalInvokeOnTarget(ResourceMethodInvoker.java:660)
        at org.jboss.resteasy.core.ResourceMethodInvoker.invokeOnTargetAfterFilter(ResourceMethodInvoker.java:524)
        at org.jboss.resteasy.core.ResourceMethodInvoker.lambda$invokeOnTarget$2(ResourceMethodInvoker.java:474)
        at org.jboss.resteasy.core.interception.jaxrs.PreMatchContainerRequestContext.filter(PreMatchContainerRequestContext.java:364)
        at org.jboss.resteasy.core.ResourceMethodInvoker.invokeOnTarget(ResourceMethodInvoker.java:476)
        at org.jboss.resteasy.core.ResourceMethodInvoker.invoke(ResourceMethodInvoker.java:434)
        at org.jboss.resteasy.core.ResourceMethodInvoker.invoke(ResourceMethodInvoker.java:408)
        at org.jboss.resteasy.core.ResourceMethodInvoker.invoke(ResourceMethodInvoker.java:69)
        at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:492)
        ... 15 more
```

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-with-postgis-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

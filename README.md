# QR Code Generator

[![Build Status](https://travis-ci.org/savitoh/qr-code-generator-backend.svg?branch=master)](https://travis-ci.org/savitoh/qr-code-generator-backend.svg?branch=master)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.savitoh:demo-qr-code&metric=alert_status)](https://sonarcloud.io/dashboard/index/com.savitoh:demo-qr-code)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.savitoh:demo-qr-code&metric=coverage)](https://sonarcloud.io/component_measures?id=com.savitoh:demo-qr-code&metric=coverage)

Projeto de demostração da biblioteca [ZXing] para geração de QR Code.

## Pré Requisitos

- [JDK 11] ou mais recente.
- [Maven 3.5] ou mais recente.

## Tecnologias Utilizadas

- [Java 11]
- [Spring Boot 2.1.7.RELEASE]
- [Lib Zxing]

## Iniciando o projeto

Clone o projeto com o comando:

```
git clone https://github.com/savitoh/qrcode-generator-backend.git
```

Acesse a pasta raiz:

```
cd qrcode-generator-backend
```

### Rodando Localmente

Acesse a pasta com o pom.xml:

```
cd demo-qr-code-api
```

Executando por meio do plugin do Spring Boot para Maven

```
mvn spring-boot:run
```

Executando a partir do .JAR. Rode os comandos abaixo:


```
mvn clean package
```

```
java -jar target/gs-rest-service-0.1.0.jar
```

### Rodando os testes

```
mvn test
```


[ZXing]: <https://opensource.google/projects/zxing>
[Lib ZXing]: <https://opensource.google/projects/zxing>
[Java 11]: <https://www.oracle.com/java/technologies/javase-jdk11-downloads.html>
[Spring Boot 2.1.7.RELEASE]: <https://spring.io/projects/spring-boot>
[JDK 11]: <https://www.oracle.com/java/technologies/javase-downloads.html>
[Maven 3.5]: <https://maven.apache.org/download.cgi>

# QR Code Generator

[![Build Status](https://travis-ci.org/savitoh/qrcode-generator-backend.svg?branch=master)](https://travis-ci.org/savitoh/qrcode-generator-backend.svg?branch=master)
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
- [Docker]

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

Acesse a pasta onde o arquivo pom.xml estar localizado:

```
cd demo-qr-code-api
```

É possivél rodar a apliçação local por três modos.

1. Executando por meio do plugin do Spring Boot para o Maven

```
    mvn spring-boot:run
```

2. Executando a partir do .JAR empacotado. Rode os comandos abaixo:

```
   mvn clean package  - (Empacota a APP em um .jar)

   java -jar target/demo-qr-code.0.0.1-SNAPSHOT.jar  - (Comando Java para rodar o .jar criado na linha acima)
```

3. Executando com Docker

```
   mvn clean package  - (Empacota a APP em um .jar)  
  
   docker build -t qr-code-api .  (Cria a imagem a partir do Dockerfile)

   docker run --rm -d  -p 8081:8081/tcp qr-code-api  (Sobe o container da imagem criada na linha acima)
```

### Rodando os testes

Acesse a pasta onde o arquivo pom.xml estar localizado:

```
cd demo-qr-code-api
```

1. Execute o comando maven abaixo:

```
   mvn test
```

[ZXing]: <https://opensource.google/projects/zxing>
[Lib ZXing]: <https://opensource.google/projects/zxing>
[Java 11]: <https://www.oracle.com/java/technologies/javase-jdk11-downloads.html>
[Spring Boot 2.1.7.RELEASE]: <https://spring.io/projects/spring-boot>
[JDK 11]: <https://www.oracle.com/java/technologies/javase-downloads.html>
[Maven 3.5]: <https://maven.apache.org/download.cgi>
[Docker]: <https://www.docker.com/>

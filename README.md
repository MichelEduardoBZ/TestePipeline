# Projeto Cidades Inteligentes - Gerenciamento de Resíduos Urbanos

Este projeto tem como objetivo otimizar a gestão de resíduos urbanos através de um sistema automatizado de monitoramento, desenvolvido com Java Spring Boot. Ele foi projetado para ser facilmente escalável, utilizando Docker para containerização e GitHub Actions para CI/CD automatizado.

### Tecnologias Utilizadas

    Java 17 (OpenJDK)
    Spring Boot
    MySQL
    Docker e Docker Compose
    GitHub Actions para CI/CD

Antes de rodar o projeto, certifique-se de que seu ambiente atenda aos seguintes requisitos:

    Java 17 ou superior
    Docker
    Git

### Estrutura do Projeto

    Java Spring Boot: Aplicação principal desenvolvida com Spring Boot.
    MySQL: Banco de dados relacional utilizado para armazenar dados da aplicação.
    Docker Compose: Gerencia a orquestração da aplicação e do banco de dados.
    CI/CD Pipeline: Automatização do processo de build e deploy via GitHub Actions.

### Executando o Projeto
#### Passo 1: Clonando o repositório

```bash
git clone https://github.com/MichelEduardoBZ/TestePipeline.git
cd seu-repositorio
```


#### Passo 2: Configuração com Docker Compose

O projeto já está configurado para rodar com Docker Compose. Para iniciar a aplicação e o banco de dados, execute o comando abaixo na raiz do projeto:

```bash
docker-compose up --build
```

Isso irá:

    Iniciar um contêiner MySQL na porta 3307 (mapeada para a porta 3306 do contêiner).
    Construir e iniciar o contêiner do aplicativo Spring Boot na porta 8080.

#### Passo 3: Verificando a execução

Após iniciar o projeto, você pode verificar se a aplicação está rodando acessando a URL: `http://localhost:8080`

A API estará disponível nessa URL para testar as funcionalidades do sistema de monitoramento de resíduos.
Pipeline CI/CD

A pipeline de CI/CD está configurada para ser executada no GitHub Actions a cada push na branch main. Ela inclui as seguintes etapas:

    Checkout do Código: Baixa o código do repositório.
    Configuração do JDK: Define a versão do JDK 17 para build.
    Build do Projeto: Executa o Maven para compilar o código.
    Build da Imagem Docker: Gera uma imagem Docker para o aplicativo Spring Boot.
    Deploy: Configurado para ser realizado em dois ambientes: staging e produção.

Arquivo `build-and-deploy.yml`

Aqui está uma visão geral do arquivo build-and-deploy.yml:

```yaml

name: Pipeline

on:
push:
branches: [ "main" ]
pull_request:
branches: [ "main" ]

jobs:
job-hello:
runs-on: ubuntu-latest
steps:
- name: Imprimir mensagem
run: echo "Iniciando serviço"

checkout_java_docker:
runs-on: ubuntu-latest
steps:
- name: Git Checkout
uses: actions/checkout@v4
```

### Dockerfile

O Dockerfile utilizado para criar a imagem Docker do aplicativo Spring Boot:

```dockerfile
FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```
### Variáveis de Ambiente | Acesso ao Banco de Dados e Autenticação

O banco de dados roda em um contêiner Docker separado, a URL usada é jdbc:mysql://db:3306/lixo, onde:

```SQL
MYSQL_ROOT_PASSWORD: nico1804
MYSQL_DATABASE: lixo
```
Acesso à Aplicação via Spring Security

```java
spring.security.user.name=michel
spring.security.user.password=123
```

Como Contribuir

Se você deseja contribuir com este projeto, siga os passos abaixo:

    Fork este repositório.
    Crie uma branch com sua nova feature: git checkout -b minha-feature.
    Faça o commit das suas mudanças: git commit -m 'Adicionando nova feature'.
    Envie suas mudanças para o repositório: git push origin minha-feature.
    Crie um novo Pull Request.
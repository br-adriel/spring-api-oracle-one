# VollMed API

API REST para gestão de médicos, pacientes e agendamentos de consulta, com
autenticação via tokens JWT.

O endpoint `/swagger-ui/index.html` contém informações sobre todos os outros
endpoints disponíveis.

Projeto desenvolvido durante o acompanhamento dos cursos "Spring Boot 3:
desenvolva uma API Rest em Java", "Spring Boot 3: aplique boas práticas e
proteja uma API Rest" e "Spring Boot 3: documente, teste e prepare uma API para
o deploy" da plataforma Alura durante o programa educacional Oracle One.

![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

## Executando o projeto

1. Faça o download do repositório e abra a pasta do projeto na IDE de sua
   preferência.

2. Crie um banco de dados postgres e insira a url de acesso, usuário e senha nos
campos correspondentes de um dos arquivos a seguir, conforme o seu caso:

   - Banco de dados de desenvolvimento:
     - `src\main\resources\application.properties`
   
   - Banco de dados para testes:
     - `src\main\resources\application-test.properties`
   
   - Banco de dados de produção:
     - Não precisa adicionar os dados em um arquivo `.properties`.

3. Siga a seguir a subseção adequada ao seu caso.

### Desenvolvimento

1. Execute o comando a seguir para instalar as dependências do projeto:

    ```bash
    mvn clean install
    ```

2. Compile e execute o projeto

### Produção

1. Compile o projeto para um arquivo `.jar` com o comando a seguir:

    ```bash
    mvn clean package
    ```

2. Inicie o servidor de produção, substitua os valores entre colchetes pelas
informações de conexão correspondentes do seu banco de dados:

    - Linux bash:
   
        ```bash
        java -Dspring.profiles.active=prod -DDATASOURCE_URL="[URL]" -DDATASOURCE_USERNAME=[USUARIO] -DDATASOURCE_PASSWORD=[SENHA] -jar ./target/api-0.0.1-SNAPSHOT.jar
        ```
   
    - Windows powershell:
   
        ```powershell
        java "-Dspring.profiles.active=prod" "-DDATASOURCE_URL=[URL]" "-DDATASOURCE_USERNAME=[USUARIO]" "-DDATASOURCE_PASSWORD=[SENHA]" -jar .\target\api-0.0.1-SNAPSHOT.jar
        ```

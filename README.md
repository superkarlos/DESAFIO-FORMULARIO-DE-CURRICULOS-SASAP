# Formulário de Envio de Currículos

Este é um projeto de exemplo para um formulário de envio de currículos usando Java Spring Boot. O formulário permite que os usuários enviem informações pessoais e anexem um currículo, que é então armazenado em um banco de dados e enviado por e-mail.

## Funcionalidades

### Formulário para envio de currículos com os seguintes campos:

- Nome
- E-mail
- Telefone
- Cargo Desejado (Campo de texto livre)
- Escolaridade (Campo de seleção)
- Observações
- Arquivo (para envio do currículo)

Observações:
- Apenas o campo "Observações" é opcional.
- Apenas arquivos com as extensões .doc, .docx ou .pdf são aceitos.
- Tamanho máximo do arquivo: 1MB.

### Feedback Visual:

Após enviar, espere a resposta do formulário (em média, leva 10 segundos para ser enviado).

![Formulário](https://github.com/superkarlos/DESAFIO-FORMULARIO-DE-CURRICULOS-SASAP/assets/50372440/5abae612-89e6-4f1e-8076-3832dd311308)

Caso tudo tenha dado certo, você verá esta página:

![Sucesso](https://github.com/superkarlos/DESAFIO-FORMULARIO-DE-CURRICULOS-SASAP/assets/50372440/98e22abf-a45b-40ef-ba47-05416e7ae8c1)

Verifique seu e-mail cadastrado no formulário. Você receberá um e-mail de `sistemaenvioemail08@gmail.com` com suas informações:

![E-mail](https://github.com/superkarlos/DESAFIO-FORMULARIO-DE-CURRICULOS-SASAP/assets/50372440/2a325471-8956-43f7-8967-6db1bd8794ca)

Caso tenha ocorrido algum erro, você verá esta página:

![Erro](https://github.com/superkarlos/DESAFIO-FORMULARIO-DE-CURRICULOS-SASAP/assets/50372440/66068f27-7de3-48d0-8a6b-ae67e901159e)


## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.3.1
  - Spring MVC
  - Spring Data JPA
  - Spring Mail
  - Spring Validation
- Thymeleaf
- Bootstrap
- MySQL
- H2 Database (para testes)
- Maven

## Requisitos para Execução

- JDK 21 ou superior
- Maven
- MySQL (para produção)

## Instalação

1. Clone o repositório:
    ```sh
    git clone https://github.com/superkarlos/DESAFIO-FORMULARIO-DE-CURRICULOS-SASAP.git
    ```

2. Navegue até o diretório do projeto:
    ```sh
    cd DESAFIO-FORMULARIO-DE-CURRICULOS-SASAP
    ```

3. Configure o banco de dados MySQL no arquivo `application.properties`, localizado em `DESAFIO-FORMULARIO-DE-CURRICULOS-SASAP/blob/main/src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/seubancodedados    # Lembre-se de criar um banco com esse nome
    spring.datasource.username=seuusuario                                # Lembre-se, este é o seu usuário do MySQL
    spring.datasource.password=suasenha                                  # Lembre-se, esta é a sua senha do MySQL
    spring.jpa.hibernate.ddl-auto=update
    ```

    Caso não tenha MySQL, sinta-se à vontade para usar o H2 Database. Ele só guarda informações até o fim da aplicação. Para usar, apenas descomente as linhas referentes a ele e comente as linhas do MySQL, conforme mostrado na imagem:

    ![H2 Database](https://github.com/superkarlos/DESAFIO-FORMULARIO-DE-CURRICULOS-SASAP/assets/50372440/443520c5-3411-4cd7-b330-a3e0007e9f78)

4. Compile e execute o projeto:
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

5. Abra seu navegador e acesse:
    ```sh
    http://localhost:8080/
    ```

## Armazenamento dos Dados no Banco de Dados

### Usando MySQL Workbench:

1. Abra o MySQL Workbench.
2. Clique em "Local instance MySQL" para se conectar ao seu servidor MySQL local.
3. Na aba esquerda, expanda a seção "Schemas" para visualizar os bancos de dados disponíveis.
4. Encontre e clique no banco de dados que você criou (por exemplo, `seubancodedados`).
5. Você verá duas tabelas: `curriculo` e `email_model`.
6. Selecione uma nova query e digite:
    ```sql
    SELECT * FROM curriculo;
    ```
   para visualizar as informações do currículo no banco de dados.

![Banco de Dados](https://github.com/superkarlos/DESAFIO-FORMULARIO-DE-CURRICULOS-SASAP/assets/50372440/0b2c966e-44e2-40b3-ac8b-c30438d33c31)

### Envio de e-mail com os dados do formulário e o arquivo anexado:

6.1 Selecione uma nova query e digite:
    ```sql
    SELECT * FROM email_model;
    ```

![Status](https://github.com/superkarlos/DESAFIO-FORMULARIO-DE-CURRICULOS-SASAP/assets/50372440/da5b9a3e-8e4a-4a70-a335-2dc4dabec054)

- O status mostra se houve falha ou se foi enviado com sucesso.


### Usando o terminal MySQL:

1. Conecte-se ao MySQL:
    ```sh
    mysql -u seuusuario -p
    ```
2. Crie o banco de dados:
    ```sql
    CREATE DATABASE seubancodedados;
    ```
3. Use o banco de dados:
    ```sql
    USE seubancodedados;
    ```


## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

# To-Do List API (In Progress..)

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgresql](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Junit](https://img.shields.io/badge/testing%20library-323330?style=for-the-badge&logo=testing-library&logoColor=red)

## End points
> **Swagger:** o projeto possui integração com o Swagger através da biblioteca ``SpringDoc``, após clonar o projeto para acessar insira no seu navegador:
```
http://localhost:8080/swagger-ui/index.html#/
```
> **Postman:** o projeto possui um arquivo JSON com todos os endpoints para postman nomeado "to-do-list-API.postman_collection.json" 😉

## Overview
Esta aplicação é uma API Rest dedicada à gestão de tarefas. Ela oferece ao usuário a possibilidade de se organizar de maneira mais eficiente e aumentar sua produtividade. Uma de suas principais funcionalidades é a definição de prioridades para as tarefas por meio de tags de prioridade.

Além disso, a aplicação conta com um sistema de cadastro de usuários que utiliza autenticação JWT. Os endpoints são restritos com base no papel do usuário, seja ele um usuário comum ou administrador.

O projeto está em andamento e pode ser usado como base para o desenvolvimento de uma aplicação completa de lista de tarefas.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.2.1:** <br>
``
Starter Web | Starter Validation | Starter Data JPA
Starter Test | Starter Security
``
- **Spring Security Test 6.2.1**
- **SpringDoc 2.3.0**
- **PostgreSQL 46.2.0**
- **JUnit 4.13.2**
- **JWT 4.4.0**

## Funcionalidades

### Tasks
- Criação
- Recuperação
- Atualizaçao
- Remoção

### Pesquisas por filtros
- Todas
- Por Titulo
- Pela data de Criação

### Tags de Prioridade (a implementar)
- Alta
- Média
- Baixa

## API Endpoints
A API possui os seguintes endpoints:

**Task**
```markdown
- Create a new product           - POST /api/v1/tasks
- Retrieve all tasks             - GET /api/v1/tasks
- Retrieve task by creation date - GET /api/v1/tasks/creation_date?q=2024-03-01
- Retrieve task by title         - GET /api/v1/tasks/title?q=Uma%20task
- Updates a task                 - PUT /api/v1/tasks/{id}
- Delete a task                  - DELETE /api/v1/tasks/{id}
```

**User**
```markdown
- Create an account           - POST /api/v1/user/auth/register
- Log in to an account        - POST /api/v1/user/auth/login
```

**Admin**
```markdown
- Log in to an account           - POST /api/v1/admin/auth/login
- Retrieve all users             - GET /api/v1/admin/users
- Retrieve an user by id         - GET /api/v1/admin/user/{id}
- Retrieve all admins            - GET /api/v1/admin/admins
```

**Root**
```markdown
- Log in to the Root account     - POST   /api/v1/root-admin/auth/login
- Create a Root user             - POST   /api/v1/root-admin/auth/register
- Create a admin account         - POST   /api/v1/root-admin/auth/admin-register
- Delete a admin account         - DELETE /api/v1/root-admin/admin/{adminId}
```

### Exemplos de requisições (Entrada e Saída)

**BODY (INPUT) - CREATE / PUT**
```json
{
  "title": "Uma Task",
  "description": "Uma descrição",
  "conclusionDate": "2024-01-31"
}
```
**BODY (OUTPUT)**
```json
{
  "id": "c2bb4cd2-2a75-4fcf-8f34-bbe96681534e",
  "title": "Uma Task",
  "description": "Uma descrição",
  "creationDate": "2024-01-01",
  "conclusionDate": "2024-01-31",
  "lastUpdate": "2024-01-23"
},
```

# Banco de dados
1. **Instale o PostgreSQL**:
   - Certifique-se de que o PostgreSQL está instalado na sua máquina.

2. **Crie um Banco de Dados**:
   - Abra o terminal ou o prompt de comando.
   - Acesse o psql, a interface de linha de comando do PostgreSQL.
   - Execute o comando abaixo para criar um banco de dados chamado `todolist_api`:

     ```sh
     createdb todolist_api
     ```

   - Alternativamente, você pode usar o psql diretamente para criar o banco de dados:

     ```sh
     psql -U postgres -c "CREATE DATABASE todolist_api;"
     ```

   - Certifique-se de que o usuário PostgreSQL (por padrão, `postgres`) tem as permissões necessárias para criar bancos de dados.

3. **Configurar as Variáveis de Ambiente**:
   - Configure as variáveis de ambiente do seu sistema operacional. Você pode fazer isso adicionando as seguintes linhas ao arquivo de configuração de ambiente do seu sistema:

     **No Linux/MacOS**:
     ```sh
     export DB_NAME=todolist_api
     export POSTGRES_DB_USER=seu_usuario
     export POSTGRES_DB_PASSWORD=sua_senha
     ```

     **No Windows**:
     ```bat
     setx DB_NAME todolist_api
     setx POSTGRES_DB_USER=seu_usuario
     setx POSTGRES_DB_PASSWORD=sua_senha
     ```

4. **Modificar o arquivo application.properties**:
   - Você também pode configurar o banco de dados no arquivo `application.properties` do Spring Boot. Abra o arquivo `src/main/resources/application.properties` e altere as seguintes linhas:

   **application.properties**
   ```properties
   spring.datasource.username=${DB_USER}
   spring.datasource.password=${DB_PASSWORD}
   ```
   Altere o "${DB_USER}" e "${DB_PASSWORD}" pelo seu usuário e senha respectivamente.

# Sobre o user
O user é o usuário comum, ele possui apenas permissões relacionadas a suas tarefas, sendo elas:
+ Criar tarefas
+ Consultar tarefas
+ Atualizar tarefas
+ Excluir tarefas

# Sobre o admin
+ Não existe limitação do número de contas com esse cargo.

O administrador possui como permissões: 
+ Acessar e excluir usuários comuns da aplicação (usuários que não possuem permissões administrativas)
+ Consultar usuários comuns e usuários administradores

# Sobre o Admin-Root
> **Importante**: Guarde bem a senha e o login escolhidos para o Root pois não poderá ser recuperado através do sistema (o banco de dados guarda a senha encriptada).

O Root será único na aplicação e é o cargo com todas as permissões no sistema

A ideia é que o Root seja de acesso interno do sistema e seja de acesso restrito, apenas para pessoas de confiança

### O admin-root pode ser criado através do endpoint POST:
```
/api/v1/root-admin/auth/admin-register
```
Nele é necessário passar um JSON com a seguinte estrutura:
```json
{
    "login": "LoginDoRoot",
    "password": "senhaDoRoot"
}
```
+ A senha escolhida será encriptada assim como todo usuário, portanto a segurança da senha é feita pela aplicação, não se preocupe com a persistência da senha ``apenas em guardar com segurança a senha e o login escolhido.``
+ O login do admin root também possui autenticação via JWT

O Root possui como permissões:

+ Único a possuir permissão de criar e excluir usuários administradores.
+ Todas as permissões dos cargos abaixo dele.
+ Consultar tarefas de todos os usuários existentes na aplicação (ainda não implementado)

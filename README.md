# To-Do List API (In Progress..)

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgresql](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Junit](https://img.shields.io/badge/testing%20library-323330?style=for-the-badge&logo=testing-library&logoColor=red)

## Overview
Esta aplicação é uma API de tarefas que permite ao usuário organizar suas atividades diárias.

O projeto está em andamento e pode ser usado como base para o desenvolvimento de uma aplicação completa de lista de tarefas.


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

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Boot Starter Validation**
- **Spring Data JPA**
- **PostgreSQL**
- **JUnit**

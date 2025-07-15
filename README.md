# 🏡 Backend - Sistema de Gestão de Condomínios

Este é um projeto backend desenvolvido com **Spring Boot** para gerenciar um sistema de administração de **condomínios**. O sistema permite o cadastro de **proprietários**, **moradores**, **imóveis**, **contratos**, **agendamentos** e **serviços**, com um sistema de autenticação e permissões baseadas nos tipos de usuário.

## 📁 Estrutura do Projeto

backend-meu-condominio/
├── src/main/java/com/api/app/
│ ├── config/                  # Configurações (CORS, etc)
│ ├── controllers/             # Controladores REST
│ ├── dtos/                    # Objetos de transferência de dados
│ ├── models/                  # Entidades JPA
│ │ └── enums/                 # Enumerações (Tipo de usuário, Status de Agendamento, etc)
│ ├── repositories/            # Interfaces JPA Repository
│ └── AppApplication.java     # Classe principal do Spring Boot
├── pom.xml                    # Configuração Maven
└── .mvn/                      # Wrapper Maven

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security (com autenticação JWT)
- PostgreSQL (ou outro banco de dados configurado)
- Maven

## 🔐 Funcionalidades

- **Autenticação com JWT**: Login e gerenciamento de usuários com tokens.
- **CRUD completo de usuários**: Cadastro e gerenciamento de **proprietários** e **moradores**.
- **Cadastro e gerenciamento de **imóveis** e **contratos**.
- **Cadastro e gerenciamento de **agendamentos**.
- **Enumerações para status** e **tipos** (ex: status de agendamento).
- **Permissões baseadas no tipo de usuário**: Diferenciação entre **proprietário** e **morador**.
- **CORS configurado** para permitir requisições de diferentes domínios.

## 📦 Endpoints Principais

### Autenticação
- `POST /auth/login` - Realiza login e retorna o token JWT.

### Usuários
- `GET /usuario/{id}` - Buscar usuário por ID.
- `POST /usuario` - Criar novo usuário.

### Proprietário
- `GET /proprietario/me` - Dados do proprietário logado.

### Morador
- `GET /morador` - Lista moradores vinculados ao condomínio.

### Imóvel
- `POST /imovel` - Cadastro de imóvel.
- `GET /imovel` - Listagem de imóveis.
- `PUT /imovel/{id}` - Atualiza informações do imóvel.
- `DELETE /imovel/{id}` - Deleta imóvel.

### Agendamento
- `POST /agendamento` - Criar agendamento.
- `GET /agendamento` - Listar agendamentos.
- `PUT /agendamento/{id}` - Atualizar agendamento.
- `DELETE /agendamento/{id}` - Deletar agendamento.

## 📌 Como Executar

### Pré-requisitos
- **Java 17+**
- **Maven 3.8+**
- **PostgreSQL** ou outro banco de dados configurado

### Passos

```bash
# Clone o projeto
git clone https://github.com/seuusuario/backend-meu-condominio.git
cd backend-meu-condominio

# Compile o projeto
./mvnw clean install

# Execute o projeto
./mvnw spring-boot:run

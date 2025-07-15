🏡 Backend - Sistema de Gestão de Condomínios
Este é o projeto backend desenvolvido com Spring Boot para gerenciar um sistema de administração de condomínios. O sistema permite o cadastro de proprietários, moradores, imóveis, contratos e agendamentos, com um sistema de autenticação e permissões baseadas nos tipos de usuário.

📁 Estrutura do Projeto
pgsql
Copiar
backend-meu-condominio/  
├── src/main/java/com/api/app/  
│   ├── config/                  # Configurações (CORS, etc)
│   ├── controllers/             # Controladores REST
│   ├── dtos/                    # Objetos de transferência de dados
│   ├── models/                  # Entidades JPA
│   │   └── enums/               # Enumerações (Tipo de usuário, Status de agendamento)
│   ├── repositories/            # Interfaces JPA Repository
│   └── AppApplication.java     # Classe principal do Spring Boot
├── pom.xml                      # Configuração do Maven
└── .mvn/                        # Wrapper do Maven
🚀 Tecnologias Utilizadas
Java 17

Spring Boot

Spring Data JPA

Spring Security (com autenticação JWT)

PostgreSQL (ou outro banco configurado)

Maven

🔐 Funcionalidades
Autenticação com JWT: Sistema de login seguro com token.

CRUD de usuários: Cadastro e gerenciamento de proprietários e moradores.

Cadastro e gerenciamento de imóveis.

Cadastro e gerenciamento de contratos.

Cadastro e gerenciamento de agendamentos.

Permissões baseadas no tipo de usuário: Permissões diferenciadas entre proprietário e morador.

CORS configurado para permitir requisições de diferentes domínios.

📦 Endpoints Principais
Autenticação
POST /auth/login: Realiza login e retorna o token JWT.

Usuários
GET /usuario/{id}: Busca usuário por ID.

POST /usuario: Cria novo usuário.

Proprietário
GET /proprietario/me: Retorna os dados do proprietário logado.

Morador
GET /morador: Lista moradores vinculados.

Agendamento
POST /agendamento: Cria um novo agendamento.

GET /agendamento: Lista os agendamentos existentes.

PUT /agendamento/{id}: Atualiza um agendamento existente.

DELETE /agendamento/{id}: Deleta um agendamento.

📌 Como Executar
Pré-requisitos
Java 17+

Maven 3.8+

PostgreSQL ou outro banco de dados configurado

Passos
Clone o projeto:

bash
Copiar
git clone https://github.com/seuusuario/backend-meu-condominio.git
cd backend-meu-condominio
Instale as dependências:

Se você não tiver o Maven instalado, utilize o wrapper Maven incluído no projeto:

bash
Copiar
./mvnw clean install
Configure o banco de dados:

Certifique-se de configurar seu banco de dados (PostgreSQL ou outro) no arquivo src/main/resources/application.properties. Abaixo está um exemplo de configuração para PostgreSQL:

properties
Copiar
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
Execute o projeto:

Após a instalação e configuração, execute o projeto:

bash
Copiar
./mvnw spring-boot:run
Isso iniciará a aplicação, que estará disponível em http://localhost:8080 (ou outro endereço configurado).

🧪 Como Executar os Testes
Testes Unitários e de Integração
Rodar todos os testes com Maven:

Para executar todos os testes, use o comando do Maven:

bash
Copiar
mvn test
Rodar um teste específico:

Para rodar uma classe de teste específica:

bash
Copiar
mvn -Dtest=NomeDaClasseDeTeste test

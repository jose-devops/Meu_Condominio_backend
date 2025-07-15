# 🏡 Backend - Sistema de Gestão de Condomínios

Este é um projeto backend desenvolvido com **Spring Boot** para gerenciar um sistema de administração de **condomínios**. O sistema permite o cadastro de **proprietários**, **moradores**, **imóveis**, **contratos**, **agendamentos** e **serviços**, com um sistema de autenticação e permissões baseadas nos tipos de usuário.

## 📁 Estrutura do Projeto

```
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
```

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

### **Autenticação**
- `POST /auth/login` - Realiza login e retorna o token JWT.

### **Usuários**
- `GET /usuario/{id}` - Buscar usuário por ID.
- `POST /usuario` - Criar novo usuário.

### **Proprietário**
- `GET /proprietario/me` - Dados do proprietário logado.

### **Morador**
- `GET /morador` - Lista moradores vinculados ao condomínio.

### **Imóvel**
- `POST /imovel` - Cadastro de imóvel.
- `GET /imovel` - Listagem de imóveis.
- `PUT /imovel/{id}` - Atualiza informações do imóvel.
- `DELETE /imovel/{id}` - Deleta imóvel.

### **Agendamento**
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
```

# 🧪 Como Executar os Testes

Este guia explica como executar os testes unitários do seu projeto passo a passo, usando o **Maven**.

### Pré-requisitos

Antes de executar os testes, certifique-se de que os seguintes pré-requisitos estão configurados:

- **Java 17 ou superior**
- **Maven 3.8 ou superior**
- **Dependências do projeto instaladas** (Execute o comando `./mvnw clean install` para garantir que todas as dependências necessárias sejam baixadas).

## Passo 1: Acessar o diretório do projeto

Certifique-se de estar no diretório do projeto onde o arquivo `pom.xml` está localizado. Se não tiver feito isso ainda, navegue até o diretório do projeto:

```bash
cd /caminho/para/seu/projeto/backend-meu-condominio
```

## Passo 2: Executar todos os testes unitários

Para rodar todos os testes unitários do projeto, utilize o comando Maven:

```bash
./mvnw test
```

Esse comando irá executar todos os testes configurados no projeto. O Maven irá compilar o código e, em seguida, executar os testes definidos no diretório `src/test/java`. O resultado será mostrado no terminal.

### O que esperar:
- O Maven irá exibir no terminal o progresso dos testes.
- Caso todos os testes passem, você verá uma mensagem como **BUILD SUCCESS**.
- Se algum teste falhar, o Maven irá exibir detalhes sobre o erro, incluindo a falha do teste e o motivo.

## Passo 3: Rodar testes de uma classe específica

Se você deseja rodar apenas uma classe de teste específica, use o comando abaixo, substituindo `NomeDaClasseDeTeste` pelo nome da classe que você quer executar:

```bash
./mvnw -Dtest=NomeDaClasseDeTeste test
```

Exemplo:

```bash
./mvnw -Dtest=AgendamentoServiceTest test
```

Isso executará apenas os testes da classe `AgendamentoServiceTest`.

## Passo 4: Gerar um relatório dos testes

Após rodar os testes, você pode gerar um relatório detalhado dos resultados. Para isso, execute o seguinte comando:

```bash
./mvnw surefire-report:report
```

Este comando gerará um relatório em HTML, que estará localizado na pasta `target/site`. Você pode abrir o arquivo gerado no navegador para visualizar os detalhes dos testes executados.

## Passo 5: Verificar a cobertura de código (Opcional)

Se você configurou o **Jacoco** (ou outro plugin de cobertura de código), pode rodar o comando abaixo para verificar a cobertura de código:

```bash
./mvnw clean verify
```

Esse comando irá executar os testes e gerar um relatório de cobertura. A cobertura de código ajudará a entender quais partes do código estão sendo testadas e quais não estão.

## Dicas Importantes

- **Erros Comuns**: Se os testes falharem, verifique as mensagens de erro para entender o que precisa ser corrigido. O Maven geralmente fornece informações úteis para depuração.
- **Testes Específicos**: Você pode também rodar métodos específicos dentro de uma classe de teste, mas isso pode exigir configurações adicionais dependendo do seu ambiente.

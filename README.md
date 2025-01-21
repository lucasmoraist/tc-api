# TC SOBEI

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

## Descrição
O backend do site institucional do Telecentro da SOBEI foi projetado para gerenciar operações relacionadas aos cursos e inscrições, além de fornecer autenticação para controle de acesso. Esta solução também é responsável por enviar e-mails de confirmação para os alunos inscritos.

## Funcionalidades
1. **Cursos**:
   - CRUD (Create, Read, Update, Delete) para cursos.
2. **Inscrições**:
   - Gerenciamento de inscrições realizadas pelos usuários.
3. **Autenticação e Controle de Acesso**:
   - Autenticação por JWT (JSON Web Token).
   - Controle de acesso para usuários e agentes do Telecentro.
4. **Envio de E-mail**:
   - E-mails de confirmação para alunos que se inscrevem em cursos.
5. **Documentação**:
   - Swagger para exploração e teste dos endpoints.

## Estrutura de Diretórios
```
tc-api/
├── .github/                    # Configurações do GitHub
├── .mvn/                       # Configurações do Maven Wrapper
├── collections/                # Coleções adicionais
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.telecentro.api/
│   │   │       ├── controller/   # Controladores da aplicação
│   │   │       ├── domain/       # Modelos e lógica de domínio
│   │   │       ├── infra/        # Configurações de infraestrutura
│   │   │       ├── repository/   # Classes de repositório
│   │   │       ├── service/      # Serviços de negócio
│   │   │       ├── validations/  # Classes de validação
│   │   │       └── ApiApplication # Classe principal da aplicação
│   │   ├── resources/            # Arquivos de configuração e recursos
│   │   │   ├── application.properties        # Configuração padrão
│   │   │   ├── application-dev.properties    # Configuração para desenvolvimento
│   │   │   └── application-prod.properties   # Configuração para produção
│   └── test/                     # Testes da aplicação
├── .env                         # Arquivo de variáveis de ambiente
├── .env.example                 # Exemplo de variáveis de ambiente
├── compose.yml                  # Configuração do Docker Compose
├── Dockerfile                   # Configuração para o Docker
├── mvnw                         # Script do Maven Wrapper
└── pom.xml                      # Arquivo de configuração do Maven
```

## Tecnologias Utilizadas

### Frameworks e Ferramentas
- **Spring Boot**: Framework principal para o backend.
- **Spring Security**: Implementação de autenticação e controle de acesso.
- **Spring Data JPA**: Interação com o banco de dados.
- **PostgreSQL**: Banco de dados relacional para armazenamento de informações.
- **Spring Cache**: Armazenamento temporário de dados solicitados com frequência.
- **Java Mail Sender**: Para envio de e-mails.

### Dependências
- **Lombok**: Redução de boilerplate no código.
- **Slf4j**: Logs estruturados.
- **OpenAPI**: Documentação dos endpoints com Swagger.
- **Validation**: Validação de dados de entrada nas requisições.

## Endpoints Disponíveis
### Autenticação
- `POST /v1/signup`: Registro de novos usuários.
- `POST /v1/signin`: Autenticação de usuários.

### Cursos
- `GET /course/v1`: Listagem de cursos públicos.
- `GET /course`: Listagem de todos os cursos com detalhes.
- `GET /course/{courseId}`: Detalhes de um curso pelo ID.
- `POST /course`: Criação de um novo curso.
- `PUT /course/{courseId}`: Atualização de um curso existente.
- `DELETE /course/{courseId}`: Exclusão de um curso.

### Alunos
- `GET /student`: Listagem de todos os alunos.
- `GET /student/v1/confirm/{studentId}`: Confirma presença do aluno.
- `GET /student/{studentId}`: Detalhes de um aluno pelo ID.
- `PUT /student/{studentId}`: Atualização de dados de um aluno.

### Inscrições
- `PATCH /course/v1/enrollment/{courseId}`: Inscrição de um aluno em um curso.

## Autenticação e Segurança
- **JWT**: Tokens gerados no login e enviados no cabeçalho `Authorization`.
- **Controle de Acesso**:
  - Visitantes: Acesso apenas aos endpoints públicos.
  - Agentes: Acesso completo mediante autenticação.

## Logs e Monitoramento
- **Slf4j**: Logs estruturados no formato JSON.
- **Níveis de Log**:
  - `DEBUG`: Para desenvolvimento.
  - `INFO`: Para operações comuns.
  - `ERROR`: Para rastreamento de erros.

Logs são armazenados em arquivos rotativos no diretório `/logs`.

## Instruções de Instalação
### Requisitos
- Java 17 ou superior
- Maven 3.4.1 ou superior
- Docker

### Passos
1. Clone o repositório na sua máquina:
   ```shell
   git clone https://github.com/sobei/telecentro-backend.git
   ```
2. Acesse o diretório do projeto:
   ```shell
   cd tc-api
   ```
3. Configure o ambiente de desenvolvimento:
   - Crie um `.env` e utilize o `.env.example` como modelo.

4. Execute o Docker Compose para criar o container do banco de dados:
   ```shell
   docker-compose up -d
   ```
5. Compile e execute o projeto:
   ```shell
   mvn clean package
   java -jar target/tc-api-0.0.1-SNAPSHOT.jar
   ```


# Alura desafio

Alura desafio é uma aplicação Java Spring Boot, que oferece recursos para gerenciamento de cursos, matrículas, usuários e avaliações. A aplicação possui autenticação de usuários, autorização de rotas, geração de token JWT e documentação de API com Swagger. Além disso, a aplicação gera relatórios com o Net Promoter Score (NPS) de cada curso que tenha mais de quatro matrículas e o NPS por curso. 

## Tecnologias Utilizadas
- Java 21
- Maven
- Spring Boot 3.2.3
- MySQL
- Flyway (Ferramenta para versionamento de scripts SQL)
- Swagger (Documentação de API)
- Spring Security (Autenticação e Autorização)
- JWT (Token de autenticação)
- Bean Validation (Validação de dados)
- Docke-compose (Containerização do banco de dados)
- JUnit (Testes unitários)
- Mockito (Mock de objetos)


## Como Executar o Projeto Localmente

- Certifique-se de ter o JDK e o Maven instalados em seu sistema antes de continuar.

1. Clone o repositório do GitHub para o seu computador.

2. Navegue até o diretório do projeto clonado.
    ```bash
    cd alura
    ```
3. Execute o comando Maven para compilar o projeto.
    ```bash
    mvn clean install
    ```
4. Navegue até a pasta "docker" e execute o comando Docker Compose para iniciar o contêiner MySQL.
    ```bash
    cd docker
    docker-compose up
    ```
5. Após o contêiner MySQL ser iniciado, execute o aplicativo Spring Boot.
    ```bash
    mvn spring-boot:run
    ```
6. A aplicação será executada em `http://localhost:8081`.

7. Acesse `http://localhost:8081/swagger-ui.html` para visualizar a documentação da API.


## Funcionalidades

1. Usuarios
    - [x] Cadastro de Usuários
    - [x] Buscar usuário por username
    - [x] Buscar usuário por id
2. Cursos
    - [x] Cadastro de Cursos
    - [x] Listagem de cursos ativos e inativos
    - [x] Buscar curso por id
    - [x] Inativação de um curso
3. Matrículas
    - [x] Matricular Usuário em Curso
    - [x] Detalhes de Matrícula
4. Avaliações
    - [x] Avaliar Curso
    - [x] Envio de notificação de avaliação negativa para o instrutor
    - [x] Relatório com o Net Promoter Score (NPS) de cada curso que tenha mais de quatro matrículas
    - [x] Relatório com o Net Promoter Score (NPS) por curso
5. Autenticação
    - [x] Autenticação de Usuários
    - [x] Geração de Token JWT
    - [x] Validação de Token JWT
    - [x] Roles de Usuário

## Fluxo da funcionalidade de NPS
<img loading="lazy" src="https://r2.easyimg.io/k6ypam0pg/untitled-2024-03-26-2300.png" width=100% ><br>

## Como Autenticar-se na API usando o Swagger
1. Crie um usuário através da rota `/user` com o seguinte payload:

<img loading="lazy" src="https://r2.easyimg.io/5h1y9uikb/user.png" width=100% ><br>
 - Restrições:
   - Username deve conter apenas caracteres minúsculos, sem numerais e sem espaços.
   - Precisa ser um endereço de e-mail em um formato válido.
   - Pode apenas ter um usuário com o mesmo email/username.
```json
{
   "name": "Maria",
   "username": "maria",
   "email": "maria@example.com",
   "password": "123456",
   "role": "ESTUDANTE"
}
```
- O campo `role` pode ser `ESTUDANTE`, `ADMIN`' ou `INSTRUTOR`.
   - `ESTUDANTE` - Pode matricular-se em cursos e avaliá-los.
   - `ADMIN` - Pode criar cursos, matricular-se neles, desativa-los, consultar usuários e gerar relatórios.
   - `INSTRUTOR` - Pode matricular em um curso.
2. Faça login na rota `/auth` com o seguinte payload:
<img loading="lazy" src="https://r2.easyimg.io/5h1y9uikb/auth.png" width=100% ><br>

```json
{
   "username": "maria",
   "password": "123456"
}
```
3. Copie o token gerado.
<img loading="lazy" src="https://r2.easyimg.io/5h1y9uikb/token.png" width=100% ><br>
4. Clique no botão "Authorize" no canto superior direito da página do Swagger.
<img loading="lazy" src="https://r2.easyimg.io/5h1y9uikb/auth-swagger.png" ><br>
5. Cole o token no campo "Value" e clique em "Authorize".
6. Agora você está autenticado e pode acessar todas as rotas da API.
7. Para deslogar, clique no botão "Logout" no canto superior direito da página do Swagger.
   <img loading="lazy" src="https://r2.easyimg.io/5h1y9uikb/auth-true.png" width=100% ><br>




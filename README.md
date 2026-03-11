# Cadastro de Ninjas — Spring Boot

API RESTful completa para gerenciamento de ninjas e missões, com interface web via Thymeleaf e documentação interativa via Swagger. Desenvolvida como projeto do curso **Java10x**.

---

## 📋 Sobre o Projeto

O sistema permite cadastrar ninjas e associá-los a missões. A relação entre as entidades é **Many-to-One**: um ninja pode ter uma missão, e uma missão pode pertencer a vários ninjas.

A aplicação oferece **duas camadas de acesso**:
- **API REST** (`/ninjas`, `/missoes`) — para integração com clientes HTTP e testes via Swagger
- **Interface Web** (`/ninjas/ui`, `/missao/ui`) — páginas HTML renderizadas com Thymeleaf

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|---|---|---|
| Java | 17 | Linguagem principal |
| Spring Boot | 3.4.5 | Framework principal |
| Spring Web | — | Exposição dos endpoints REST |
| Spring Data JPA | — | Camada de persistência e ORM |
| Spring Validation | — | Validação de dados com Bean Validation |
| Thymeleaf | — | Template engine para interface web |
| H2 Database | — | Banco de dados em memória |
| Flyway | — | Gerenciamento de migrações do banco |
| Lombok | — | Redução de boilerplate |
| SpringDoc OpenAPI (Swagger) | 2.8.13 | Documentação interativa da API |
| Maven | — | Build e gerenciamento de dependências |

---

## 🗂️ Estrutura do Projeto

```
src/
└── main/
    ├── java/dev/lunguinhoantonio/CadastroDeNinjas/
    │   ├── CadastroDeNinjasApplication.java
    │   ├── Ninjas/
    │   │   ├── controller/
    │   │   │   ├── NinjaController.java       # Endpoints REST
    │   │   │   └── NinjaControllerUI.java     # Rotas da interface web
    │   │   ├── dto/NinjaDTO.java
    │   │   ├── mapper/NinjaMapper.java
    │   │   ├── model/NinjaModel.java
    │   │   ├── repository/NinjaRepository.java
    │   │   └── service/NinjaService.java
    │   └── Missoes/
    │       ├── controller/
    │       │   ├── MissoesController.java     # Endpoints REST
    │       │   └── MissoesControllerUI.java   # Rotas da interface web
    │       ├── dto/MissoesDTO.java
    │       ├── mapper/MissoesMapper.java
    │       ├── model/MissoesModel.java
    │       ├── repository/MissoesRepository.java
    │       └── service/MissoesService.java
    └── resources/
        ├── application.properties
        ├── static/images/ninja-default.jpg
        └── templates/
            ├── Ninja/
            │   ├── adicionarNinja.html
            │   ├── alterarNinja.html
            │   ├── detalhesNinja.html
            │   └── listarNinjas.html
            ├── Missao/
            │   ├── adicionarMissao.html
            │   ├── alterarMissao.html
            │   └── listarMissoes.html
            └── error/404.html
```

---

## 🗄️ Modelo de Dados

### Ninja (`tb_cadastro`)

| Campo | Tipo | Restrições |
|---|---|---|
| id | Long | PK, auto-gerado |
| nome | String | Obrigatório, 2–100 caracteres |
| idade | int | Obrigatório, 1–120 |
| email | String | Obrigatório, único, formato válido |
| imgUrl | String | Obrigatório, até 255 caracteres |
| rank | String | 4–50 caracteres |
| missoes_id | Long | FK → tb_missoes |

### Missão (`tb_missoes`)

| Campo | Tipo | Restrições |
|---|---|---|
| id | Long | PK, auto-gerado |
| nome | String | Obrigatório, 3–100 caracteres |
| dificuldade | String | `Fácil`, `Média`, `Difícil` ou `S-Rank` |

### Relacionamento

```
Missão (1) ──────────── (N) Ninja
```

---

## 🌐 Endpoints da API REST

### Ninjas — `/ninjas`

| Método | Rota | Descrição | Status |
|---|---|---|---|
| GET | `/ninjas/boasvindas` | Mensagem de boas-vindas | 200 |
| GET | `/ninjas/listar` | Lista todos os ninjas | 200 |
| GET | `/ninjas/listar/{id}` | Busca ninja por ID | 302 / 404 |
| POST | `/ninjas/criar` | Cria um novo ninja | 201 / 400 |
| PUT | `/ninjas/alterar/{id}` | Atualiza ninja completo | 200 / 404 |
| PATCH | `/ninjas/alterar/{id}` | Atualiza ninja parcialmente | 200 / 404 |
| DELETE | `/ninjas/deletar/{id}` | Remove ninja | 200 / 404 |

### Missões — `/missoes`

| Método | Rota | Descrição | Status |
|---|---|---|---|
| GET | `/missoes/listar` | Lista todas as missões | 200 |
| GET | `/missoes/listar/{id}` | Busca missão por ID | 200 / 404 |
| POST | `/missoes/criar` | Cria uma nova missão | 201 / 400 |
| PUT | `/missoes/alterar/{id}` | Atualiza missão completa | 200 / 404 |
| PATCH | `/missoes/alterar/{id}` | Atualiza missão parcialmente | 200 / 404 |
| DELETE | `/missoes/deletar/{id}` | Remove missão | 200 / 404 |

### Interface Web (Thymeleaf)

| Rota | Descrição |
|---|---|
| `/ninjas/ui/listar` | Listagem de ninjas |
| `/ninjas/ui/criar` | Formulário de cadastro de ninja |
| `/ninjas/ui/listar/{id}` | Detalhes de um ninja |
| `/ninjas/ui/alterar/{id}` | Formulário de edição de ninja |
| `/missao/ui/listar` | Listagem de missões |
| `/missao/ui/criar` | Formulário de cadastro de missão |
| `/missao/ui/alterar/{id}` | Formulário de edição de missão |

---

## ⚙️ Configuração e Execução

### Pré-requisitos

- Java 17+
- Maven 3.8+

### Variáveis de Ambiente

| Variável | Descrição | Exemplo |
|---|---|---|
| `DATABASE_URL` | URL de conexão do H2 | `jdbc:h2:mem:ninja_db` |
| `DATABASE_USERNAME` | Usuário do banco | `sa` |
| `DATABASE_PASSWORD` | Senha do banco | *(vazio)* |
| `PORT` | Porta do servidor (opcional) | `8080` |

### Executando localmente

```bash
# 1. Clone o repositório
git clone https://github.com/lunguinhoantonio/cadastro-ninja-spring.git
cd cadastro-ninja-spring

# 2. Defina as variáveis de ambiente
export DATABASE_URL=jdbc:h2:mem:ninja_db
export DATABASE_USERNAME=sa
export DATABASE_PASSWORD=

# 3. Execute a aplicação
./mvnw spring-boot:run
```

A aplicação estará disponível em: **http://localhost:8080**

---

## 📖 Documentação Interativa (Swagger)

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🗃️ Console do Banco de Dados H2

```
http://localhost:8080/h2-console
```

Use as mesmas credenciais configuradas nas variáveis de ambiente.

---

## ✅ Exemplos de Uso

### Criar um ninja
```http
POST /ninjas/criar
Content-Type: application/json

{
  "nome": "Naruto Uzumaki",
  "idade": 17,
  "email": "naruto@konoha.com",
  "imgUrl": "https://exemplo.com/naruto.jpg",
  "rank": "Genin"
}
```

### Criar uma missão
```http
POST /missoes/criar
Content-Type: application/json

{
  "nome": "Missão de Resgate",
  "dificuldade": "Difícil"
}
```

### Atribuir missão a um ninja (PATCH)
```http
PATCH /ninjas/alterar/1
Content-Type: application/json

{
  "missoes": { "id": 1 }
}
```

---

## 🧪 Testes

```bash
./mvnw test
```

---

## 👤 Autor

**Antonio Lunguinho**  
🔗 [github.com/lunguinhoantonio](https://github.com/lunguinhoantonio)

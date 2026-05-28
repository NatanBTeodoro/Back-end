# 🍕 Escopo do Sistema — Pizzaria Online

> Projeto simples com três atores: **Cliente**, **Admin** e **Pizzaria** (sistema).  
> Criado por Natan — 2026

---

## 1. Visão Geral

Sistema web de pedidos de pizza com cadastro de clientes, cardápio digital e painel administrativo. O fluxo principal é: cliente acessa o site → se cadastra → faz pedido → admin visualiza e gerencia.

---

## 2. Atores do Sistema

| Ator | Descrição |
|---|---|
| **Cliente** | Usuário final que se cadastra, navega no cardápio e realiza pedidos |
| **Admin** | Gerencia cardápio, visualiza clientes e acompanha pedidos |
| **Sistema (Pizzaria)** | Regras de negócio, validações, fluxo de navegação |

---

## 3. Estrutura de Páginas — Cliente

### 3.1 Página Inicial (`/`)

- **Barra de navegação superior** com os itens:
  - `Pizzas` — ancora ou navega para o cardápio
  - `Bebidas` — ancora ou navega para o cardápio
  - `Cadastro` — redireciona para `/cadastro`
- **Corpo central:** botão de destaque **"Peça Agora!"**
  - Se **não autenticado** → redireciona para `/cadastro`
  - Se **autenticado** → redireciona para `/pedido`
- **Rodapé fixo:** `Criado por Natan 2026` *(presente em todas as páginas)*

---

### 3.2 Página de Cadastro (`/cadastro`)

Formulário com os campos:

| Campo | Tipo | Observação |
|---|---|---|
| Nome completo | Texto | Obrigatório |
| CPF | Texto/Máscara | Formato `000.000.000-00` |
| Usuário | Texto | Único no sistema |
| Senha | Senha | Mínimo 6 caracteres (sugerido) |

> O endereço **não é cadastrado nesta etapa.** O cliente adiciona seus endereços posteriormente na página de Perfil.

- Botão **"Cadastrar"** — valida campos e cria conta
- Link **"Já tenho cadastro"** → redireciona para `/login`

---

### 3.3 Página de Login (`/login`)

- Campos: **Usuário** e **Senha**
- Botão **"Entrar"**
- Link **"Criar conta"** → `/cadastro`

---

### 3.4 Página de Perfil (`/perfil`) *(autenticado)*

Exibe as informações do cliente (somente leitura):

- Nome completo
- CPF
- Usuário

Campos **editáveis:**

- Usuário
- Senha *(com confirmação de senha)*

- Botão **"Salvar alterações"**

---

#### 3.4.1 Gerenciar Endereços (dentro do Perfil)

O cliente pode cadastrar **até 7 endereços simultâneos** vinculados à sua conta.

Cada endereço possui:

| Campo | Tipo | Observação |
|---|---|---|
| CEP | Texto | Preenchimento manual |
| Rótulo/Tipo | Seleção + Texto | Ver regra abaixo |

**Regra do Rótulo:**

| Opção | Comportamento |
|---|---|
| 🏠 Casa | Rótulo fixo, sem campo extra |
| 💼 Trabalho | Rótulo fixo, sem campo extra |
| ✏️ Outro | Exibe campo de texto livre obrigatório para nomear o local (ex: "Casa da Vó", "Academia") |

- Botão **"Adicionar endereço"** — disponível enquanto o total for menor que 7
- Cada endereço listado tem opção de **editar** ou **remover**
- Um endereço pode ser marcado como **principal** (usado por padrão no pedido)

---

### 3.5 Página de Pedido (`/pedido`) *(autenticado)*

**Duas abas intercambiáveis:**

#### Aba — Pizzas
- Ao clicar, expande e a aba Bebidas minimiza
- Lista de pizzas com:
  - Nome da pizza
  - Breve descrição
  - Valor
  - Botão **`+`** para adicionar ao pedido

#### Aba — Bebidas
- Ao clicar, expande e a aba Pizzas minimiza
- Lista de bebidas com:
  - Nome *(sem descrição)*
  - Valor
  - Botão **`+`** para adicionar ao pedido
- Bebidas disponíveis: Suco de Uva, Guaraná, Coca-Cola, Pepsi, Água

#### Resumo do Pedido
- Lista dos itens adicionados com quantidade e subtotal
- Valor total
- Botão **"Pedir"** *(verde)* — confirma e envia o pedido
- Botão **"Cancelar"** *(vermelho)* — limpa o pedido atual

---

## 4. Estrutura de Páginas — Admin

### 4.1 Login Admin (`/admin/login`)

- Campos: **Usuário** e **Senha** (credenciais exclusivas de admin)
- Acesso separado do cliente

---

### 4.2 Dashboard Admin (`/admin`) *(autenticado como admin)*

Visão geral com cards de resumo:
- Total de clientes cadastrados
- Total de pedidos realizados
- Pedidos do dia

---

### 4.3 Gerenciar Cardápio (`/admin/cardapio`)

#### Pizzas
- Listar todas as pizzas cadastradas
- **Cadastrar pizza:** Nome, Descrição, Valor, (opcional: imagem)
- **Editar** pizza existente
- **Remover** pizza

#### Bebidas
- Listar todas as bebidas
- **Cadastrar bebida:** Nome, Valor
- **Editar** e **Remover**

---

### 4.4 Gerenciar Clientes (`/admin/clientes`)

- Tabela com todos os clientes:
  - Nome completo
  - CPF
  - Usuário
  - Data de cadastro
- Busca por nome ou CPF
- Visualização individual do cliente com:
  - Todos os dados cadastrais *(somente leitura)*
  - Lista de endereços (CEP + rótulo de cada um)
  - **Histórico de alterações de cadastro** *(visível apenas para o admin)*:
    - Campo alterado (ex: "Usuário", "Senha", "Endereço")
    - Data e hora da alteração
    - Exemplo: `Usuário alterado em 15/05/2026 às 14:32`

---

### 4.5 Gerenciar Pedidos (`/admin/pedidos`)

- Lista de todos os pedidos com:
  - Cliente (nome/usuário)
  - Itens pedidos
  - Valor total
  - Data e hora
- Filtro por data

---

## 5. Navegação — Barra Superior por Perfil

| Item | Cliente não autenticado | Cliente autenticado | Admin |
|---|---|---|---|
| Pizzas | ✅ | ✅ | — |
| Bebidas | ✅ | ✅ | — |
| Cadastro | ✅ | — | — |
| Perfil | — | ✅ | — |
| Fazer Pedido | — | ✅ | — |
| Sair | — | ✅ | ✅ |
| Painel Admin | — | — | ✅ |

---

## 6. Regras de Negócio

- Apenas usuários autenticados podem fazer pedidos
- O botão "Peça Agora!" redireciona conforme o estado de autenticação
- CPF e usuário devem ser únicos no sistema
- Admin não acessa as páginas do cliente e vice-versa
- O rodapé `Criado por Natan 2026` aparece em **todas** as páginas
- Cada conta suporta **no máximo 7 endereços** simultâneos; ao atingir o limite, o botão "Adicionar endereço" é desabilitado
- Os rótulos **Casa** e **Trabalho** são pré-definidos; qualquer outro rótulo exige que o cliente escreva o nome do local manualmente
- Toda alteração em campos do perfil (usuário, senha, endereços) gera um **registro de log** com campo alterado + data/hora, visível **somente pelo admin**

---

## 7. Estrutura de Dados — Entities

> Todas as tabelas são geradas automaticamente pelo Hibernate ao subir o Spring Boot (`ddl-auto=update`).  
> O banco `pizzaria` deve ser criado manualmente no MySQL Workbench antes de iniciar a aplicação.

---

### Cliente → tabela `cliente`

| Campo | Tipo Java | Coluna | Restrições |
|---|---|---|---|
| id | `Long` | `id` | PK, auto increment |
| nomeCompleto | `String` | `nome_completo` | not null, length 150 |
| cpf | `String` | `cpf` | not null, unique, length 14 |
| usuario | `String` | `usuario` | not null, unique, length 50 |
| senha | `String` | `senha` | not null, length 255 |
| dataCadastro | `LocalDateTime` | `data_cadastro` | not null, gerado automaticamente, imutável |

---

### Endereco → tabela `endereco`

| Campo | Tipo Java | Coluna | Restrições |
|---|---|---|---|
| id | `Long` | `id` | PK, auto increment |
| cep | `String` | `cep` | not null, length 9 |
| rotulo | `enum Rotulo` | `rotulo` | not null — valores: `CASA`, `TRABALHO`, `OUTRO` |
| nomePersonalizado | `String` | `nome_personalizado` | obrigatório quando `rotulo = OUTRO`, length 100 |
| principal | `Boolean` | `principal` | not null, default `false` |
| cliente | `Cliente` | `cliente_id` | FK → cliente.id, not null |

> Máximo de 7 registros por `cliente_id` — validado na camada `verificacao`

---

### Pizza → tabela `pizza`

| Campo | Tipo Java | Coluna | Restrições |
|---|---|---|---|
| id | `Long` | `id` | PK, auto increment |
| nome | `String` | `nome` | not null, unique, length 100 |
| descricao | `String` | `descricao` | not null, length 255 |
| valor | `BigDecimal` | `valor` | not null |
| ativo | `Boolean` | `ativo` | not null, default `true` |

---

### Bebida → tabela `bebida`

| Campo | Tipo Java | Coluna | Restrições |
|---|---|---|---|
| id | `Long` | `id` | PK, auto increment |
| nome | `String` | `nome` | not null, unique, length 100 |
| valor | `BigDecimal` | `valor` | not null |
| ativo | `Boolean` | `ativo` | not null, default `true` |

---

### Pedido → tabela `pedido`

| Campo | Tipo Java | Coluna | Restrições |
|---|---|---|---|
| id | `Long` | `id` | PK, auto increment |
| cliente | `Cliente` | `cliente_id` | FK → cliente.id, not null |
| valorTotal | `BigDecimal` | `valor_total` | not null |
| dataHora | `LocalDateTime` | `data_hora` | not null, gerado automaticamente, imutável |

---

### PedidoItem → tabela `pedido_item`

| Campo | Tipo Java | Coluna | Restrições |
|---|---|---|---|
| id | `Long` | `id` | PK, auto increment |
| pedido | `Pedido` | `pedido_id` | FK → pedido.id, not null |
| tipo | `enum Tipo` | `tipo` | not null — valores: `PIZZA`, `BEBIDA` |
| itemId | `Long` | `item_id` | id da Pizza ou Bebida conforme o tipo |
| quantidade | `Integer` | `quantidade` | not null |
| valorUnitario | `BigDecimal` | `valor_unitario` | not null — salvo no momento do pedido |

> O campo `valorUnitario` preserva o preço original mesmo que o cardápio seja alterado depois.

---

### LogAlteracao → tabela `log_alteracao` *(visível apenas ao admin)*

| Campo | Tipo Java | Coluna | Restrições |
|---|---|---|---|
| id | `Long` | `id` | PK, auto increment |
| cliente | `Cliente` | `cliente_id` | FK → cliente.id, not null |
| campoAlterado | `String` | `campo_alterado` | not null, length 50 — ex: `"usuario"`, `"senha"`, `"endereco"` |
| dataHora | `LocalDateTime` | `data_hora` | not null, gerado automaticamente, imutável |

---

### Relacionamentos

```
cliente ──< endereco        (1 cliente → muitos endereços, máx. 7)
cliente ──< pedido          (1 cliente → muitos pedidos)
cliente ──< log_alteracao   (1 cliente → muitos logs)
pedido  ──< pedido_item     (1 pedido  → muitos itens)
```

---

## 8. Stack Tecnológica Definida

### 8.1 Back-end — Spring Boot

| Configuração | Valor |
|---|---|
| Framework | Spring Boot `4.0.6` |
| Linguagem | Java |
| Gerenciador de dependências | Maven |
| Arquivo de configuração | `application.properties` |
| Porta | `8080` |

**Dependências:**

| Dependência | Finalidade |
|---|---|
| `Spring Web` | Exposição de endpoints REST (controllers, rotas) |
| `Spring Data JPA` | Mapeamento objeto-relacional e acesso ao banco (repositories, entities) |
| `MySQL Driver` | Conexão com o banco de dados MySQL |

**Configuração base (`application.properties`):**
```properties
spring.application.name=pizzaria
spring.datasource.url=jdbc:mysql://localhost:3306/pizzaria?useUnicode=true&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**Estrutura de pastas (`src/main/java/com/pizzaria`):**

```
src/main/java/com/pizzaria/
│
├── entity/                  # Mapeamento das tabelas do banco (@Entity)
│   ├── Cliente.java
│   ├── Endereco.java
│   ├── Pizza.java
│   ├── Bebida.java
│   ├── Pedido.java
│   └── LogAlteracao.java
│
├── repository/              # Interface de acesso ao banco (extends JpaRepository)
│   ├── ClienteRepository.java
│   ├── EnderecoRepository.java
│   ├── PizzaRepository.java
│   ├── BebidaRepository.java
│   ├── PedidoRepository.java
│   └── LogAlteracaoRepository.java
│
├── dto/                     # Request e Response no mesmo arquivo por entidade
│   ├── ClienteDTO.java          # ClienteDTO.Request + ClienteDTO.Response
│   ├── EnderecoDTO.java         # EnderecoDTO.Request + EnderecoDTO.Response
│   ├── PizzaDTO.java            # PizzaDTO.Request + PizzaDTO.Response
│   ├── BebidaDTO.java           # BebidaDTO.Request + BebidaDTO.Response
│   ├── PedidoDTO.java           # PedidoDTO.Request + PedidoDTO.Response
│   ├── PedidoItemDTO.java       # PedidoItemDTO.Request + PedidoItemDTO.Response
│   └── LogAlteracaoDTO.java     # LogAlteracaoDTO.Response (apenas saída, sem Request)
│
├── mapper/                  # Conversão entre Entity ↔ DTO
│   ├── ClienteMapper.java
│   ├── EnderecoMapper.java
│   ├── PizzaMapper.java
│   ├── BebidaMapper.java
│   ├── PedidoMapper.java
│   └── LogAlteracaoMapper.java
│
├── verificacao/             # Métodos de validação e regras de negócio
│   ├── ClienteVerificacao.java    # Ex: CPF único, usuário único, limite de endereços
│   ├── EnderecoVerificacao.java   # Ex: máximo 7 endereços por cliente
│   ├── PedidoVerificacao.java     # Ex: pedido não pode ser vazio
│   └── AdminVerificacao.java      # Ex: credenciais de admin válidas
│
├── service/                 # Lógica de negócio; usa repository + verificacao + mapper
│   ├── ClienteService.java
│   ├── EnderecoService.java
│   ├── PizzaService.java
│   ├── BebidaService.java
│   ├── PedidoService.java
│   ├── LogAlteracaoService.java
│   └── AdminService.java
│
└── controller/              # Endpoints REST; recebe requisição e delega ao service
    ├── ClienteController.java
    ├── EnderecoController.java
    ├── PizzaController.java
    ├── BebidaController.java
    ├── PedidoController.java
    ├── LogAlteracaoController.java
    └── AdminController.java
```

**Fluxo de uma requisição:**
```
Request (JSON)
   → Controller        # Recebe e valida o formato da entrada (DTO)
   → Service           # Orquestra a lógica
       → Verificacao   # Valida regras de negócio antes de prosseguir
       → Repository    # Acessa o banco
       → Mapper        # Converte Entity → ResponseDTO
   → Response (JSON)
```

---

### 8.2 Front-end — React com Atomic Design

| Configuração | Valor |
|---|---|
| Framework | React |
| Porta | `3000` |
| Metodologia de organização | Atomic Design |

**Estrutura de pastas (`src/components`):**

```
src/
├── components/
│   ├── atoms/          # Elementos mínimos e indivisíveis
│   │   ├── Button/
│   │   ├── Input/
│   │   ├── Label/
│   │   └── Badge/
│   │
│   ├── molecules/      # Combinação de átomos com função específica
│   │   ├── FormField/        # Label + Input
│   │   ├── CardPizza/        # Nome + Descrição + Valor + Botão +
│   │   ├── CardBebida/       # Nome + Valor + Botão +
│   │   └── NavItem/          # Ícone + Link de navegação
│   │
│   ├── organisms/      # Seções completas e independentes
│   │   ├── Navbar/           # Barra de navegação superior
│   │   ├── Footer/           # Rodapé com "Criado por Natan 2026"
│   │   ├── FormCadastro/     # Formulário completo de cadastro
│   │   ├── FormLogin/        # Formulário de login
│   │   ├── ListaPizzas/      # Aba de pizzas com todos os cards
│   │   ├── ListaBebidas/     # Aba de bebidas com todos os cards
│   │   ├── ResumoPedido/     # Itens adicionados + total + botões
│   │   └── ListaEnderecos/   # Gerenciamento de endereços no perfil
│   │
│   ├── templates/      # Estrutura visual da página sem dados reais
│   │   ├── TemplatePublico/      # Layout com Navbar + conteúdo + Footer
│   │   ├── TemplateAutenticado/  # Layout com menu do cliente logado
│   │   └── TemplateAdmin/        # Layout do painel administrativo
│   │
│   └── pages/          # Páginas completas com dados e lógica
│       ├── Home/
│       ├── Cadastro/
│       ├── Login/
│       ├── Perfil/
│       ├── Pedido/
│       ├── admin/
│       │   ├── AdminLogin/
│       │   ├── AdminDashboard/
│       │   ├── AdminCardapio/
│       │   ├── AdminClientes/
│       │   └── AdminPedidos/
```

---

### 8.3 Banco de Dados

| Configuração | Valor |
|---|---|
| SGBD | MySQL |
| Interface gráfica | MySQL Workbench |
| Nome do banco | `pizzaria` |
| Gerenciado via | Spring Data JPA / Hibernate |

> **Fluxo de uso:** O banco `pizzaria` deve ser criado manualmente no MySQL Workbench antes de subir o projeto. Com `spring.jpa.hibernate.ddl-auto=update` no `application.properties`, o Hibernate cria e atualiza as tabelas automaticamente ao rodar a aplicação.

**Criação do banco no Workbench:**
```sql
CREATE DATABASE pizzaria;
USE pizzaria;
```

---

### 8.4 Comunicação Front ↔ Back

- O React (porta `3000`) consome a API REST do Spring (porta `8080`)
- Requisições via `fetch` ou `axios`
- Configurar **CORS** no back-end para permitir origem `http://localhost:3000`

```java
// Exemplo de configuração CORS no Spring
@CrossOrigin(origins = "http://localhost:3000")
```

---

*Rodapé de todas as páginas do sistema:*  
**Criado por Natan 2026**
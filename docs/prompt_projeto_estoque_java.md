# Prompt para Geração de Projeto: Sistema de Gestão de Estoque em Java (POO + JDBC)

## Contexto e Objetivo
Atue como um Engenheiro de Software Sênior e Arquiteto Java especializado em Programação Orientada a Objetos (POO) e banco de dados relacional. Desenvolva um **Sistema de Gestão de Estoque via Terminal (CLI)** completo, didático, bem estruturado e pronto para compilação e execução em **Java (versão 17 ou superior)**.

O objetivo do projeto é servir como material prático de estudo e referência acadêmica, demonstrando de forma clara os pilares da Orientação a Objetos, boas práticas de arquitetura em camadas e persistência de dados multi-banco utilizando JDBC puro.

---

## Requisitos Técnicos e Arquitetura

### 1. Stack Tecnológica
* **Linguagem:** Java 17+
* **Gerenciador de Dependências:** Maven (`pom.xml`) contendo os drivers JDBC oficiais para:
  * SQLite (`org.xerial:sqlite-jdbc`)
  * MySQL (`com.mysql:mysql-connector-j`)
  * PostgreSQL (`org.postgresql:postgresql`)
* **Persistência:** JDBC puro (`java.sql.*`) com uso obrigatório de `PreparedStatement` para prevenção contra SQL Injection e `try-with-resources` para gerenciamento seguro de conexões.

### 2. Estrutura de Pacotes
Organize o código-fonte seguindo a arquitetura em camadas:
```text
src/main/java/com/estoque/
├── config/          # Fábrica de conexões e leitura de configurações (config.properties)
├── model/           # Entidades de domínio (Produto, ProdutoPerecivel, ProdutoEletronico, Categoria)
├── repository/      # Interfaces DAO/Repository e implementações JDBC
├── service/         # Regras de negócio e validações
├── exception/       # Exceções personalizadas (ex: EstoqueInsuficienteException, ProdutoNaoEncontradoException)
├── ui/              # Interface de linha de comando (Menus interativos e leitura com Scanner)
└── Main.java        # Ponto de entrada da aplicação
```

### 3. Modelagem Orientada a Objetos (Pacote `model`)
Implemente os seguintes conceitos obrigatórios:
* **Abstração e Encapsulamento:** Classe abstrata `Produto` com atributos privados (`id`, `nome`, `preco`, `quantidade`, `categoria`), getters/setters com validação (impedindo preços negativos ou nomes vazios) e o método concreto `calcularValorTotal()`.
* **Herança e Polimorfismo:**
  * `ProdutoPerecivel extends Produto`: adiciona `dataValidade` (`LocalDate`), sobrescreve `exibirDetalhes()` alertando caso o item esteja vencido e implementa método para verificar validade.
  * `ProdutoEletronico extends Produto`: adiciona `mesesGarantia` (`int`) e sobrescreve `exibirDetalhes()` exibindo o prazo de garantia.
* **Associação / Composição:** Classe `Categoria` (`id`, `nome`) associada a `Produto` (relacionamento N:1).

### 4. Conexão Multi-Banco (SQLite, MySQL e PostgreSQL)
* Crie um arquivo `src/main/resources/config.properties` contendo a chave `db.type` (aceitando `sqlite`, `mysql` ou `pgsql`) e as respectivas propriedades de URL, usuário e senha.
* Implemente a classe `DatabaseConnectionFactory` utilizando o **Padrão Factory** para ler o arquivo `config.properties` e retornar uma instância válida de `java.sql.Connection` correspondente ao banco selecionado.
* Inclua na inicialização da aplicação um método que cria automaticamente as tabelas (`categorias` e `produtos`) caso não existam, adaptando a sintaxe de autoincremento (`AUTOINCREMENT` no SQLite, `AUTO_INCREMENT` no MySQL, `SERIAL` no PostgreSQL).

### 5. Operações CRUD e Regras de Negócio
Implemente a interface `ProdutoRepository` e sua implementação `ProdutoRepositoryJDBC` (além de `CategoriaRepository` para suporte), contemplando:
* **Create (Cadastrar):** Cadastrar categorias e produtos (permitindo escolher entre Produto Perecível ou Eletrônico).
* **Read (Listar e Buscar):** Listar todos os produtos formatados em tabela no terminal, buscar produto por ID e gerar relatório de **Estoque Crítico** (quantidade < 5 unidades).
* **Update (Atualizar e Movimentar):** Atualizar dados cadastrais (preço/nome) e realizar movimentação de estoque (Entrada e Saída), lançando `EstoqueInsuficienteException` caso a saída seja maior que o saldo disponível.
* **Delete (Excluir):** Excluir um produto por ID mediante confirmação explícita (`S/N`) do usuário no terminal.

### 6. Interface no Terminal (Pacote `ui`)
* Crie uma classe `MenuCLI` limpa e resiliente a erros de digitação do usuário (tratando `InputMismatchException` e entradas inválidas sem encerrar o programa abruptamente).
* Exiba no cabeçalho do menu qual banco de dados está ativo no momento (ex: `[Banco Ativo: SQLITE]`).

---

## Entregáveis Esperados na Resposta
1. Arquivo `pom.xml` completo e funcional.
2. Arquivo `config.properties` comentado com exemplos para SQLite, MySQL e PostgreSQL.
3. Scripts SQL DDL (`schema.sql`) comentados para referência nos três bancos.
4. Código-fonte completo, comentado e didático de todas as classes e interfaces listadas na estrutura de pacotes.
5. Um guia rápido (`README.md`) explicando como compilar, trocar o banco de dados ativo e executar o projeto via terminal.

# Prompt para Geração de Projeto Java: Sistema de Gestão de Estoque (CLI + Multi-Banco)

Atue como um Engenheiro de Software Sênior e Professor de Programação Orientada a Objetos. Crie um projeto completo, didático e bem documentado em **Java (versão 17 ou superior)** utilizando **Maven** e **JDBC puro** (sem frameworks ORM como Hibernate/JPA, para evidenciar os conceitos de POO e SQL).

## 1. Objetivo do Projeto
Desenvolver uma aplicação de linha de comando (CLI) para **Gestão de Estoque** que aplique rigorosamente os pilares da Programação Orientada a Objetos e permita alternar facilmente a conexão entre **SQLite**, **MySQL** e **PostgreSQL** através de um arquivo de configuração (`config.properties`).

## 2. Arquitetura e Estrutura de Pacotes
Organize o código na seguinte estrutura de pacotes dentro de `src/main/java/com/estoque/`:
- `model/`: Entidades de domínio (`Produto`, `Categoria`).
- `database/`: Fábrica de conexões (`ConnectionFactory`) e inicializador de tabelas (`DatabaseInitializer`).
- `repository/`: Interfaces (`ProdutoRepository`, `CategoriaRepository`) e suas implementações JDBC (`ProdutoRepositoryJDBC`, `CategoriaRepositoryJDBC`).
- `service/`: Regras de negócio e validações (`EstoqueService`).
- `view/`: Interface de usuário no terminal (`MenuTerminal`).
- `Main.java`: Ponto de entrada da aplicação.

## 3. Requisitos de Orientação a Objetos (POO)
- **Encapsulamento:** Todos os atributos das classes de modelo devem ser `private`, com *getters* e *setters* realizando validações básicas (ex: impedir preço ou quantidade negativos lançando `IllegalArgumentException`).
- **Associação / Composição:** A classe `Produto` deve possuir um atributo `private Categoria categoria;`, representando o relacionamento com a classe `Categoria` (`id`, `nome`).
- **Abstração e Polimorfismo:** Utilizar interfaces para a camada de repositório (`ProdutoRepository` e `CategoriaRepository`), desacoplando a regra de negócio da tecnologia de persistência.
- **Métodos de Domínio:** A classe `Produto` deve conter métodos próprios como `calcularValorTotal()` (preço * quantidade) e `exibirDetalhes()` (retornando uma string formatada com os dados do produto e sua categoria).

## 4. Persistência Multi-Banco (SQLite, MySQL, PostgreSQL)
- **Dependências (`pom.xml`):** Incluir os drivers JDBC oficiais para `sqlite-jdbc`, `mysql-connector-j` e `postgresql`.
- **Configuração Externa:** Ler um arquivo `src/main/resources/config.properties` contendo:
  - `db.type` (`sqlite`, `mysql` ou `pgsql`)
  - `db.url`, `db.user`, `db.password`
- **Padrão Factory (`ConnectionFactory`):** Retornar uma instância de `java.sql.Connection` configurada de acordo com o `db.type` definido.
- **Criação Automática de Tabelas (`DatabaseInitializer`):** Executar `CREATE TABLE IF NOT EXISTS` para as tabelas `categorias` e `produtos` na inicialização, adaptando a sintaxe de chave primária autoincrementável conforme o banco ativo (`INTEGER PRIMARY KEY AUTOINCREMENT` no SQLite, `INT AUTO_INCREMENT PRIMARY KEY` no MySQL e `SERIAL PRIMARY KEY` no PostgreSQL).
- **Segurança:** Utilizar `PreparedStatement` em todas as consultas SQL para evitar SQL Injection.

## 5. Funcionalidades Obrigatórias (CRUD no Terminal)
Implementar um menu interativo utilizando `Scanner` com tratamento de exceções (`try-catch`) para entradas inválidas:
1. **Cadastrar Categoria e Produto (Create):** Permitir cadastrar categorias e novos produtos vinculados a uma categoria existente.
2. **Listar Produtos em Estoque (Read):** Exibir todos os produtos cadastrados de forma tabulada no console (ID, Nome, Categoria, Preço Unitário, Quantidade e Valor Total em Estoque) e permitir busca por ID.
3. **Movimentar Estoque / Atualizar Produto (Update):** Permitir atualizar dados básicos (nome/preço) ou registrar **Entrada** (adição) e **Saída** (baixa) de estoque, validando na camada `Service` se há saldo suficiente antes de efetuar a saída.
4. **Excluir Produto (Delete):** Solicitar o ID do produto, exibir seus dados, pedir confirmação (`S/N`) e removê-lo do banco de dados.

## 6. Sugestão Visual da Tela no Terminal
Utilize o modelo abaixo como referência visual para a construção do menu principal no console, exibindo dinamicamente o banco de dados ativo no cabeçalho:

```text
==================================================
   SISTEMA DE GESTÃO DE ESTOQUE [Banco: SQLite]
==================================================
1. Cadastrar Novo Produto
2. Listar Produtos em Estoque
3. Registrar Entrada / Saída (Atualizar)
4. Excluir Produto
5. Gerenciar Categorias
0. Sair
--------------------------------------------------
Escolha uma opção: _
```

## 7. Entregáveis Esperados na Resposta
Forneça:
1. O arquivo `pom.xml` completo.
2. O arquivo `config.properties` comentado com exemplos de conexão para os 3 bancos.
3. Os scripts SQL de referência para criação das tabelas nos 3 SGBDs.
4. O código-fonte completo, limpo e comentado de todas as classes Java.
5. Instruções rápidas de como compilar e executar o projeto pelo terminal.

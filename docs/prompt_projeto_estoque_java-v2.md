# Prompt para Geração de Projeto Gabarito: Sistema de Gestão de Estoque em Java (POO + JDBC)

Atue como um Engenheiro de Software Sênior e Professor de Programação Orientada a Objetos. Crie um projeto completo, didático, bem estruturado e comentado em **Java (versão 17 ou superior)** utilizando **Maven** e **JDBC puro** (sem frameworks ORM como Hibernate), para um **Sistema de Gestão de Estoque via Terminal (CLI)**.

---

## 1. Requisitos Técnicos e Arquitetura
* **Linguagem e Build:** Java 17+, gerenciado via Maven (`pom.xml`).
* **Drivers JDBC no `pom.xml`:** Incluir as dependências para **SQLite** (`sqlite-jdbc`), **MySQL** (`mysql-connector-j`) e **PostgreSQL** (`postgresql`).
* **Padrões de Projeto:**
  * **Factory Pattern (`DatabaseFactory`):** Lê um arquivo `config.properties` (com chaves `db.type=sqlite|mysql|pgsql`, `db.url`, `db.user`, `db.password`) e retorna uma instância ativa de `java.sql.Connection`.
  * **Repository Pattern (`ProdutoRepository`):** Interface definindo o contrato do CRUD (`salvar`, `buscarPorId`, `listarTodos`, `atualizar`, `deletar`, `listarEstoqueBaixo`) e sua implementação concreta `ProdutoRepositoryJDBC` usando `PreparedStatement` para evitar SQL Injection.
* **Organização de Pacotes:**
  * `br.edu.estoque.model`
  * `br.edu.estoque.database`
  * `br.edu.estoque.repository`
  * `br.edu.estoque.service`
  * `br.edu.estoque.cli`
  * `br.edu.estoque.Main`

---

## 2. Modelagem Orientada a Objetos (POO)
Implemente rigorosamente os pilares da POO:
1. **Encapsulamento:** Todos os atributos devem ser `private`, com *getters* e *setters* contendo validações (ex: lançar `IllegalArgumentException` caso preço ou quantidade sejam negativos).
2. **Abstração e Herança:**
   * `Produto` (classe abstrata base): `id` (Integer), `nome` (String), `preco` (double), `quantidade` (int), `categoria` (Categoria). Métodos: `calcularValorTotal()` e método abstrato `exibirDetalhes()`.
   * `ProdutoPerecivel` (estende `Produto`): adiciona `dataValidade` (`LocalDate`). Sobrescreve `exibirDetalhes()` indicando se o item está vencido ou dentro da validade.
   * `ProdutoEletronico` (estende `Produto`): adiciona `mesesGarantia` (int). Sobrescreve `exibirDetalhes()` exibindo o tempo de garantia.
3. **Associação:** Classe `Categoria` (`id`, `nome`) associada a `Produto`.
4. **Polimorfismo:** Ao listar os produtos no terminal, iterar sobre uma `List<Produto>` invocando `exibirDetalhes()` de forma polimórfica.

---

## 3. Banco de Dados e Scripts SQL
* Forneça os scripts SQL (`schema_sqlite.sql`, `schema_mysql.sql`, `schema_pgsql.sql`) para criação das tabelas `categorias` e `produtos` (utilizando *Single Table Inheritance* com uma coluna `tipo_produto` para diferenciar `PERECIVEL` e `ELETRONICO`, além de colunas anuláveis `data_validade` e `meses_garantia`).
* Inclua `INSERT`s iniciais de categorias (ex: Alimentos, Informática, Limpeza) para facilitar os testes.
* Implemente um inicializador automático opcional no `DatabaseFactory` para criar as tabelas caso não existam (especialmente útil no SQLite).

---

## 4. Funcionalidades do Terminal (CRUD + Regras de Negócio)
Implemente um menu interativo via `Scanner` na camada `cli` com tratamento de exceções (`try-catch` para entradas inválidas e erros de SQL):
1. **Create (Cadastrar Produto):** Permite escolher entre Perecível ou Eletrônico, selecionar a Categoria e salvar no banco.
2. **Read (Listar Estoque):** Exibe todos os produtos formatados no console com seus detalhes polimórficos e o valor total em estoque.
3. **Update (Movimentar / Editar Estoque):** Permite atualizar preço ou registrar **Entrada** (soma quantidade) e **Saída** (subtrai quantidade, validando na camada `Service` se há saldo suficiente em estoque).
4. **Delete (Excluir Produto):** Solicita o ID, exibe o produto encontrado e pede confirmação (`S/N`) antes de remover.
5. **Relatório de Estoque Crítico:** Lista produtos com `quantidade < 5`.

### Sugestão Visual da Tela no Terminal
Utilize o modelo abaixo como referência visual para a interface do menu principal no console, exibindo dinamicamente o banco de dados ativo no cabeçalho:

```text
==================================================
   SISTEMA DE GESTÃO DE ESTOQUE [Banco: SQLite]
==================================================
1. Cadastrar Novo Produto
2. Listar Produtos em Estoque
3. Registrar Entrada / Saída (Atualizar)
4. Excluir Produto
5. Relatório de Estoque Crítico
0. Sair
--------------------------------------------------
Escolha uma opção: _
```

---

## 5. Formato de Saída Esperado
Entregue:
1. A árvore completa de diretórios do projeto.
2. O conteúdo completo do `pom.xml` e do `config.properties`.
3. Os scripts SQL para SQLite, MySQL e PostgreSQL.
4. O código-fonte completo e comentado de todas as classes Java.
5. Instruções rápidas de como executar e alternar entre os três bancos de dados.

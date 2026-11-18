# Prompt para Geração do Projeto Gabarito: Sistema de Gestão de Estoque em Java (CLI + SQLite)

Atue como um Engenheiro de Software Sênior e Professor de Programação Orientada a Objetos. Gere o código-fonte completo, didático, compilável e bem estruturado de um **Sistema de Gestão de Estoque via Terminal (CLI)** desenvolvido em **Java 17+** utilizando **Maven** e **JDBC puro** com banco de dados **SQLite**.

## 1. Requisitos Técnicos e Dependências
* **Linguagem:** Java 17 ou superior.
* **Gerenciador de Dependências:** Maven (`pom.xml` completo com o driver `sqlite-jdbc`).
* **Acesso a Dados:** JDBC puro (`PreparedStatement`, `ResultSet`, `Connection`) utilizando SQLite embarcado (criando o arquivo `estoque.db` localmente).
* **Inicialização Automática:** Ao iniciar a aplicação, o sistema deve executar automaticamente os comandos `CREATE TABLE IF NOT EXISTS` para criar as tabelas necessárias no SQLite, além de inserir 3 categorias padrão iniciais caso a tabela de categorias esteja vazia.

## 2. Arquitetura e Estrutura de Pacotes
Organize o projeto seguindo boas práticas de separação de responsabilidades (padrões **Factory** e **Repository/DAO**):

```text
src/main/java/br/edu/estoque/
├── Main.java                       # Ponto de entrada da aplicação
├── database/
│   └── ConnectionFactory.java      # Fábrica de conexão com o SQLite (estoque.db)
├── model/
│   ├── Categoria.java              # Classe de domínio (id, nome)
│   └── Produto.java                # Classe de domínio (id, nome, preco, quantidade, categoria)
├── repository/
│   ├── CategoriaRepository.java    # Operações de leitura de categorias
│   └── ProdutoRepository.java      # Implementação JDBC do CRUD de produtos
├── service/
│   └── EstoqueService.java         # Regras de negócio e validações
└── cli/
    └── MenuTerminal.java           # Interface de console com Scanner e tratamento de entradas
```

## 3. Modelagem de Classes (POO)
* **Encapsulamento:** Todos os atributos devem ser `private`, com construtores, *getters* e *setters* validando regras básicas (ex.: não permitir preço negativo ou quantidade negativa).
* **Classe `Categoria`:**
  * Atributos: `id` (`int`), `nome` (`String`).
* **Classe `Produto`:**
  * Atributos: `id` (`int`), `nome` (`String`), `preco` (`double`), `quantidade` (`int`), `categoria` (`Categoria` - composição/associação).
  * Métodos adicionais: `calcularValorTotal()` (retorna `preco * quantidade`) e `toString()` formatado.

## 4. Funcionalidades Obrigatórias (CRUD no Terminal)
O menu principal deve rodar em loop até o usuário escolher a opção `0` (Sair), contemplando as 4 operações essenciais:
1. **Cadastrar Novo Produto (Create):** Solicita nome, preço, quantidade inicial e exibe as categorias disponíveis para escolha por ID. Salva no SQLite.
2. **Listar Produtos em Estoque (Read):** Exibe todos os produtos cadastrados em formato tabular no console (ID, Nome, Categoria, Preço Unitário, Quantidade e Valor Total em Estoque).
3. **Atualizar Produto / Movimentar Estoque (Update):** Permite buscar um produto pelo ID e escolher entre atualizar o preço ou registrar uma **Entrada** (somar quantidade) ou **Saída** (subtrair quantidade, validando se há saldo suficiente em estoque).
4. **Excluir Produto (Delete):** Busca o produto pelo ID, exibe seus dados, pede confirmação (`S/N`) e remove o registro do SQLite.

## 5. Sugestão Visual da Tela no Terminal
Utilize o modelo visual abaixo como referência para a interface de console (`MenuTerminal.java`), mantendo uma apresentação limpa e organizada:

```text
==================================================
   SISTEMA DE GESTÃO DE ESTOQUE [Banco: SQLite]
==================================================
1. Cadastrar Novo Produto
2. Listar Produtos em Estoque
3. Atualizar Produto / Movimentar Estoque
4. Excluir Produto
0. Sair
--------------------------------------------------
Escolha uma opção: _
```

## 6. Instruções de Entrega na Resposta
* Forneça o arquivo `pom.xml` completo.
* Forneça o código completo de **todas** as classes Java listadas na estrutura de pacotes, sem omitir trechos com comentários como `// implementar aqui`.
* Trate exceções de entrada inválida (`InputMismatchException` no `Scanner`) e erros de banco (`SQLException`) de forma amigável para o usuário no terminal.
* Inclua ao final um breve passo a passo de como compilar e executar o projeto via Maven no terminal.

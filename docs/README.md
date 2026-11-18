# Sistema de Gestão de Estoque (Java + POO + JDBC)

Projeto didático de um sistema de estoque via terminal, feito em **Java 17+** e **JDBC puro**.
O banco de dados é o **SQLite**: um arquivo (`estoque.db`) criado sozinho, sem instalar nenhum servidor.

## Estrutura do projeto

```text
ProjetoJava/
├── src/
│   ├── App.java                         # Ponto de entrada (main): monta as camadas
│   ├── model/
│   │   └── Produto.java                 # Classe do produto (atributos + validações)
│   ├── database/
│   │   └── Conexao.java                 # Conexão com o SQLite e criação da tabela
│   ├── repository/                      # Acesso ao banco (SQL)
│   │   ├── ProdutoRepository.java       # Interface (contrato)
│   │   └── ProdutoRepositoryJDBC.java   # Implementação com PreparedStatement
│   ├── service/
│   │   └── ProdutoService.java          # Regras de negócio (ex: saldo para saída)
│   └── cli/
│       └── MenuCLI.java                 # Menu no terminal (Scanner)
├── lib/
│   └── sqlite-jdbc.jar                  # Driver JDBC do SQLite
└── bin/                                 # Classes compiladas (gerado automaticamente)
```

### Como as camadas conversam

```text
MenuCLI  ->  ProdutoService  ->  ProdutoRepository  ->  Conexao  ->  Banco
(tela)       (regras)            (SQL)                  (conexão)
```

Cada camada só conhece a camada logo abaixo dela. A tela nunca escreve SQL, e o repositório
nunca valida regra de negócio.

## Onde está cada conceito de POO

| Conceito        | Onde ver                                                                    |
|-----------------|-----------------------------------------------------------------------------|
| Classe e objeto | `Produto` e os `new Produto(...)` no `MenuCLI` e no repositório             |
| Encapsulamento  | Atributos `private` e setters com validação em `Produto`                    |
| Interface       | `ProdutoRepository` (contrato) e `ProdutoRepositoryJDBC` (implementação)    |
| Composição      | `ProdutoService` usa um `ProdutoRepository`; `MenuCLI` usa um `ProdutoService` |

## Como executar

Pré-requisito: **Java 17+** (`java -version` para conferir).

### Pelo VS Code
Abra a pasta do projeto, abra o `src/App.java` e clique em **Run** acima do `main`.
O VS Code já está configurado (`.vscode/settings.json`) para usar `src/`, `bin/` e os `.jar` da `lib/`.

### Pelo terminal
Na pasta do projeto:

```bash
# Compilar
javac -d bin $(find src -name "*.java")

# Executar (no Windows, troque ":" por ";")
java -cp "bin:lib/*" App
```

Na primeira execução, o arquivo `estoque.db` é criado na pasta do projeto com a tabela `produtos`.
Para começar com o banco vazio, basta apagar o `estoque.db`.

> Rode sempre a partir da pasta do projeto: é lá que o programa cria o `estoque.db`.
>
> No Java 24 ou mais novo, o SQLite pode mostrar um aviso `WARNING: A restricted method...`.
> Pode ignorar, não atrapalha em nada.

## Sugestões de exercícios para os alunos

1. Criar um relatório de produtos com estoque baixo (ex: quantidade menor que 5).
2. Adicionar a opção de alterar o nome de um produto.
3. **Herança:** criar subclasses de `Produto` (ex: `ProdutoPerecivel` com `dataValidade`,
   `ProdutoEletronico` com `mesesGarantia`) e tornar `Produto` abstrata.
4. **Associação:** criar a classe `Categoria` e a tabela `categorias`, ligando cada produto a uma
   categoria (aqui entra o `JOIN`).

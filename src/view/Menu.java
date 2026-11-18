package view;

import model.Cliente;
import model.Produto;
import repository.ClienteRepositorySQLite;
import repository.ProdutoRepositorySQLite;
import service.ClienteService;
import service.ProdutoService;
import utils.Utils;

import java.sql.SQLException;
import java.util.List;


public class Menu {

    private static final String LINHA_DUPLA = "==================================================";
    private static final String LINHA_SIMPLES = "--------------------------------------------------";

    private final ProdutoService service;
    private final ClienteService clienteService;

    public Menu() {
        this.service = new ProdutoService(new ProdutoRepositorySQLite());
        this.clienteService = new ClienteService(new ClienteRepositorySQLite());
    }

    /** Laço principal do menu. */
    public void iniciar() {
        int opcao;
        do {
            exibirMenu();
            opcao = Utils.lerInteiro("Escolha uma opção: ");

            // Um único try-catch protege todas as opções:
            // erros de banco ou de validação não derrubam o programa.
            try {
                switch (opcao) {
                    case 1 -> cadastrarProduto();
                    case 2 -> listarProdutos();
                    case 3 -> excluirProduto();
                    case 4 -> cadastrarCliente();
                    case 5 -> listarClientes();
                    case 6 -> excluirCliente();
                    case 0 -> System.out.println("Saindo... Até logo!");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.out.println("Erro no banco de dados: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Atenção: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void exibirMenu() {
        System.out.println();
        System.out.println(LINHA_DUPLA);
        System.out.println("   SISTEMA DE CADASTRO [Banco: SQLite]");
        System.out.println(LINHA_DUPLA);
        System.out.println("1. Cadastrar Produto");
        System.out.println("2. Listar Produtos");
        System.out.println("3. Excluir Produto");
        System.out.println(LINHA_SIMPLES);
        System.out.println("4. Cadastrar Cliente");
        System.out.println("5. Listar Clientes");
        System.out.println("6. Excluir Cliente");
        System.out.println(LINHA_SIMPLES);
        System.out.println("0. Sair");
        System.out.println(LINHA_SIMPLES);
    }

    private void cadastrarProduto() throws SQLException {
        System.out.println("\n--- Cadastrar Produto ---");
        String descricao = Utils.lerTexto("Descrição: ");
        double preco = Utils.lerDouble("Preço (R$): ");
        int estoque = Utils.lerInteiro("Estoque: ");

        // O id é null porque quem gera o id é o banco
        Produto produto = new Produto(null, descricao, preco, estoque);

        service.cadastrar(produto);
        System.out.println("Produto cadastrado com sucesso! ID: " + produto.getId());
    }

    private void listarProdutos() throws SQLException {
        List<Produto> produtos = service.listar();
        System.out.println("\n--- Produtos ---");
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        for (Produto p : produtos) {
            System.out.println(p.exibirDetalhes());
        }
    }

    private void excluirProduto() throws SQLException {
        System.out.println("\n--- Excluir Produto ---");
        int id = Utils.lerInteiro("ID do produto: ");

        service.excluir(id);
        System.out.println("Produto excluído!");
    }

    private void cadastrarCliente() throws SQLException {
        System.out.println("\n--- Cadastrar Cliente ---");
        String nome = Utils.lerTexto("Nome: ");
        String telefone = Utils.lerTexto("Telefone: ");
        String email = Utils.lerTexto("Email: ");

        Cliente cliente = new Cliente(null, nome, telefone, email);

        clienteService.cadastrar(cliente);
        System.out.println("Cliente cadastrado com sucesso! ID: " + cliente.getId());
    }

    private void listarClientes() throws SQLException {
        List<Cliente> clientes = clienteService.listar();
        System.out.println("\n--- Clientes ---");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente c : clientes) {
            System.out.println(c.exibirDetalhes());
        }
    }

    private void excluirCliente() throws SQLException {
        System.out.println("\n--- Excluir Cliente ---");
        int id = Utils.lerInteiro("ID do cliente: ");

        clienteService.excluir(id);
        System.out.println("Cliente excluído!");
    }
}

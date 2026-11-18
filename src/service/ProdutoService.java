package service;

import model.Produto;
import repository.ProdutoRepositoryInterface;

import java.sql.SQLException;
import java.util.List;

/**
 * Camada de SERVIÇO: guarda as REGRAS DE NEGÓCIO.
 *
 * O CLI (tela) não fala direto com o banco: ele chama o Service,
 * que valida as regras e depois usa o Repository para persistir.
 */
public class ProdutoService {

    private final ProdutoRepositoryInterface produtoRepository;

    // Recebe o repositório pelo construtor (a interface, não a classe JDBC)
    public ProdutoService(ProdutoRepositoryInterface produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void cadastrar(Produto produto) throws SQLException {
        produtoRepository.salvar(produto);
    }

    public List<Produto> listar() throws SQLException {
        return produtoRepository.listar();
    }

    public void excluir(int id) throws SQLException {
        if (!produtoRepository.deletar(id)) {
            throw new IllegalArgumentException("Produto com ID " + id + " não encontrado.");
        }
    }
}

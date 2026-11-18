package repository;

import model.Produto;

import java.sql.SQLException;
import java.util.List;

/**
 * REPOSITORY PATTERN: contrato (interface) com as operações de banco de Produto.
 *
 * Quem usa (o Service) depende só desta interface, não da implementação.
 * Assim poderíamos trocar JDBC por outra tecnologia sem mexer no Service.
 */
public interface ProdutoRepositoryInterface {

    void salvar(Produto produto) throws SQLException;

    List<Produto> listar() throws SQLException;

    /** Retorna true se excluiu, false se o id não existia. */
    boolean deletar(int id) throws SQLException;
}

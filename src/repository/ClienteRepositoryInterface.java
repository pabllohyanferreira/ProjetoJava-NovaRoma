package repository;

import model.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface ClienteRepositoryInterface {

    void salvar(Cliente cliente) throws SQLException;

    List<Cliente> listar() throws SQLException;

    boolean deletar(int id) throws SQLException;
}

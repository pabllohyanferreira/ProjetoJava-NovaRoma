package service;

import model.Cliente;
import repository.ClienteRepositoryInterface;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {
    private final ClienteRepositoryInterface clienteRepository;

    public ClienteService(ClienteRepositoryInterface clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void cadastrar(Cliente cliente) throws SQLException {
        clienteRepository.salvar(cliente);
    }

    public List<Cliente> listar() throws SQLException {
        return clienteRepository.listar();
    }

    public void excluir(int id) throws SQLException {
        if (!clienteRepository.deletar(id)) {
            throw new IllegalArgumentException("Cliente com ID " + id + " não encontrado.");
        }
    }
}

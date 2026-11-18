package repository;

import database.Conexao;
import model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do ProdutoRepository usando JDBC puro.
 *
 * Sempre usamos PreparedStatement com "?" no lugar dos valores.
 * Isso evita SQL Injection, pois o driver trata os valores com segurança.
 *
 * Usamos try / catch / finally:
 *  - try:     executa o SQL
 *  - catch:   trata o erro (aqui acrescentamos uma mensagem mais clara)
 *  - finally: SEMPRE executa, com ou sem erro. Usamos para fechar a conexão.
 *             (Fechar a Connection fecha também o Statement e o ResultSet.)
 */
public class ProdutoRepositorySQLite implements ProdutoRepositoryInterface {

    @Override
    public void salvar(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos (descricao, preco, estoque) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getEstoque());
            stmt.executeUpdate();

            // Recupera o id gerado pelo banco e coloca no objeto
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                produto.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao salvar produto: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public List<Produto> listar() throws SQLException {
        String sql = "SELECT * FROM produtos ORDER BY id";
        Connection conn = null;

        try {
            conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            List<Produto> produtos = new ArrayList<>();
            while (rs.next()) {
                produtos.add(new Produto(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getInt("estoque")));
            }
            return produtos;
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar produtos: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";
        Connection conn = null;

        try {
            conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            throw new SQLException("Erro ao excluir produto: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}

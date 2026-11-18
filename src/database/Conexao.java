package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Conexão com o banco de dados SQLite.
 *
 * O banco é um arquivo (estoque.db) criado na pasta do projeto.
 * Não é preciso instalar nenhum servidor de banco de dados.
 */
public class Conexao {

    // Endereço do banco: "jdbc:sqlite:" + nome do arquivo
    private static final String URL = "jdbc:sqlite:estoque.db";

    /** Abre e retorna uma conexão com o banco. */
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Cria as tabelas de produtos e clientes.
     * "IF NOT EXISTS" faz com que nada aconteça se a tabela já existir.
     */
    public static void criarTabela() throws SQLException {
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS produtos ("
                    + " id         INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " descricao  VARCHAR(100) NOT NULL,"
                    + " preco      REAL NOT NULL,"
                    + " estoque    INTEGER NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS clientes ("
                    + " id         INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " nome       VARCHAR(100) NOT NULL,"
                    + " telefone   VARCHAR(20) NOT NULL,"
                    + " email      VARCHAR(100) NOT NULL)");
        }
    }
}

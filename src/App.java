import database.Conexao;
import view.Menu;
// import view.ProdutoArrayRepository;

import java.sql.SQLException;

/**
 * Ponto de entrada do sistema.
 *
 * Aqui "montamos" as camadas:  CLI -> Service -> Repository -> Banco
 */
public class App {

    public static void main(String[] args) {
        try {
            // Cria a tabela, se ainda não existir
            Conexao.criarTabela();

            new Menu().iniciar();

        } catch (SQLException e) {
            System.out.println("Não foi possível criar o banco de dados: " + e.getMessage());
        }

    }
}

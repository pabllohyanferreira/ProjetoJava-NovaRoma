package model;

/**
 * Representa um produto.
 */
public class Produto {
    // ENCAPSULAMENTO: tudo private. Se fosse public, qualquer classe
    // poderia colocar, por exemplo, um preço negativo.
    private Integer id;
    private String descricao;
    private double preco;
    private int estoque;

    public Produto(Integer id, String descricao, double preco, int estoque) {
        // Usamos os setters no construtor para reaproveitar as validações
        this.id = id;
        this.setDescricao(descricao);
        this.setPreco(preco);
        this.setEstoque(estoque);
    }

    /** Retorna uma linha com os dados do produto. */
    public String exibirDetalhes() {
        return String.format("[%d] %-20s | R$ %8.2f | Estoque: %d", id, descricao, preco, estoque);
    }

    // ---------------- Getters e Setters (com validação) ----------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição do produto é obrigatória.");
        }
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        if (estoque < 0) {
            throw new IllegalArgumentException("O estoque não pode ser negativo.");
        }
        this.estoque = estoque;
    }
}

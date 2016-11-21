package br.edu.fpu.listadecompras.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nome;
    private Unidade unidade;
    private Double quantidade;
    private Double preco;

    public String getCodigo() {
        return codigo;
    }

    public Produto setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Produto setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public Produto setUnidade(Unidade unidade) {
        this.unidade = unidade;
        return this;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public Produto setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public Double getPreco() {
        return preco;
    }

    public Produto setPreco(Double preco) {
        this.preco = preco;
        return this;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", unidade=" + unidade +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                '}';
    }
}


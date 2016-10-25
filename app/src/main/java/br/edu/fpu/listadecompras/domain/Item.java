package br.edu.fpu.listadecompras.domain;

import java.io.Serializable;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String descricao;
    private Unidade unidade;
    private Double quantidade;
    private Status status;

    public Item(){}

    public Item(Long id, String descricao, Unidade unidade,
                Double quantidade, Status status) {
        this.id = id;
        this.descricao = descricao;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", unidade=" + unidade +
                ", quantidade=" + quantidade +
                ", status=" + status +
                '}';
    }
}

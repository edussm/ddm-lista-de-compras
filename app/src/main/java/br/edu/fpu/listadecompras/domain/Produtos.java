package br.edu.fpu.listadecompras.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Produtos {
    private List<Produto> content;

    public List<Produto> getContent() {
        return content;
    }

    public void setContent(List<Produto> content) {
        this.content = content;
    }
}

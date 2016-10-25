package br.edu.fpu.listadecompras.domain;

public enum Unidade {
    LITROS("l"), QUILOS("kg");

    private String texto;
    private Unidade(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}

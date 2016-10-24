package br.edu.fpu.listadecompras;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repositorio {

    private static List<Map<String, Object>> itens = new ArrayList<>();

    public static List<Map<String, Object>> getItens() {
        return itens;
    }

    public static void addItem(Map<String, Object> item) {
        itens.add(item);
    }
}

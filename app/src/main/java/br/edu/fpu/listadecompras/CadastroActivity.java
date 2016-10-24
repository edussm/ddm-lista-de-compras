package br.edu.fpu.listadecompras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextQuantidade;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextQuantidade = (EditText) findViewById(R.id.editTextQuantidade);
        radioGroup = ((RadioGroup) findViewById(R.id.radioGroup));
        //carregarItens();
    }

    public void salvar(View view) {
        String nome = editTextNome.getText().toString();
        int quantidade  = Integer.parseInt(editTextQuantidade.getText().toString());
        String unidade;
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButtonLitros) {
            unidade = "L";
        } else {
            unidade = "kg";
        }
        adicionarItem(nome, quantidade, unidade);
        Intent intent = new Intent(this, ListaActivity.class);
        startActivity(intent);
    }

    private void adicionarItem(String descricao, int quantidade, String unidade) {
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("quantidade", quantidade);
        item.put("unidade", unidade);
        item.put("descricao", descricao);
        Repositorio.addItem(item);
    }

    private void carregarItens() {
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("quantidade", 1);
        item.put("unidade", "l");
        item.put("descricao","Leite");
        Repositorio.addItem(item);

        item = new HashMap<String, Object>();
        item.put("quantidade", 2);
        item.put("unidade", "kg");
        item.put("descricao","Feijao");
        Repositorio.addItem(item);

        for (int i = 0; i < 20; i++) {
            Repositorio.addItem(geraItem(i));
        }
    }

    String bebidas[] = {"Leite", "Refrigerante", "Suco", "Cerveja"};
    Random r = new Random(System.currentTimeMillis());


    private Map<String, Object> geraItem(int id) {

        Map<String, Object> item = new HashMap<String, Object>();
        item.put("id", id);
        item.put("quantidade", r.nextInt(5)+1);
        item.put("unidade", "litros");
        item.put("descricao",bebidas[r.nextInt(bebidas.length-1)]);
        return item;
    }
}

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

import br.edu.fpu.listadecompras.database.DBHandler;
import br.edu.fpu.listadecompras.domain.Item;
import br.edu.fpu.listadecompras.domain.Status;
import br.edu.fpu.listadecompras.domain.Unidade;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextQuantidade;
    private RadioGroup radioGroup;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextQuantidade = (EditText) findViewById(R.id.editTextQuantidade);
        radioGroup = ((RadioGroup) findViewById(R.id.radioGroup));

        dbHandler = new DBHandler(this);
    }

    public void salvar(View view) {
        String nome = editTextNome.getText().toString();
        Double quantidade  = Double.parseDouble(editTextQuantidade.getText().toString());
        Unidade unidade;

        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButtonLitros) {
            unidade = Unidade.LITROS;
        } else {
            unidade = Unidade.QUILOS;
        }

        adicionarItem(nome, quantidade, unidade);
        finish();
    }

    private void adicionarItem(String descricao, double quantidade, Unidade unidade) {
        Item item = new Item();
        item.setDescricao(descricao);
        item.setUnidade(Unidade.LITROS);
        item.setStatus(Status.PRECISA_COMPRAR);
        item.setQuantidade(quantidade);
        dbHandler.addItem(item);
    }

}

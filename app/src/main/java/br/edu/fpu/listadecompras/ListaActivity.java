package br.edu.fpu.listadecompras;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class ListaActivity extends ListActivity
        implements AdapterView.OnItemClickListener {

    private List<Map<String, Object>> itens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] de = {"id", "quantidade", "unidade", "descricao"};
        int[] para = {R.id.id, R.id.quantidade, R.id.unidade, R.id.descricao};

        SimpleAdapter adapter = new SimpleAdapter(this, listarItens(),
                R.layout.list_item, de, para);
        setListAdapter(adapter);

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        Map<String, Object> map = itens.get(position);
        String item = (String) map.get("descricao");
        Toast.makeText(this, "Item Selecionado: "+ item,Toast.LENGTH_SHORT).show();
    }

    private List<Map<String, Object>> listarItens() {
        itens = Repositorio.getItens();
        return itens;
    }
}

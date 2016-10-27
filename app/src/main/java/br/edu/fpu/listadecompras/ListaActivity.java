package br.edu.fpu.listadecompras;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.fpu.listadecompras.database.DBHandler;
import br.edu.fpu.listadecompras.domain.Item;

public class ListaActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private List<Map<String, Object>> itens;
    private DBHandler dbHandler;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista);
        listView = (ListView) findViewById(R.id.listView);

        String[] de = {"id", "quantidade", "unidade", "descricao"};
        int[] para = {R.id.id, R.id.quantidade, R.id.unidade, R.id.descricao};

        dbHandler = new DBHandler(this);

        SimpleAdapter adapter = new SimpleAdapter(this, listarItens(),
                R.layout.list_item, de, para);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        // Habilitar o context menu
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Selecione a ação");
        //groupId, itemId, order, title
        menu.add(0, 1, 0, "Editar");
        menu.add(0, 2, 1, "Comprar");
    }

    // Caso queira um clique simples no item
    @Override
    public void onItemClick(AdapterView<?> adapterView,
                            View view, int i, long l) {
        this.openContextMenu(view);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Map<String, Object> i = itens.get(info.position);

        if (item.getItemId() == 1) {
            Toast.makeText(getApplicationContext(),
                    "Editar " + i.get("descricao"), Toast.LENGTH_LONG).show();

        } else if (item.getItemId() == 2) {
            Toast.makeText(getApplicationContext(),
                    "Comprar " + i.get("descricao"), Toast.LENGTH_LONG).show();
        } else {
            return false;
        }
        return true;
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view,
//                            int position, long id) {
//        Map<String, Object> map = itens.get(position);
//        String item = (String) map.get("descricao");
//        Toast.makeText(this, "Item Selecionado: "+ item,Toast.LENGTH_SHORT).show();
//    }

    private List<Map<String, Object>> listarItens() {
        itens = new ArrayList<>();
        List<Item> l = dbHandler.getAllItens();

        for (Item i : l) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("id", i.getId());
            item.put("quantidade", i.getQuantidade());
            item.put("unidade", i.getUnidade().getTexto());
            item.put("descricao", i.getDescricao());
            item.put("status", i.getStatus());
            itens.add(item);
        }

        return itens;
    }
}

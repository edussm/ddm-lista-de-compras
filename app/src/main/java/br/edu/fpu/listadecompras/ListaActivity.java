package br.edu.fpu.listadecompras;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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

        dbHandler = new DBHandler(this);

        // Instancia o adapter e carrega os dados na ListView
        loadListView();

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
            // ID do elemento: i.get("id")
        } else if (item.getItemId() == 2) {
            Toast.makeText(getApplicationContext(),
                    "Comprar " + i.get("descricao"), Toast.LENGTH_LONG).show();

        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                navigateTo(CadastroActivity.class);
                return true;
            case R.id.action_buscar:
                navigateTo(BuscaProdutoRestActivity.class);
                return true;
            case R.id.action_listar:
                navigateTo(BuscaListaDeProdutosRestActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateTo(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadListView();
    }

    private void loadListView() {
        String[] de = {"id", "quantidade", "unidade", "descricao"};
        int[] para = {R.id.id, R.id.quantidade, R.id.unidade, R.id.descricao};

        itens = listarItens();

        SimpleAdapter adapter = new SimpleAdapter(this, itens,
                R.layout.list_item, de, para);
        listView.setAdapter(adapter);
    }


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

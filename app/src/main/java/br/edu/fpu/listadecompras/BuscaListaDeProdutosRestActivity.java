package br.edu.fpu.listadecompras;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.fpu.listadecompras.database.DBHandler;
import br.edu.fpu.listadecompras.domain.Item;
import br.edu.fpu.listadecompras.domain.Produto;
import br.edu.fpu.listadecompras.domain.Produtos;

public class BuscaListaDeProdutosRestActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private List<Map<String, Object>> itens;
    private ListView listView;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista);
        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(this);

        // Habilitar o context menu
        registerForContextMenu(listView);

        // Criar o dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Buscando ...");
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
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
        inflater.inflate(R.menu.menu_lista_busca, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_update:
                // Inicia a progress bar
                progressDialog.setProgress(0);
                progressDialog.show();

                new BuscarProdutosTask().execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setProgressPercent(Integer progress) {

        progressDialog.setProgress(progress);
    }

    private void updateScreen(List<Produto> produtos) {
        if (produtos != null) {
            itens = new ArrayList<>();
            for (Produto i : produtos) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("id", i.getCodigo());
                item.put("quantidade", i.getQuantidade());
                item.put("unidade", i.getUnidade().getTexto());
                item.put("descricao", i.getNome());
                itens.add(item);
            }

            String[] de = {"id", "quantidade", "unidade", "descricao"};
            int[] para = {R.id.id, R.id.quantidade, R.id.unidade, R.id.descricao};

            SimpleAdapter adapter = new SimpleAdapter(this, itens,
                    R.layout.list_item, de, para);
            listView.setAdapter(adapter);
        }
        progressDialog.dismiss();
    }


    private class BuscarProdutosTask extends
            AsyncTask<String, Integer, List<Produto>> {
        @Override
        protected List<Produto> doInBackground(String... params) {
            try {
                final String url =getResources().getString(R.string.web_service_root_url)+
                        "/produto";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters()
                        .add(new MappingJackson2HttpMessageConverter());
                Produtos produtos =
                        restTemplate.getForObject(url, Produtos.class);
                System.out.println(produtos);
                return produtos.getContent();
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }

        @Override
        protected void onPostExecute(List<Produto> produtos) {
            updateScreen(produtos);
        }

    }
}

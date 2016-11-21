package br.edu.fpu.listadecompras;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.edu.fpu.listadecompras.domain.Item;
import br.edu.fpu.listadecompras.domain.Produto;

public class RestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
    }

    public void buscar(View view) {
        EditText codigo = (EditText) findViewById(R.id.editTextCodigo);
        String c = codigo.getText().toString();

        HttpRequestTask task = new HttpRequestTask();
        task.execute(c);
    }

    private class HttpRequestTask extends
            AsyncTask<String, Void, Produto> {
        @Override
        protected Produto doInBackground(String... params) {
            try {
                final String url =
                        "http://192.168.32.45:8080/rest/produto/" + params[0];
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters()
                        .add(new MappingJackson2HttpMessageConverter());
                Produto produto =
                        restTemplate.getForObject(url, Produto.class);
                return produto;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Produto produto) {
            TextView codigo = (TextView) findViewById(R.id.textViewCodigo);
            TextView nome = (TextView) findViewById(R.id.textViewNome);
            TextView preco = (TextView) findViewById(R.id.textViewPreco);

            codigo.setText(produto.getCodigo());
            nome.setText(produto.getNome());
            preco.setText("R$ " + produto.getPreco());
        }

    }

}

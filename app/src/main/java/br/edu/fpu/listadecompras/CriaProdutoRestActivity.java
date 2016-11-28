package br.edu.fpu.listadecompras;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.edu.fpu.listadecompras.domain.Produto;
import br.edu.fpu.listadecompras.domain.Unidade;

public class CriaProdutoRestActivity extends AppCompatActivity {

    // Atividade: implementar uma interface para criar novos produtos (POST)
    // implemntar uma interface para atualizar produtos (PUT)
    // diretamente no webservice
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cria_produto_rest);
        Produto p = new Produto()
                .setCodigo("10")
                .setNome("Teste Post")
                .setPreco(10.99)
                .setQuantidade(1.0)
                .setUnidade(Unidade.LITROS);
        new CriarProdutoTask().execute(p);
    }

    // https://developer.android.com/reference/android/os/AsyncTask.html
    // android.os.AsyncTask<Params, Progress, Result>
    private class CriarProdutoTask extends
            AsyncTask<Produto, Integer, Produto> {
        @Override
        protected Produto doInBackground(Produto... params) {
            try {
                final String url =getResources().getString(R.string.web_service_root_url)+
                        "/produto";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters()
                        .add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                Produto retorno =
                        restTemplate.postForObject(url, params[0], Produto.class);

                return retorno;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Produto produto) {
            Toast.makeText(CriaProdutoRestActivity.this, "Produto inserido: " + produto, Toast.LENGTH_LONG).show();
        }

    }
}

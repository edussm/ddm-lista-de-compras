package br.edu.fpu.listadecompras;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import br.edu.fpu.listadecompras.domain.Item;
import br.edu.fpu.listadecompras.domain.Produto;
import br.edu.fpu.listadecompras.domain.Produtos;

public class BuscaProdutoRestActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.INVISIBLE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Buscando ...");
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);

    }

    public void buscar(View view) {
        EditText codigo = (EditText) findViewById(R.id.editTextCodigo);
        String c = codigo.getText().toString();

        if (c != null && !c.trim().isEmpty()) {
            // Fazer o teclado sumir da tela
            View v = this.getCurrentFocus();
            if (v != null) {
                InputMethodManager imm =
                        (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            // Inicia a progress bar
            progressDialog.setProgress(0);
            progressDialog.show();

            // Faz a busca
            new BuscarProdutoTask().execute(c);
        } else {
            Toast.makeText(this, "Favor inserir o código!!!", Toast.LENGTH_LONG).show();
        }

    }

    private void setProgressPercent(Integer progress) {

        progressDialog.setProgress(progress);
    }

    private void updateScreen(Produto produto) {
        if (produto != null) {
            TextView codigo = (TextView) findViewById(R.id.textViewCodigo);
            TextView nome = (TextView) findViewById(R.id.textViewNome);
            TextView preco = (TextView) findViewById(R.id.textViewPreco);

            codigo.setText(produto.getCodigo());
            nome.setText(produto.getNome());
            preco.setText("R$ " + produto.getPreco());
        }
        progressDialog.dismiss();
    }

    // https://developer.android.com/reference/android/os/AsyncTask.html
    // android.os.AsyncTask<Params, Progress, Result>
    private class BuscarProdutoTask extends
            AsyncTask<String, Integer, Produto> {
        @Override
        protected Produto doInBackground(String... params) {
            try {
                final String url =getResources().getString(R.string.web_service_root_url)+
                        "/produto/" + params[0];
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
        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }

        @Override
        protected void onPostExecute(Produto produto) {
            updateScreen(produto);
        }

    }

}

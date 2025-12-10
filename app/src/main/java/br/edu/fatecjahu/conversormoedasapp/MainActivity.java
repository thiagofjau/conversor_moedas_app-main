package br.edu.fatecjahu.conversormoedasapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText edtValorReal;
    private Spinner spinnerMoedas;
    private Button btnConverter;
    private TextView tvResultado;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar componentes da interface
        edtValorReal = findViewById(R.id.edtValorReal);
        spinnerMoedas = findViewById(R.id.spinnerMoedas);
        btnConverter = findViewById(R.id.btnConverter);
        tvResultado = findViewById(R.id.tvResultado);

        // Inicializar cliente HTTP
        client = new OkHttpClient();

        // Configurar Spinner com as opções de moedas
        String[] moedas = {"USD - Dólar Americano", "EUR - Euro", "GBP - Libra Esterlina", 
                          "JPY - Iene Japonês", "ARS - Peso Argentino", "CAD - Dólar Canadense"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 
                android.R.layout.simple_spinner_item, moedas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMoedas.setAdapter(adapter);

        // Configurar o botão de conversão
        btnConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                converterMoeda();
            }
        });
    }

    private void converterMoeda() {
        String valorTexto = edtValorReal.getText().toString().trim();

        // Validar entrada
        if (valorTexto.isEmpty()) {
            Toast.makeText(this, R.string.texto_msg_digite_um_valor_valido_em_reais, 
                    Toast.LENGTH_SHORT).show();
            return;
        }

        double valorReal;
        try {
            valorReal = Double.parseDouble(valorTexto);
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.texto_msg_digite_um_valor_valido_em_reais, 
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Obter código da moeda selecionada
        String moedaSelecionada = spinnerMoedas.getSelectedItem().toString();
        String codigoMoeda = moedaSelecionada.substring(0, 3);

        // Buscar taxa de câmbio
        buscarTaxaCambio(codigoMoeda, valorReal);
    }

    private void buscarTaxaCambio(final String codigoMoeda, final double valorReal) {
        // API de taxas de câmbio - AwesomeAPI (API brasileira gratuita)
        // Corrigido: deve ser de MOEDA para BRL (ex: USD-BRL)
        String url = "https://economia.awesomeapi.com.br/json/last/" + codigoMoeda + "-BRL";
        
        android.util.Log.d("ConversorApp", "=== INICIANDO CONVERSÃO ===");
        android.util.Log.d("ConversorApp", "Moeda: " + codigoMoeda);
        android.util.Log.d("ConversorApp", "Valor: R$ " + valorReal);
        android.util.Log.d("ConversorApp", "URL: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                android.util.Log.e("ConversorApp", "❌ ERRO NA REQUISIÇÃO!");
                android.util.Log.e("ConversorApp", "Tipo do erro: " + e.getClass().getName());
                android.util.Log.e("ConversorApp", "Mensagem: " + e.getMessage());
                e.printStackTrace();
                
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String mensagemErro = "Erro: " + e.getMessage();
                        Toast.makeText(MainActivity.this, mensagemErro, Toast.LENGTH_LONG).show();
                        android.util.Log.e("ConversorApp", "Toast mostrado ao usuário");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                android.util.Log.d("ConversorApp", "✅ Resposta recebida!");
                android.util.Log.d("ConversorApp", "Status Code: " + response.code());
                
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        android.util.Log.d("ConversorApp", "Response Body: " + responseBody);
                        
                        JSONObject jsonObject = new JSONObject(responseBody);
                        
                        // A API retorna a chave no formato "XXXBRL" (ex: "USDBRL")
                        String chave = codigoMoeda + "BRL";
                        android.util.Log.d("ConversorApp", "Buscando chave: " + chave);
                        
                        JSONObject cotacao = jsonObject.getJSONObject(chave);
                        
                        // Obter a taxa de câmbio (bid = taxa de venda)
                        double taxa = cotacao.getDouble("bid");
                        android.util.Log.d("ConversorApp", "Taxa encontrada: " + taxa);
                        
                        // Calcular conversão: Real para Moeda Estrangeira
                        // Dividir o valor em reais pela taxa (quantos reais = 1 moeda estrangeira)
                        double valorConvertido = valorReal / taxa;
                        android.util.Log.d("ConversorApp", "Valor convertido: " + valorConvertido);
                        
                        // Atualizar UI na thread principal
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DecimalFormat df = new DecimalFormat("#,##0.00");
                                String nomeMoeda = cotacao.optString("name", codigoMoeda);
                                
                                String resultado = "R$ " + df.format(valorReal) + " = " + 
                                        codigoMoeda + " " + df.format(valorConvertido) + 
                                        "\n\nTaxa: 1 " + codigoMoeda + " = R$ " + df.format(taxa);
                                
                                tvResultado.setText(resultado);
                            }
                        });
                    } catch (Exception e) {
                        android.util.Log.e("ConversorApp", "❌ ERRO AO PROCESSAR JSON!");
                        android.util.Log.e("ConversorApp", "Erro: " + e.getMessage());
                        e.printStackTrace();
                        
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, 
                                        "Erro ao processar resposta: " + e.getMessage(), 
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    android.util.Log.e("ConversorApp", "❌ RESPOSTA NÃO SUCESSO!");
                    android.util.Log.e("ConversorApp", "Status Code: " + response.code());
                    
                    final int statusCode = response.code();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String mensagem;
                            if (statusCode == 429) {
                                mensagem = "⚠️ Muitas tentativas! Aguarde 1 minuto e tente novamente.";
                            } else if (statusCode >= 500) {
                                mensagem = "❌ Servidor da API temporariamente indisponível. Tente em alguns minutos.";
                            } else if (statusCode == 404) {
                                mensagem = "❌ Moeda não encontrada. Erro no código da moeda.";
                            } else {
                                mensagem = "❌ Erro HTTP " + statusCode + ". Tente novamente.";
                            }
                            Toast.makeText(MainActivity.this, mensagem, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
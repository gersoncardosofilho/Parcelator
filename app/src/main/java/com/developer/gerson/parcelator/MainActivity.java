package com.developer.gerson.parcelator;


import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormatSymbols;
import android.os.Debug;
import android.support.design.widget.Snackbar;
import android.support.v4.math.MathUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.gerson.parcelator.Activity.MainActivity2;
import com.developer.gerson.parcelator.Model.Calculos;
import com.xw.repo.BubbleSeekBar;

import java.io.Console;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements ICalculos {

    private Button calcularButton, limparButton;
    private EditText
            etValorPrincipal,
            etTaxaDebito,
            etTaxaCredito,
            etTaxaParcelado;


    private TextView
            tvValorTotalDebito,
            tvValorTotalCredito,
            tvValorTotalParcelado,
            tvValorTarifaDebito,
            tvValorTarifaCredito,
            tvValorTarifaParcelado,
            tvValorParcela, tvValorPrincipal;

    private BubbleSeekBar bubbleSeekBarParcelas;

    private String valorPrincipal;

    private int quantidadeParcelas = 1;

    View rootView;

    NumberFormat nf;

    Context activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = getApplicationContext();

        Locale myLocale = new Locale("pt", "BR");
        nf = NumberFormat.getCurrencyInstance(myLocale);

        rootView = findViewById(R.id.myActivity);

        initializeComponents();
        bubbleSeekBarParcelas.setProgress(1);
        quantidadeParcelas = bubbleSeekBarParcelas.getProgress();

        doCalculation(quantidadeParcelas);

        bubbleSeekBarParcelas.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

                if (etValorPrincipal.getText().toString().equals(""))
                {
                    return;
                } else {
                    AtualizaValores(progress);
                    Log.i("Parcelas atualizadas: ", String.valueOf(progress));
                    super.onProgressChanged(bubbleSeekBar, progress, progressFloat);
                }
            }
        });

        etValorPrincipal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (hasWindowFocus())
                    bubbleSeekBarParcelas.setProgress(1);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.menu_parcelator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:
                compartilhar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void compartilhar(){
        if (tvValorTotalDebito.getText().toString() == "")
            return;

        Double _valorPrincipal = GetValorPrincipal();

        Calculos calculos = new Calculos();
        calculos.setValorPrincipal(nf.format(_valorPrincipal));
        calculos.setTarifaDebito(tvValorTarifaDebito.getText().toString());
        calculos.setTarifaCredito(tvValorTarifaCredito.getText().toString());
        calculos.setTarifaParcelado(tvValorTarifaParcelado.getText().toString());
        calculos.setValorDebito(tvValorTotalDebito.getText().toString());
        calculos.setValorCredito(tvValorTotalCredito.getText().toString());
        calculos.setValorParcelado(tvValorTotalParcelado.getText().toString());
        calculos.setQuantidadeDeParcelas(bubbleSeekBarParcelas.getProgress());
        calculos.setValorDaParcela(tvValorParcela.getText().toString());

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("calculos", (Serializable) calculos);
        startActivity(intent);
    }

    private void initializeComponents() {

        etValorPrincipal = findViewById(R.id.editTextValorPrincipal);
        etTaxaDebito = findViewById(R.id.editTextTaxaDebito);
        tvValorTarifaDebito = findViewById(R.id.textViewValorTarifaDebito);
        tvValorTotalDebito = findViewById(R.id.textViewValorTotalDebito);
        etTaxaCredito = findViewById(R.id.editTextTaxaCredito);
        tvValorTarifaCredito = findViewById(R.id.textViewValorTarifaCredito);
        tvValorTotalCredito = findViewById(R.id.textViewValorTotalCredito);
        tvValorTotalParcelado = findViewById(R.id.textViewValorTotalParcelado);
        tvValorTarifaParcelado = findViewById(R.id.textViewValorTarifaParcelado);
        etTaxaParcelado = findViewById(R.id.editTextTaxaParcelado);
        calcularButton = findViewById(R.id.button);
        bubbleSeekBarParcelas = findViewById(R.id.bubleSeekParcelas);
        tvValorParcela = findViewById(R.id.textViewValorParcela);
        limparButton = findViewById(R.id.buttonLimpar);
    }

    private void limparCampos(){
        etValorPrincipal.setText("");
        tvValorTotalCredito.setText("");
        tvValorTotalDebito.setText("");
        tvValorTotalParcelado.setText("");
        tvValorTarifaDebito.setText("");
        tvValorTarifaCredito.setText("");
        tvValorTarifaParcelado.setText("");
        bubbleSeekBarParcelas.setProgress(1);
    }



    private void AtualizaValores(int quantidadeParcelas) {

            //Debito
            Double taxaDebito = Double.parseDouble(etTaxaDebito.getText().toString());
            Double valorPrincipal = GetValorPrincipal();
            Double formulaDebito = GetFormula(taxaDebito);
            Double valorTarifaDebito = GetValorTaxa(taxaDebito, valorPrincipal);
            Double valorTotal = valorPrincipal + valorTarifaDebito;



            Log.i("Taxa: ===>", taxaDebito.toString());
            Log.i("Valor principal ===>", GetValorPrincipal().toString());
            Log.i("Tarifa debito ===>", valorTarifaDebito.toString());
            Log.i("Formula ===>", formulaDebito.toString());
            Log.i("Valor total===>", valorTotal.toString());


            tvValorTarifaDebito.setText(nf.format(valorTarifaDebito));
            tvValorTotalDebito.setText(nf.format(valorTotal));

            //Credito
            Double taxaCredito = Double.parseDouble(etTaxaCredito.getText().toString());
            Double formulaCredito = GetFormula(taxaCredito);
            Double valorTarifaCredito = GetValorTaxa(taxaCredito, valorPrincipal);
            Double valorTotalCredito = valorPrincipal + valorTarifaCredito;


            tvValorTarifaCredito.setText(nf.format(valorTarifaCredito));
            tvValorTotalCredito.setText(nf.format(valorTotalCredito));
//
//        //Parcelado
            Double taxaParcelado = Double.parseDouble(etTaxaParcelado.getText().toString());
            Double formulaParcelado = (GetFormula(taxaParcelado) * quantidadeParcelas);
            Double valorTarifaParcelado = (GetValorTaxa(taxaParcelado, valorPrincipal) * quantidadeParcelas);
            Double valorTotalParcelado = (valorPrincipal + valorTarifaParcelado);
            Double valorParcela = valorTotalParcelado / quantidadeParcelas;


            tvValorTarifaParcelado.setText(nf.format(valorTarifaParcelado));
            tvValorTotalParcelado.setText(nf.format(valorTotalParcelado));
            tvValorParcela.setText(nf.format(valorParcela));

    }

    private void doCalculation(final int parcelas) {

        valorPrincipal = etValorPrincipal.getText().toString();

        calcularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etValorPrincipal.getText().toString().equals("")) {
                    return;
                } else {

                    AtualizaValores(parcelas);

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                return;
            }
        });

        limparButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparCampos();
            }
        });

    }



    @Override
    public Double GetFormula(Double taxa) {
        Double formula = 0.00d;
        formula = (100 - taxa) / 100;
        return formula;
    }

    @Override
    public Double GetValorPrincipal() {
        Double valorPrincipal = 0.00d;
        valorPrincipal = Double.parseDouble(etValorPrincipal.getText().toString());
        return valorPrincipal;
    }

    @Override
    public Double GetValorTaxa(Double taxa, Double valorTotal) {

        Double valorTaxa = 0.00d;
        valorTaxa = (valorTotal * taxa) / 100;
        return valorTaxa;
    }

    @Override
    public Double GetValorParcela(Double valorTotal, int quantidadeParcelas) {
        return 0.1;
    }
}



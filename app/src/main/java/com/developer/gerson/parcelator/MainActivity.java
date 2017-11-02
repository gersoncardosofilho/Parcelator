package com.developer.gerson.parcelator;


import android.content.Context;
import android.icu.text.DecimalFormatSymbols;
import android.os.Debug;
import android.support.design.widget.Snackbar;
import android.support.v4.math.MathUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xw.repo.BubbleSeekBar;

import java.io.Console;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements ICalculos {

    private Button calcularButton;
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
            tvValorParcela;

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
        quantidadeParcelas = bubbleSeekBarParcelas.getProgress();

        doCalculation(quantidadeParcelas);

        bubbleSeekBarParcelas.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(int progress, float progressFloat) {
                super.onProgressChanged(progress, progressFloat);

                AtualizaValores(progress);

                Log.i("Parcelas atualizadas: ", String.valueOf(progress));
            }
        });

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
    }

    private void AtualizaValores(int quantidadeParcelas) {
        //Debito

        Double taxaDebito = Double.parseDouble(etTaxaDebito.getText().toString());
        Double valorPrincipal = GetValorPrincipal();
        Double formulaDebito = GetFormula(taxaDebito);
        Double valorTotal = valorPrincipal / formulaDebito;
        Double valorTarifaDebito = GetValorTaxa(taxaDebito, valorPrincipal);

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
        Double valorTotalCredito = valorPrincipal / formulaCredito;
        Double valorTarifaCredito = GetValorTaxa(taxaCredito, valorPrincipal);

        tvValorTarifaCredito.setText(nf.format(valorTarifaCredito));
        tvValorTotalCredito.setText(nf.format(valorTotalCredito));
//
//        //Parcelado
        Double taxaParcelado = Double.parseDouble(etTaxaParcelado.getText().toString());
        Double formulaParcelado = GetFormula(taxaParcelado);
        Double valorTotalParcelado = valorPrincipal / formulaParcelado;
        Double valorTarifaParcelado = GetValorTaxa(taxaParcelado, valorPrincipal);
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



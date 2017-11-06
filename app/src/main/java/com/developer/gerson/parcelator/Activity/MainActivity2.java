package com.developer.gerson.parcelator.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.developer.gerson.parcelator.Model.Calculos;
import com.developer.gerson.parcelator.R;

public class MainActivity2 extends AppCompatActivity {

    Button btnCompartilhar;

    TextView
            valorPrincipal,
            valorDebito,
            valorCredito,
            valorParcelado,
            quantidadeDeParcelas,
            valorDaParcela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        Calculos calculos = (Calculos) intent.getSerializableExtra("calculos");

        InitializeComponents();

        PreencheCampos(calculos);

    }

    private void InitializeComponents(){
        btnCompartilhar = findViewById(R.id.buttonCompartilhar);
        valorPrincipal = findViewById(R.id.ma2EditTextValorPrincipal);
        valorDebito = findViewById(R.id.ma2TextViewValorDebito);
        valorCredito = findViewById(R.id.ma2TextViewValorCredito);
        valorParcelado = findViewById(R.id.ma2TextViewValorParcelado);
        quantidadeDeParcelas = findViewById(R.id.ma2TextViewQuantidadeParcelas);
        valorDaParcela = findViewById(R.id.ma2TextViewValorDaParcela);
        valorDaParcela = findViewById(R.id.ma2TextViewValorDaParcela);
    }

    private void PreencheCampos(Calculos calculos){
        valorPrincipal.setText(calculos.getValorPrincipal());
        valorDebito.setText(calculos.getValorDebito());
        valorCredito.setText(calculos.getValorCredito());
        valorParcelado.setText(calculos.getValorParcelado());
        quantidadeDeParcelas.setText(Integer.toString(calculos.getQuantidadeDeParcelas()));
        valorDaParcela.setText(calculos.getValorDaParcela());
    }
}

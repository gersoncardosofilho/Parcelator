package com.developer.gerson.parcelator.Model;

import java.io.Serializable;

/**
 * Created by gerso on 11/2/2017.
 */

public class Calculos implements Serializable {

    private String ValorPrincipal;
    private String ValorDebito;
    private String ValorCredito;
    private String ValorParcelado;
    private int QuantidadeDeParcelas;
    private String ValorDaParcela;
    private String TarifaDebito;
    private String TarifaCredito;
    private String TarifaParcelado;



    public String getValorDebito() {
        return ValorDebito;
    }

    public void setValorDebito(String valorDebito) {
        ValorDebito = valorDebito;
    }

    public String getValorCredito() {
        return ValorCredito;
    }

    public void setValorCredito(String valorCredito) {
        ValorCredito = valorCredito;
    }

    public String getValorParcelado() {
        return ValorParcelado;
    }

    public void setValorParcelado(String valorParcelado) {
        ValorParcelado = valorParcelado;
    }

    public int getQuantidadeDeParcelas() {
        return QuantidadeDeParcelas;
    }

    public void setQuantidadeDeParcelas(int quantidadeDeParcelas) {
        QuantidadeDeParcelas = quantidadeDeParcelas;
    }

    public String getValorDaParcela() {
        return ValorDaParcela;
    }

    public void setValorDaParcela(String valorDaParcela) {
        ValorDaParcela = valorDaParcela;
    }

    public String getValorPrincipal() {
        return ValorPrincipal;
    }

    public void setValorPrincipal(String valorPrincipal) {
        ValorPrincipal = valorPrincipal;
    }

    public String getTarifaDebito() {
        return TarifaDebito;
    }

    public void setTarifaDebito(String tarifaDebito) {
        TarifaDebito = tarifaDebito;
    }

    public String getTarifaCredito() {
        return TarifaCredito;
    }

    public void setTarifaCredito(String tarifaCredito) {
        TarifaCredito = tarifaCredito;
    }

    public String getTarifaParcelado() {
        return TarifaParcelado;
    }

    public void setTarifaParcelado(String tarifaParcelado) {
        TarifaParcelado = tarifaParcelado;
    }
}

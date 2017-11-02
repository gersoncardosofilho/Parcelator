package com.developer.gerson.parcelator;

/**
 * Created by Gerson on 10/31/2017.
 */

public interface ICalculos {
    Double GetFormula(Double taxa);
    Double GetValorPrincipal();
    Double GetValorTaxa(Double taxa, Double valorTotal);
    Double GetValorParcela(Double valorTotal, int quantidadeParcelas);
}

package com.eliasfs06.tinktime.model;

public abstract class ValidadorDeIdeia {

    public boolean validar(String ideia){
        return validarCamposComuns(ideia) && validarCamposEspecificos(ideia);
    }

    public boolean validarCamposComuns(String ideia){
        return true;
    }

    public abstract boolean validarCamposEspecificos(String ideia);
}

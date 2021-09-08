package br.com.zupacademy.fpsaraiva.microservicepropostas.avisoviagem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultadoNotificacaoViagem {

    private String resultado;

    public ResultadoNotificacaoViagem(@JsonProperty("resultado") String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

}

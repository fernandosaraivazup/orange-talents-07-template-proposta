package br.com.zupacademy.fpsaraiva.microservicepropostas.bloqueiocartao;

import javax.validation.constraints.NotNull;

public class ResultadoNotificacaoBloqueio {

    @NotNull
    private StatusCartao resultado;

    public ResultadoNotificacaoBloqueio(StatusCartao resultado) {
        this.resultado = resultado;
    }

    public StatusCartao getResultado() {
        return resultado;
    }

}

package br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ResultadoAnalise {

    @NotBlank
    private String documento;

    @NotBlank
    private String nome;

    @NotNull
    private StatusTransacaoPagamento resultadoSolicitacao;

    @NotBlank
    private String idProposta;

    public ResultadoAnalise(String documento, String nome, StatusTransacaoPagamento resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public StatusTransacaoPagamento getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

}

package br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta;

import br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta.Proposta;

import java.time.LocalDateTime;

public class CartaoResponse {

    private String id;

    private LocalDateTime emitidoEm;

    private String titular;

    private Integer limite;

    private VencimentoResponse vencimento;

    private String idProposta;

    public CartaoResponse(String id, LocalDateTime emitidoEm, String titular, Integer limite, VencimentoResponse vencimento, String idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.vencimento = vencimento;
        this.idProposta = idProposta;
    }

    public Cartao toModel(Proposta proposta) {
        Vencimento vencimento = this.vencimento.toModel();
        return new Cartao(id, emitidoEm, titular, limite, vencimento, proposta);
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public Integer getLimite() {
        return limite;
    }

    public VencimentoResponse getVencimento() {
        return vencimento;
    }

    public String getIdProposta() {
        return idProposta;
    }

}

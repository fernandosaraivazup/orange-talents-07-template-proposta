package br.com.zupacademy.fpsaraiva.microservicepropostas.acompanhamentoproposta;

import br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante.StatusProposta;
import br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta.Proposta;

import java.math.BigDecimal;

public class PropostaDetalheResponse {

    private String document;
    private String email;
    private String address;
    private String name;
    private BigDecimal income;
    private StatusProposta status;

    public PropostaDetalheResponse(Proposta proposta) {
        this.status = proposta.getStatus();
        this.document = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.name = proposta.getNome();
        this.address = proposta.getEndereco();
        this.income = proposta.getSalario();
    }

    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public StatusProposta getStatus() {
        return status;
    }

}

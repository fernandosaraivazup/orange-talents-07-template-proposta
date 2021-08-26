package br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante;

import br.com.zupacademy.fpsaraiva.microservicepropostas.compartilhado.validacoes.ValidDocument;
import br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta.Proposta;

import javax.validation.constraints.NotBlank;

public class SolicitacaoAnaliseRequest {

    @NotBlank
    @ValidDocument
    private String documento;

    @NotBlank
    private String nome;

    @NotBlank
    private String idProposta;

    public SolicitacaoAnaliseRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId().toString();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

}

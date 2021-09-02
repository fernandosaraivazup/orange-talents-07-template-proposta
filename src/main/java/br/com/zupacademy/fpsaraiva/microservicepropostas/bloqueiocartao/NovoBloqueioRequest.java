package br.com.zupacademy.fpsaraiva.microservicepropostas.bloqueiocartao;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;

import javax.validation.constraints.NotBlank;

public class NovoBloqueioRequest {

    @NotBlank
    private String userAgent;

    @NotBlank
    private String ipClient;

    public NovoBloqueioRequest(String userAgent, String ipClient) {
        this.userAgent = userAgent;
        this.ipClient = ipClient;
    }

    public Bloqueio toModel(Cartao cartao) {
        return new Bloqueio(userAgent, ipClient, cartao);
    }

}

package br.com.zupacademy.fpsaraiva.microservicepropostas.bloqueiocartao;

import javax.validation.constraints.NotBlank;

public class NotificacaoBloqueioRequest {

    @NotBlank
    private String sistemaResponsavel;

    public NotificacaoBloqueioRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

}

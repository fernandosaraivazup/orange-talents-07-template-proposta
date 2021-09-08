package br.com.zupacademy.fpsaraiva.microservicepropostas.avisoviagem;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destinoViagem;

    @NotNull
    @FutureOrPresent
    private LocalDate dataTerminoViagem;

    public AvisoViagemRequest(String destinoViagem, LocalDate dataTerminoViagem) {
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = dataTerminoViagem;
    }

    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDate getTerminoViagem() {
        return dataTerminoViagem;
    }

    public AvisoViagem toModel(String userAgent, String ipClient, Cartao cartao) {
        return new AvisoViagem(destinoViagem, dataTerminoViagem, userAgent, ipClient, cartao);
    }

}

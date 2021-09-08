package br.com.zupacademy.fpsaraiva.microservicepropostas.avisoviagem;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NotificacaoAvisoRequest {

    @NotBlank
    private String destino;

    @NotNull
    @FutureOrPresent
    private LocalDate validoAte;

    public NotificacaoAvisoRequest(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}

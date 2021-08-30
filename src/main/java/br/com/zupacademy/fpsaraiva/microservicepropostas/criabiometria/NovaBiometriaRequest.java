package br.com.zupacademy.fpsaraiva.microservicepropostas.criabiometria;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.Base64;


public class NovaBiometriaRequest {

    @NotBlank
    private String fingerprint;

    public NovaBiometriaRequest(@JsonProperty("fingerprint") String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(Base64.getEncoder().encodeToString(this.fingerprint.getBytes()), cartao);
    }

    public String getFingerprint() {
        return fingerprint;
    }

}

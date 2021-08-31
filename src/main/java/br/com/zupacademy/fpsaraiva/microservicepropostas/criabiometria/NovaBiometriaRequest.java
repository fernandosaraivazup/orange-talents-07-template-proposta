package br.com.zupacademy.fpsaraiva.microservicepropostas.criabiometria;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Base64;

public class NovaBiometriaRequest {

    @NotBlank
    //TODO: validacao base64 entrada - refactor
    //@Pattern(regexp = "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$")
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

package br.com.zupacademy.fpsaraiva.microservicepropostas.associacarteiradigital;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AssociaCarteiraRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String carteira;

    public AssociaCarteiraRequest(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }

    public Carteira toModel(Cartao cartao) {
        return new Carteira(email, carteira, cartao);
    }

}

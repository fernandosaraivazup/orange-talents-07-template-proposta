package br.com.zupacademy.fpsaraiva.microservicepropostas.associacarteiradigital;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AssociaCarteiraTemplate {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String carteira;

    public AssociaCarteiraTemplate(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }

}

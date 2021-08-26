package br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta;

import br.com.zupacademy.fpsaraiva.microservicepropostas.compartilhado.validacoes.ValidDocument;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

public class NovaPropostaRequest {

    @NotNull
    private UUID id;

    @NotBlank
    @ValidDocument
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @PositiveOrZero
    private BigDecimal salario;

    public NovaPropostaRequest(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.id = UUID.randomUUID();
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toModel() {
        return new Proposta(id, documento, email, nome, endereco, salario);
    }

}

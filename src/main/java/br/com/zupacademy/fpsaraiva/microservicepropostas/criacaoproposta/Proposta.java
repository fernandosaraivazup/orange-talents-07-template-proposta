package br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta;

import br.com.zupacademy.fpsaraiva.microservicepropostas.compartilhado.validacoes.ValidDocument;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_proposta")
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    @NotBlank
    @ValidDocument
    @Column(name = "document", nullable = false)
    private String documento;

    @NotBlank
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String nome;

    @NotBlank
    @Column(name = "address", nullable = false)
    private String endereco;

    @NotNull
    @PositiveOrZero
    @Column(name = "income", nullable = false)
    private BigDecimal salario;

    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public UUID getId() {
        return id;
    }

    public boolean possuiDocumentoCadastrado(PropostaRepository propostaRepository) {
        return propostaRepository.existsByDocumento(documento);
    }

}

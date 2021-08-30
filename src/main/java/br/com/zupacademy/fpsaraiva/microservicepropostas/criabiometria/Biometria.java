package br.com.zupacademy.fpsaraiva.microservicepropostas.criabiometria;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_biometria")
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fingerprint;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime dataCadastro;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cartao cartao;

    @Deprecated
    public Biometria() {
    }

    public Biometria(String fingerprint, Cartao cartao) {
        this.id = id;
        this.fingerprint = fingerprint;
        this.cartao = cartao;
        this.dataCadastro = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

}

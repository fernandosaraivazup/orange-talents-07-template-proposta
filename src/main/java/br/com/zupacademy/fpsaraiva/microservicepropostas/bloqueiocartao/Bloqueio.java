package br.com.zupacademy.fpsaraiva.microservicepropostas.bloqueiocartao;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_bloqueio")
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "user_agent", nullable = false)
    private String userAgent;

    @NotBlank
    @Column(name = "client_ip", nullable = false)
    private String ipClient;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime dataBloqueio;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cartao cartao;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(String userAgent, String ipClient, Cartao cartao) {
        this.id = id;
        this.userAgent = userAgent;
        this.ipClient = ipClient;
        this.cartao = cartao;
        this.dataBloqueio = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

}

package br.com.zupacademy.fpsaraiva.microservicepropostas.avisoviagem;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_aviso_viagem")
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "destination", nullable = false)
    private String destinoViagem;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate dataTerminoViagem;

    @NotBlank
    @Column(name = "user_agent", nullable = false)
    private String userAgent;

    @NotBlank
    @Column(name = "client_ip", nullable = false)
    private String ipClient;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime dataAviso;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cartao cartao;

    @Deprecated
    public AvisoViagem() {}

    public AvisoViagem(String destinoViagem, LocalDate dataTerminoViagem, String userAgent, String ipClient, Cartao cartao) {
        this.id = id;
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = dataTerminoViagem;
        this.userAgent = userAgent;
        this.ipClient = ipClient;
        this.cartao = cartao;
        this.dataAviso = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

}

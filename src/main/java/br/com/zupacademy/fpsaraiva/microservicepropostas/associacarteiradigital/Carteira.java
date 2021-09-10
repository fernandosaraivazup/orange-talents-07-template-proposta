package br.com.zupacademy.fpsaraiva.microservicepropostas.associacarteiradigital;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_carteira_digital")
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(name = "wallet_name", nullable = false)
    private String carteira;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime dataAssociacaoCarteira;

    @NotBlank
    @Column(name = "association_status", nullable = false)
    private String statusAssociacao;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cartao cartao;

    @Deprecated
    public Carteira() {
    }

    public Carteira(String email, String carteira, Cartao cartao) {
        this.id = id;
        this.email = email;
        this.carteira = carteira;
        this.dataAssociacaoCarteira = LocalDateTime.now();
        this.cartao = cartao;
        this.statusAssociacao = "INEXISTENTE";
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }

    public void registraRespostaClienteExterno(String resultado) {
        this.statusAssociacao = resultado;
    }

}

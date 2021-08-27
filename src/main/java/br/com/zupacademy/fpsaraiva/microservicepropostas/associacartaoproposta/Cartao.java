package br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta;

import br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta.Proposta;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cartao")
public class Cartao {

    @Id
    private String id;

    @Column(name = "issued_date")
    private LocalDateTime emitidoEm;

    @Column(name = "owner")
    private String titular;

    @Column(name = "card_limit")
    private Integer limite;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Vencimento vencimento;

    @OneToOne
    @NotNull
    @JoinColumn(name = "idProposta")
    private Proposta proposta;

    @Deprecated
    public Cartao() {
    }

    public Cartao(String id, LocalDateTime emitidoEm, String titular, Integer limite, Vencimento vencimento, Proposta proposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.vencimento = vencimento;
        this.proposta = proposta;
    }

}

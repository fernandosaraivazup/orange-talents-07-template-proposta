package br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacarteiradigital.Carteira;
import br.com.zupacademy.fpsaraiva.microservicepropostas.avisoviagem.AvisoViagem;
import br.com.zupacademy.fpsaraiva.microservicepropostas.bloqueiocartao.*;
import br.com.zupacademy.fpsaraiva.microservicepropostas.criabiometria.Biometria;
import br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta.Proposta;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @NotNull
    @Column(name = "card_status")
    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;

    @OneToOne
    @NotNull
    @JoinColumn(name = "idProposta")
    private Proposta proposta;

    @OneToMany(mappedBy = "cartao")
    private List<Biometria> listaBiometria;

    @OneToMany(mappedBy = "cartao")
    private List<Bloqueio> listaBloqueio = new ArrayList<>();

    @OneToMany(mappedBy = "cartao")
    private List<AvisoViagem> avisoViagemList = new ArrayList<>();

    @OneToMany(mappedBy = "cartao")
    private List<Carteira> carteiraDigitalList = new ArrayList<>();

    @Transient
    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

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
        this.statusCartao = StatusCartao.ATIVO;
    }

    public String getId() {
        return id;
    }

    public StatusCartao getBloqueado() {
        return statusCartao;
    }

    public boolean notificaBloqueioASistemaExterno(NotificaBloqueioClient notificaBloqueioClient) {
        try {
            notificaBloqueioClient.notificarBloqueio(this.id, new NotificacaoBloqueioRequest("microsservice-propostas"));
            this.statusCartao = StatusCartao.BLOQUEADO;
            logger.info("Bloqueio do cartão realizado com SUCESSO.");
            return true;
        } catch (FeignException.FeignClientException.UnprocessableEntity unprocessableEntity) {
            this.statusCartao = StatusCartao.BLOQUEADO;
            logger.warn("Bloqueio do cartão retornou FALHA. MOTIVO: cartão já está bloqueado.");
            return true;
        } catch (FeignException e) {
            this.statusCartao = StatusCartao.AGUARDANDO_BLOQUEIO;
            logger.error("Bloqueio não realizado. Motivo: falha de comunicação com a API de cartões.");
            return false;
        }
    }

}

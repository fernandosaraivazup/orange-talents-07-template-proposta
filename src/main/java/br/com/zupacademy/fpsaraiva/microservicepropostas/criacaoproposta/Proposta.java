package br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;
import br.com.zupacademy.fpsaraiva.microservicepropostas.configs.criptography.Criptografia;
import br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante.AnaliseFinanceiraClient;
import br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante.ResultadoAnalise;
import br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante.SolicitacaoAnaliseRequest;
import br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante.StatusProposta;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_proposta")
public class Proposta {

    @Id
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "document", nullable = false)
    private String documento;

    @NotBlank
    @Column(name = "document_hash", nullable = false)
    private String documentoHash;

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

    @Enumerated(EnumType.STRING)
    private StatusProposta status;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "proposta")
    private Cartao cartao;

    @Transient
    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    @Deprecated
    public Proposta() {
    }

    public Proposta(UUID id, String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.id = id;
        this.documento = Criptografia.encriptar(documento);
        this.documentoHash = Criptografia.gerarHash(documento);
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.status = StatusProposta.AGUARDANDO_APROVACAO;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getDocumento() {
        return Criptografia.decriptar(documento);
    }

    public String getEmail() { return email; }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public boolean possuiDocumentoCadastrado(PropostaRepository propostaRepository) {
        return propostaRepository.existsByDocumentoHash(documentoHash);
    }

    public void analisaRestricoesFinanceirasSolicitanteProposta(AnaliseFinanceiraClient analiseFinanceiraClient, Proposta proposta) {
        try{
            SolicitacaoAnaliseRequest solicitacaoAnalise = new SolicitacaoAnaliseRequest(proposta);
            ResultadoAnalise solicitaAnaliseRequest = analiseFinanceiraClient.analisarProposta(solicitacaoAnalise);

            this.status = solicitaAnaliseRequest.getResultadoSolicitacao().getStatusTransacaoPagamento();
            logger.info("Proposta 'id={}' ANALISADA.", proposta.getId());
        } catch (FeignException.FeignClientException.UnprocessableEntity unprocessableEntity){
            this.status = StatusProposta.NAO_ELEGIVEL;
            logger.info("Proposta 'id={}' ANALISADA.", proposta.getId());
        } catch (FeignException e){
            logger.error("Proposta 'id={}' NÂO ANALISADA. Motivo: falha de comunicação com a API de análise.", proposta.getId());
        }
    }

}

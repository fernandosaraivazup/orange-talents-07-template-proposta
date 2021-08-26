package br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta;

import br.com.zupacademy.fpsaraiva.microservicepropostas.compartilhado.validacoes.ValidDocument;
import br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante.AnaliseFinanceiraClient;
import br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante.ResultadoAnalise;
import br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante.SolicitacaoAnaliseRequest;
import br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante.StatusProposta;
import feign.FeignException;
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

    @Enumerated(EnumType.STRING)
    private StatusProposta status;

    @Deprecated
    public Proposta() {
    }

    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.id = UUID.randomUUID();
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.status = StatusProposta.AGUARDANDO_APROVACAO;
    }

    public UUID getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public boolean possuiDocumentoCadastrado(PropostaRepository propostaRepository) {
        return propostaRepository.existsByDocumento(documento);
    }

    public void analisaRestricoesFinanceirasSolicitanteProposta(AnaliseFinanceiraClient analiseFinanceiraClient, Proposta proposta) {
        try{
            SolicitacaoAnaliseRequest solicitacaoAnalise = new SolicitacaoAnaliseRequest(proposta);
            ResultadoAnalise solicitaAnaliseRequest = analiseFinanceiraClient.analisarProposta(solicitacaoAnalise);

            this.status = solicitaAnaliseRequest.getResultadoSolicitacao().getStatusTransacaoPagamento();

        } catch (FeignException.FeignClientException.UnprocessableEntity unprocessableEntity){
            this.status = StatusProposta.NAO_ELEGIVEL;
        } catch (FeignException e){
            System.out.println("ERRO! Houve uma falha de comunicação com o sistema de análise financeira. Não foi possível realizar a análise da proposta.");
        }
    }

}

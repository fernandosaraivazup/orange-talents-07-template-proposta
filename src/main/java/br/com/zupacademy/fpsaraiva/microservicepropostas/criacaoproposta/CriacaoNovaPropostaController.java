package br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta;

import br.com.zupacademy.fpsaraiva.microservicepropostas.compartilhado.excessoes.ApiErroException;
import br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante.AnaliseFinanceiraClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
public class CriacaoNovaPropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private AnaliseFinanceiraClient analiseFinanceiraClient;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    @PostMapping("/propostas")
    public ResponseEntity<?> criarProposta(@RequestBody @Valid NovaPropostaRequest novaPropostaRequest,
                                           UriComponentsBuilder uriComponentsBuilder) {
        Proposta novaProposta = novaPropostaRequest.toModel();

        if(novaProposta.possuiDocumentoCadastrado(propostaRepository)) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Não é possível criar mais de uma proposta com o mesmo documento.");
        }

        novaProposta.analisaRestricoesFinanceirasSolicitanteProposta(analiseFinanceiraClient, novaProposta);
        propostaRepository.save(novaProposta);

        logger.info("Proposta 'id={}' CRIADA com sucesso!", novaProposta.getId());

        return ResponseEntity.created(uriComponentsBuilder
                .path("/propostas/{id}")
                .buildAndExpand(novaProposta.getId())
                .toUri()).build();
    }

}

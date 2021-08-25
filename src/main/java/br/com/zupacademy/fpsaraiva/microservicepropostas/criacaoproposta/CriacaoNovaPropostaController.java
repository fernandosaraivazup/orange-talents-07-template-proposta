package br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta;

import br.com.zupacademy.fpsaraiva.microservicepropostas.compartilhado.excessoes.ApiErroException;
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

    @PostMapping("/propostas")
    public ResponseEntity<?> criarProposta(@RequestBody @Valid NovaPropostaRequest novaPropostaRequest,
                                           UriComponentsBuilder uriComponentsBuilder) {
        Proposta novaProposta = novaPropostaRequest.toModel();

        if(novaProposta.possuiDocumentoCadastrado(propostaRepository)) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Não é possível criar mais de uma proposta com o mesmo documento.");
        }

        propostaRepository.save(novaProposta);

        return ResponseEntity.created(uriComponentsBuilder
                .path("/propostas/{id}")
                .buildAndExpand(novaProposta.getId())
                .toUri()).build();
    }

}

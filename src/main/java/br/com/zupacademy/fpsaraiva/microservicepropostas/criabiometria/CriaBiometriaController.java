package br.com.zupacademy.fpsaraiva.microservicepropostas.criabiometria;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;
import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.CartaoRepository;
import br.com.zupacademy.fpsaraiva.microservicepropostas.compartilhado.excessoes.ApiErroException;
import br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta.Proposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CriaBiometriaController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private BiometriaRepository biometriaRepository;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    @PostMapping("/cartoes/{id}/biometria")
    public ResponseEntity<?> criarBiometria(@Valid @PathVariable(value = "id") String id, @RequestBody NovaBiometriaRequest novaBiometriaRequest,
                                            UriComponentsBuilder uriComponentsBuilder) {
        Optional<Cartao> cartaoBuscado = cartaoRepository.findById(id);

        if(cartaoBuscado.isEmpty()) {
            throw new ApiErroException(HttpStatus.NOT_FOUND, "Não foi encontrado nenhum cartão com o ID informado.");
        }

        Biometria novaBiometria = novaBiometriaRequest.toModel(cartaoBuscado.get());
        biometriaRepository.save(novaBiometria);

        logger.info("Biometria 'id={}' CRIADA com sucesso!", novaBiometria.getId());

        return ResponseEntity.created(uriComponentsBuilder
                        .path("/cartoes/{id}/biometrias")
                        .buildAndExpand(novaBiometria.getId())
                        .toUri()).build();
    }

}

package br.com.zupacademy.fpsaraiva.microservicepropostas.associacarteiradigital;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;
import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.CartaoRepository;
import br.com.zupacademy.fpsaraiva.microservicepropostas.bloqueiocartao.StatusCartao;
import br.com.zupacademy.fpsaraiva.microservicepropostas.compartilhado.excessoes.ApiErroException;
import feign.FeignException;
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
public class AssociaCarteiraController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AssociaCarteiraRepository associaCarteiraRepository;

    @Autowired
    private AssociaCarteiraClient associaCarteiraClient;

    @PostMapping("/cartoes/{id}/carteira")
    public ResponseEntity<?> associarCarteiraDigital(@PathVariable(value = "id") String id,
                                          @Valid @RequestBody AssociaCarteiraRequest associacaoCarteiraRequest,
                                          UriComponentsBuilder uriComponentsBuilder) {

        Optional<Cartao> cartaoBuscado = cartaoRepository.findById(id);
        if(cartaoBuscado.isEmpty()) {
            throw new ApiErroException(HttpStatus.NOT_FOUND, "Não foi encontrado nenhum cartão com o ID informado.");
        }

        Cartao cartao = cartaoBuscado.get();
        if (cartao.getBloqueado().equals(StatusCartao.BLOQUEADO)) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "O cartão informado está bloqueado.");
        }

        if(!associacaoCarteiraRequest.getCarteira().equalsIgnoreCase("paypal")) {
            throw new ApiErroException(HttpStatus.BAD_REQUEST, "Erro: carteira selecionada indisponível.");
        }

        Carteira novaCarteira = associacaoCarteiraRequest.toModel(cartao);
        try {
            AssociaCarteiraTemplate associacao = new AssociaCarteiraTemplate(associacaoCarteiraRequest.getEmail(), associacaoCarteiraRequest.getCarteira());
            ResultadoAssociaCarteira respostaAssociacao = associaCarteiraClient.associarCarteiraDigital(cartao.getId(), associacao);

            novaCarteira.registraRespostaClienteExterno(respostaAssociacao.getResultado());
        } catch (FeignException.UnprocessableEntity e) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "FALHA no processamento da associação de carteira. Motivo: associação já registrada.");
        } catch (FeignException e) {
            throw new ApiErroException(HttpStatus.BAD_GATEWAY, "Erro no processamento da associação de carteira. Motivo: erro no sistema do banco");
        }

        associaCarteiraRepository.save(novaCarteira);

        return ResponseEntity.ok(uriComponentsBuilder
                .path("/cartoes/{id}/carteira")
                .buildAndExpand(novaCarteira.getId())
                .toUri());

    }

}

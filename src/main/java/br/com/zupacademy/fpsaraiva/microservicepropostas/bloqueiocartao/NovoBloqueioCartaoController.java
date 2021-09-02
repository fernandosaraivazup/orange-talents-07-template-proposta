package br.com.zupacademy.fpsaraiva.microservicepropostas.bloqueiocartao;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;
import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.CartaoRepository;
import br.com.zupacademy.fpsaraiva.microservicepropostas.compartilhado.excessoes.ApiErroException;
import br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta.Proposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class NovoBloqueioCartaoController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private BloqueioRepository bloqueioRepository;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    @GetMapping("/cartoes/{id}/bloqueio")
    public ResponseEntity<?> criaBloqueioCartao(@Valid @PathVariable(value = "id") String id, HttpServletRequest request,
                                     UriComponentsBuilder uriComponentsBuilder) {
        String userAgent = request.getHeader("User-Agent");
        String ipClient = request.getHeader("X-Forwarded-For");

        if(ipClient == null || ipClient.isBlank()) {
            throw new ApiErroException(HttpStatus.BAD_REQUEST, "O IP do cliente precisa ser informado.");
        }

        Optional<Cartao> cartaoBuscado = cartaoRepository.findById(id);
        if(cartaoBuscado.isEmpty()) {
            throw new ApiErroException(HttpStatus.NOT_FOUND, "Não foi encontrado nenhum cartão com o ID informado.");
        }

        Cartao cartao = cartaoBuscado.get();
        if (cartao.getBloqueado()) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "O cartão informado já está bloqueado.");
        }

        cartao.bloqueiaCartao();

        NovoBloqueioRequest novoPedidoDeBloqueio = new NovoBloqueioRequest(userAgent, ipClient);
        Bloqueio novoBloqueio = novoPedidoDeBloqueio.toModel(cartaoBuscado.get());
        bloqueioRepository.save(novoBloqueio);

        logger.info("Bloqueio 'id={}' realizado com sucesso!", novoBloqueio.getId());

        return ResponseEntity.ok(uriComponentsBuilder
                .path("/cartoes/{id}/bloqueio")
                .buildAndExpand(novoBloqueio.getId())
                .toUri());
    }

}

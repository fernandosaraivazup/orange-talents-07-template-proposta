package br.com.zupacademy.fpsaraiva.microservicepropostas.avisoviagem;

import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.Cartao;
import br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta.CartaoRepository;
import br.com.zupacademy.fpsaraiva.microservicepropostas.bloqueiocartao.StatusCartao;
import br.com.zupacademy.fpsaraiva.microservicepropostas.compartilhado.excessoes.ApiErroException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AvisoViagemController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AvisoViagemRepository avisoViagemRepository;

    @Autowired
    private AvisoViagemClient avisoViagemClient;

    @PostMapping("/cartoes/{id}/aviso")
    public ResponseEntity<?> criarAvisoViagem(@PathVariable(value = "id") String id,
                                                @Valid @RequestBody AvisoViagemRequest avisoViagemRequest,
                                                HttpServletRequest request, UriComponentsBuilder uriComponentsBuilder) {
        String userAgent = request.getHeader("User-Agent");
        String ipClient = request.getHeader("ipClient");

        if(ipClient == null || ipClient.isBlank()) {
            throw new ApiErroException(HttpStatus.BAD_REQUEST, "O IP do cliente precisa ser informado.");
        }

        if(userAgent == null || userAgent.isBlank()) {
            throw new ApiErroException(HttpStatus.BAD_REQUEST, "O IP do cliente precisa ser informado.");
        }

        Optional<Cartao> cartaoBuscado = cartaoRepository.findById(id);
        if(cartaoBuscado.isEmpty()) {
            throw new ApiErroException(HttpStatus.NOT_FOUND, "Não foi encontrado nenhum cartão com o ID informado.");
        }

        Cartao cartao = cartaoBuscado.get();
        if (cartao.getBloqueado().equals(StatusCartao.BLOQUEADO)) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "O cartão informado está bloqueado.");
        }

        try {
            NotificacaoAvisoRequest notificacao = new NotificacaoAvisoRequest(avisoViagemRequest.getDestinoViagem(), avisoViagemRequest.getTerminoViagem());
            ResultadoNotificacaoViagem respostaAviso = avisoViagemClient.notificarAvisoViagem(cartao.getId(), notificacao);
        } catch (FeignException.UnprocessableEntity e) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "FALHA no processamento da notificação de aviso viagem. Motivo: notificação já registrada.");
        } catch (FeignException e) {
            throw new ApiErroException(HttpStatus.BAD_GATEWAY, "Erro no processamento da notificação de avido viagem. Motivo: erro no sistema do banco");
        }

        AvisoViagem novoAviso = avisoViagemRequest.toModel(userAgent, ipClient, cartao);
        novoAviso.registraStatus();
        avisoViagemRepository.save(novoAviso);

        return ResponseEntity.ok(uriComponentsBuilder
                .path("/cartoes/{id}/aviso")
                .buildAndExpand(novoAviso.getId())
                .toUri());
    }

}

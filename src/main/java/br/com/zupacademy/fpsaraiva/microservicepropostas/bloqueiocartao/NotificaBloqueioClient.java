package br.com.zupacademy.fpsaraiva.microservicepropostas.bloqueiocartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "notifica-bloqueio", url = "${sistema-cartao.host}")
public interface NotificaBloqueioClient {

    @PostMapping("${sistema-cartao.notifica-bloqueios}")
    ResultadoNotificacaoBloqueio notificarBloqueio(@PathVariable String id,
                                                   @RequestBody @Valid NotificacaoBloqueioRequest notificacaoBloqueioRequest);
}

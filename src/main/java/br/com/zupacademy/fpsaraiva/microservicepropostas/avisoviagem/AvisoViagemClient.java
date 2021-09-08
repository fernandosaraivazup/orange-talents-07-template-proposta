package br.com.zupacademy.fpsaraiva.microservicepropostas.avisoviagem;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "notifica-viagem", url = "${sistema-cartao.host}")
public interface AvisoViagemClient {

    @PostMapping("${sistema-cartao.notifica-avisos}")
    ResultadoNotificacaoViagem notificarAvisoViagem(@PathVariable String id,
                                                         @RequestBody @Valid NotificacaoAvisoRequest notificacaoAvisoRequest);
}

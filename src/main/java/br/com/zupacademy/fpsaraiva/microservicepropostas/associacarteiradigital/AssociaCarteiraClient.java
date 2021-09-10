package br.com.zupacademy.fpsaraiva.microservicepropostas.associacarteiradigital;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "associa-carteira", url = "${sistema-cartao.host}")
public interface AssociaCarteiraClient {

    @PostMapping("${sistema-cartao.associa-carteira}")
    ResultadoAssociaCarteira associarCarteiraDigital(@PathVariable String id,
                                                     @RequestBody @Valid AssociaCarteiraTemplate associaCarteiraTemplate);
}

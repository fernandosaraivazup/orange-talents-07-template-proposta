package br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "consulta-cartao", url="${sistema-cartao.host}")
public interface ConsultaCartaoClient {

    @GetMapping("${sistema-cartao.consulta-cartoes}")
    CartaoResponse consultarCartoes(@RequestParam String idProposta);
}

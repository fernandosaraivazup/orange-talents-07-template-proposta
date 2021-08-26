package br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "analise-financeira", url="${analise-financeira.host}")
public interface AnaliseFinanceiraClient {

    @PostMapping("${analise-financeira.analisa-proposta}")
    ResultadoAnalise analisarProposta(@RequestBody @Valid SolicitacaoAnaliseRequest solicitacaoAnaliseRequest);
}

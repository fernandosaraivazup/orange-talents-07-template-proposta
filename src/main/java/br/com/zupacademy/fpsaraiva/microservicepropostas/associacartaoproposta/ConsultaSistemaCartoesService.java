package br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta;

import br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante.StatusProposta;
import br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta.Proposta;
import br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsultaSistemaCartoesService {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private ConsultaCartaoClient consultaCartaoClient;

    private final Logger logger = LoggerFactory.getLogger(ConsultaSistemaCartoesService.class);

    @Scheduled(initialDelay = 10000, fixedDelayString = "${periodicidade.consulta-numero-cartao}")
    private void consultaNumeroCartaoPropostasElegiveis() {
        logger.info("ASSOCIA CARTÕES: início da tarefa.");

        List<Proposta> propostasSemCartao = propostaRepository.findAllByStatusAndCartao(StatusProposta.ELEGIVEL, null);

        propostasSemCartao.forEach(novaProposta -> {
            try{
                CartaoResponse response = consultaCartaoClient.consultarCartoes(novaProposta.getId().toString());
                Cartao cartao = response.toModel(novaProposta);
                cartaoRepository.save(cartao);

                logger.info("Vinculação de cartão - proposta 'id={}' concluída COM SUCESSO.", novaProposta.getId());
            }catch (FeignException f){
                logger.error("ERRO: Não foi possível realizar a vinculação de cartão - proposta 'id={}' .", novaProposta.getId());
            }
        });

        logger.info("ASSOCIA CARTÕES: fim da tarefa.");
    }

}

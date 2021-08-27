package br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta;

import br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante.StatusProposta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PropostaRepository extends CrudRepository<Proposta, UUID> {
    boolean existsByDocumento(String documento);

    List<Proposta> findAllByStatusAndCartao(StatusProposta status, Long cartaoId);
}

package br.com.zupacademy.fpsaraiva.microservicepropostas.criacaoproposta;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PropostaRepository extends CrudRepository<Proposta, UUID> {
}

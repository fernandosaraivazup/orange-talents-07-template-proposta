package br.com.zupacademy.fpsaraiva.microservicepropostas.criabiometria;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BiometriaRepository extends JpaRepository<Biometria, Long> {
}

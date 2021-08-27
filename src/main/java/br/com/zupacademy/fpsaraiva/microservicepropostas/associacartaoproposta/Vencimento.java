package br.com.zupacademy.fpsaraiva.microservicepropostas.associacartaoproposta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_vencimento")
public class Vencimento {

    @Id
    private String id;

    @NotNull
    @Min(1)
    @Max(31)
    @Column(name = "day")
    private Integer dia;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime dataDeCriacao;

    @Deprecated
    public Vencimento() {
    }

    public Vencimento(String id, Integer dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

}

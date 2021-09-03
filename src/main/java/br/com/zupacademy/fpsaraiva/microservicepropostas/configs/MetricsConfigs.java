package br.com.zupacademy.fpsaraiva.microservicepropostas.configs;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class MetricsConfigs {

    private final MeterRegistry meterRegistry;

    public MetricsConfigs(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void myFirstcounter() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itaú"));

        Counter contadorDePropostasCriadas = this.meterRegistry.counter("proposta_criada", tags);
        contadorDePropostasCriadas.increment();
    }

}

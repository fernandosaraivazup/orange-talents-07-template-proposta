package br.com.zupacademy.fpsaraiva.microservicepropostas.consultadadossolicitante;

public enum StatusTransacaoPagamento {

    SEM_RESTRICAO{
        @Override
        public StatusProposta getStatusTransacaoPagamento() {
            return StatusProposta.ELEGIVEL;
        }
    },
    COM_RESTRICAO {
        @Override
        public StatusProposta getStatusTransacaoPagamento() {
            return StatusProposta.NAO_ELEGIVEL;
        }
    };

    public abstract StatusProposta getStatusTransacaoPagamento();
}

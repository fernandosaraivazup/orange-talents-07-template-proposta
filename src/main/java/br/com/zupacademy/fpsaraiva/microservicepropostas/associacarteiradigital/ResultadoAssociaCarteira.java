package br.com.zupacademy.fpsaraiva.microservicepropostas.associacarteiradigital;

public class ResultadoAssociaCarteira {

    private String resultado;

    private String idAssociacao;

    public ResultadoAssociaCarteira(String resultado, String idAssociacao) {
        this.resultado = resultado;
        this.idAssociacao = idAssociacao;
    }

    public String getResultado() {
        return resultado;
    }

}

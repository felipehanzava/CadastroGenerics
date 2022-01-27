package br.com.ebac.hz.modulo17.domain;

public class Produto implements Persistente {

    private Long codigo;

    private String nome;

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public Long getCodigo() {
        return this.codigo;
    }
}

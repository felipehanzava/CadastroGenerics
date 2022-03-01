package br.com.ebac.hz.modulo17.domain;

import br.com.ebac.hz.modulo17.annotation.TipoChave;

public class Produto implements Persistente {

    @TipoChave("getCodigo")
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

    public Long getCodigo() {
        return this.codigo;
    }
}

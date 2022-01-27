package br.com.ebac.hz.modulo17.fabrica;

import br.com.ebac.hz.modulo17.domain.Persistente;
import br.com.ebac.hz.modulo17.domain.Produto;

public class ProdutoFabrica extends FabricaPersistente {

    @Override
    public Persistente criarObjeto(String[] dados) {
        Produto produto = new Produto();
        produto.setCodigo(Long.parseLong(dados[0].trim()));
        produto.setNome(dados[1]);
        return produto;
    }
}
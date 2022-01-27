package br.com.ebac.hz.modulo17.dao;

import br.com.ebac.hz.modulo17.dao.generic.GenericDAO;

import br.com.ebac.hz.modulo17.domain.Cliente;
import br.com.ebac.hz.modulo17.domain.Produto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProdutoDAO extends GenericDAO<Produto> implements IProdutoDAO {


    public ProdutoDAO() {
        super();
        Map<Long, Produto> mapaInterno = this.map.get(getTipoClasse());
        if (mapaInterno == null) {
            mapaInterno = new HashMap<>();
            this.map.put(getTipoClasse(), mapaInterno);
        }

    }

    @Override
    public Class<Produto> getTipoClasse() {
        return Produto.class;
    }

    @Override
    public void atualizarDados(Produto produto, Produto entityCadastrado) {
        entityCadastrado.setNome(produto.getNome());

    }
}

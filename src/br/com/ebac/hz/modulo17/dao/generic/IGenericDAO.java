package br.com.ebac.hz.modulo17.dao.generic;

import br.com.ebac.hz.modulo17.domain.Persistente;
import br.com.ebac.hz.modulo17.domain.Produto;

import java.util.Collection;

public interface IGenericDAO <T extends Persistente>{


    public Boolean cadastrar(T entity);

    public void excluir(Long valor);

    public void alterar(T entity);

    public T consultar(Long valor);

    public Collection<T> buscarTodos();
}

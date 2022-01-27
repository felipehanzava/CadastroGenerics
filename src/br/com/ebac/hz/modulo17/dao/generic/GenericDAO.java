package br.com.ebac.hz.modulo17.dao.generic;

import br.com.ebac.hz.modulo17.domain.Persistente;
import br.com.ebac.hz.modulo17.domain.Produto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class GenericDAO<T extends Persistente> implements IGenericDAO<T>{

    protected Map<Class, Map<Long, T>> map;

    public abstract Class<T> getTipoClasse();

    public abstract void atualizarDados(T entity, T entityCadastrado);

    public GenericDAO(){
        if (this.map == null){
            this.map = new HashMap<>();
        }
    }

    @Override
    public Boolean cadastrar(T entity) {
        Map<Long, T> mapaInterno = this.map.get(getTipoClasse());
        if (mapaInterno.containsKey(entity.getCodigo())){
            return false;
        }
        mapaInterno.put(entity.getCodigo(), entity);
        return true;
    }

    @Override
    public void excluir(Long valor) {
        Map<Long, T> mapaInterno = this.map.get(getTipoClasse());
        T entityCadastrado = mapaInterno.get(valor);

        if (entityCadastrado != null){
            this.map.remove(valor, entityCadastrado);
        }
    }

    @Override
    public void alterar(T entity) {
        Map<Long, T> mapaInterno = this.map.get(getTipoClasse());
        T entityCadastrado = mapaInterno.get(entity.getCodigo());
        if(entityCadastrado != null){
            atualizarDados(entity, entityCadastrado);
        }
    }

    @Override
    public T consultar(Long valor) {
        Map<Long, T> mapaInterno = this.map.get(getTipoClasse());
        return mapaInterno.get(valor);
    }

    @Override
    public Collection<T> buscarTodos() {
        Map<Long, T> mapaInterno = this.map.get(getTipoClasse());
        return mapaInterno.values();
    }
}

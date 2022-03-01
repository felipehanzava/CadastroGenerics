package br.com.ebac.hz.modulo17.dao.generic;

import br.com.ebac.hz.modulo17.annotation.TipoChave;
import br.com.ebac.hz.modulo17.domain.Persistente;
import br.com.ebac.hz.modulo17.domain.Produto;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class GenericDAO<T extends Persistente> implements IGenericDAO<T>{

    protected Map<Class, Map<Long, T>> map;

    public abstract Class<T> getTipoClasse();

    public abstract void atualizarDados(T entity, T entityCadastrado);

    public GenericDAO(){
        if (this.map == null){
            this.map = new HashMap<>();
        }
    }


    // Usando Annotation and Reflection para usar methodo em tempo de execuçào
    public Long getChave(T entity){
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field: fields){
            if (field.isAnnotationPresent(TipoChave.class)){
                TipoChave tipoChave = field.getAnnotation(TipoChave.class);
                String nomeMetodo = tipoChave.value();
                try {
                    Method method = entity.getClass().getMethod(nomeMetodo);
                    Long value = (Long) method.invoke(entity);
                    return value;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public Boolean cadastrar(T entity) {
        Map<Long, T> mapaInterno = this.map.get(getTipoClasse());
        Long chave = getChave(entity);
        if (mapaInterno.containsKey(chave)){
            return false;
        }
        mapaInterno.put(chave, entity);
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
        Long chave = getChave(entity);
        T entityCadastrado = mapaInterno.get(chave);
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

package br.com.ebac.hz.modulo17.dao;

import br.com.ebac.hz.modulo17.dao.generic.GenericDAO;
import br.com.ebac.hz.modulo17.domain.Cliente;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClienteMapDAO extends GenericDAO<Cliente> implements IClienteDAO{

    public ClienteMapDAO(){
        super();
        Map<Long, Cliente> mapaInterno = this.map.get(getTipoClasse());
        if (mapaInterno == null){
            mapaInterno = new HashMap<>();
            this.map.put(getTipoClasse(), mapaInterno);

    }
    }

    @Override
        public Class<Cliente> getTipoClasse() {
        return Cliente.class;
    }

    @Override
    public void atualizarDados(Cliente cliente, Cliente entityCadastrado) {
        entityCadastrado.setNome(cliente.getNome());
        entityCadastrado.setTel(cliente.getTel());
        entityCadastrado.setEnd(cliente.getEnd());
        entityCadastrado.setNumero(cliente.getNumero());
        entityCadastrado.setCidade(cliente.getCidade());
        entityCadastrado.setEstado(cliente.getEstado());
    }

}

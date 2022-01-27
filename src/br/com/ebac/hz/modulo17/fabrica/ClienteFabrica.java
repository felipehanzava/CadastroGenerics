package br.com.ebac.hz.modulo17.fabrica;

import br.com.ebac.hz.modulo17.domain.Cliente;
import br.com.ebac.hz.modulo17.domain.Persistente;

public class ClienteFabrica extends FabricaPersistente {
    @Override
    public Persistente criarObjeto(String dadosSeparados[]) {
        return new Cliente(dadosSeparados[0],dadosSeparados[1],dadosSeparados[2],dadosSeparados[3],dadosSeparados[4],dadosSeparados[5],dadosSeparados[6]);
    }
}

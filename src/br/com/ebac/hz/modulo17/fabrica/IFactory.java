package br.com.ebac.hz.modulo17.fabrica;

public interface IFactory {
    FabricaPersistente criarFabrica(String opcaoMenuGeral);
}

package br.com.ebac.hz.modulo17;

import br.com.ebac.hz.modulo17.dao.ClienteMapDAO;
import br.com.ebac.hz.modulo17.dao.IClienteDAO;
import br.com.ebac.hz.modulo17.dao.IProdutoDAO;
import br.com.ebac.hz.modulo17.dao.ProdutoDAO;
import br.com.ebac.hz.modulo17.dao.generic.IGenericDAO;
import br.com.ebac.hz.modulo17.domain.Cliente;
import br.com.ebac.hz.modulo17.domain.Persistente;
import br.com.ebac.hz.modulo17.domain.Produto;
import br.com.ebac.hz.modulo17.fabrica.FabricaPersistente;
import br.com.ebac.hz.modulo17.fabrica.Factory;
import br.com.ebac.hz.modulo17.fabrica.IFactory;

import javax.swing.*;


public class App {
    private static IClienteDAO iClienteDAO;
    private static IProdutoDAO iProdutoDAO;
    private static String dadosClientes =  "Informe os dados do cliente separados por vígula, conforme exemplo:\n " +
                                            "Nome, CPF, Telefone, Endereço, Número, Cidade e Estado";
    private static String dadosProdutos = "Digite os dados do produto separados por vígula, conforme exemplo:\n" +
                                         " Código e Nome";

    public static void main(String args[]) {
        inicializarDAO();

        String mensagemMenu = "Escolha uma opção:\n "+
                "1 - Cliente\n" +
                " 2 - Produto";

        String escolha = "Escolha uma opção:\n " +
                "1 - Cadastrar\n"+
                " 2 - Consultar\n"+
                " 3 - Excluir\n"+
                " 4 - Alterar\n"+
                " 5 - Sair";


        String opcaoMenuGeral = JOptionPane.showInputDialog(null, mensagemMenu,
                "Cadastro", JOptionPane.INFORMATION_MESSAGE);

        while (!isOpcaoMenuValida(opcaoMenuGeral)) {
            if ("".equals(opcaoMenuGeral)) {
                sair();
            }
            opcaoMenuGeral = JOptionPane.showInputDialog(null,
                    "Opção inválida \n " + mensagemMenu, "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        }

        while (isOpcaoValida(opcaoMenuGeral)) {

            String titulo = opcaoMenuGeral.equals("1") ? "Cadastro de Clientes" : "Cadastro de Produtos";

            String opcao = JOptionPane.showInputDialog(null,
                    escolha,
                    titulo, JOptionPane.INFORMATION_MESSAGE);

            executarOpcoes(opcao, opcaoMenuGeral);

            opcaoMenuGeral = JOptionPane.showInputDialog(null,
                    mensagemMenu, "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private static void executarOpcoes(String opcao, String opcaoMenuGeral) {
        if (isOpcaoSair(opcao)) {
            sair();
        } else if (isCadastro(opcao)) {
            executarOpcaoCadastrar(opcaoMenuGeral);
        } else if(isConsultar(opcao)) {
            executarOpcaoConsultar(opcaoMenuGeral);
        } else if(isExcluir(opcao)) {
            executarOpcaoExcluir(opcaoMenuGeral);
        } else {
            executarOpcaoAtualizar(opcaoMenuGeral);
        }
    }


    private static void executarOpcaoAtualizar(String opcaoMenuGeral) {
        String dados = JOptionPane.showInputDialog(null,dadosClientes,
                "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        String[] dadosSeparados = dados.split(",");
        Cliente cliente = new Cliente(dadosSeparados[0],dadosSeparados[1],dadosSeparados[2],dadosSeparados[3],dadosSeparados[4],dadosSeparados[5],dadosSeparados[6]);
        iClienteDAO.alterar(cliente);
        JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso: ", "Sucesso",JOptionPane.INFORMATION_MESSAGE);
    }


    private static void executarOpcaoExcluir(String opcaoMenuGeral) {
        String msg = opcaoMenuGeral.equals("1") ? "Digite o CPF" : "Digite o código";
        String dados = JOptionPane.showInputDialog(null,
                msg,
                "Exclusão de dados", JOptionPane.INFORMATION_MESSAGE);
        getDAO(opcaoMenuGeral).excluir(Long.parseLong(dados));
        JOptionPane.showMessageDialog(null, "Dados excluídos com sucesso: ", "Sucesso",JOptionPane.INFORMATION_MESSAGE);
    }


    private static void executarOpcaoConsultar(String opcaoMenuGeral) {
        String msg = opcaoMenuGeral.equals("1") ? "Digite o CPF" : "Digite o código";
        String dados = JOptionPane.showInputDialog(null,
                msg,
                "Consultar", JOptionPane.INFORMATION_MESSAGE);

        Persistente persistente = consultar(dados, opcaoMenuGeral);
        if (persistente != null) {
            JOptionPane.showMessageDialog(null, "Dado encontrado: " + persistente.toString(), "Sucesso",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Dado não encontrado: ", "Sucesso",JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private static void executarOpcaoCadastrar(String opcaoMenuGeral) {
        String dados = "";

        if ("1".equals(opcaoMenuGeral)) {
            dados = JOptionPane.showInputDialog(null,
                    dadosClientes,
                    "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        } else {
            dados = JOptionPane.showInputDialog(null,
                    dadosProdutos,
                    "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        }
        cadastrar(dados, opcaoMenuGeral);
    }


    private static void inicializarDAO() {
        iClienteDAO = new ClienteMapDAO();
        iProdutoDAO = new ProdutoDAO();
    }


    public static IGenericDAO getDAO(String opcaoMenuGeral) {
        if ("1".equals(opcaoMenuGeral)) {
            return iClienteDAO;
        } else {
            return iProdutoDAO;
        }
    }

    private static Persistente consultar(String dados, String opcaoMenuGeral) {
        return getDAO(opcaoMenuGeral).consultar(Long.valueOf(dados));
    }


    private static void cadastrar(String dados, String opcaoMenuGeral) {
        String[] dadosSeparados = dados.split(",");
        Persistente persistente = criarObjetoConcreto(dadosSeparados, opcaoMenuGeral);
        Boolean isCadastrado = cadastrarObjeto(opcaoMenuGeral, persistente);

        if (isCadastrado) {
            JOptionPane.showMessageDialog(null, "Dados cadastrado com sucesso ", "Sucesso",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Dados já se encontra cadastrado", "Erro",JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private static Boolean cadastrarObjeto(String opcaoMenuGeral, Persistente persistente) {
        return getDAO(opcaoMenuGeral).cadastrar(persistente);
    }


    private static Persistente criarObjetoConcreto(String[] dadosSeparados, String opcaoMenuGeral) {
        IFactory factory = new Factory();
        FabricaPersistente fabricaPersistente = factory.criarFabrica(opcaoMenuGeral);
        return fabricaPersistente.criarObjeto(dadosSeparados);
    }

    private static boolean isOpcaoValida(String opcao) {
        if ("1".equals(opcao) || "2".equals(opcao)
                || "3".equals(opcao) || "4".equals(opcao) || "5".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static boolean isOpcaoMenuValida(String opcao) {
        if ("1".equals(opcao) || "2".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static void sair() {
        JOptionPane.showMessageDialog(null, "Até logo: ", "Sair",JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private static boolean isOpcaoSair(String opcao) {
        if ("5".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static boolean isCadastro(String opcao) {
        if ("1".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static boolean isConsultar(String opcao) {
        if ("2".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static boolean isExcluir(String opcao) {
        if ("3".equals(opcao)) {
            return true;
        }
        return false;
    }

}
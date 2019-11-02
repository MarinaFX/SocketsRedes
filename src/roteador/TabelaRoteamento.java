package roteador;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabelaRoteamento {
    /*Implemente uma estrutura de dados para manter a tabela de roteamento.
     * A tabela deve possuir: IP Destino, Métrica e IP de Saída.
     */

    //estruturas de dados para armazenar as informações da tabela de roteamento
    private List<String> tabelaCompleta;
    private List<String> ipsDestino;    //lista de ips de destino
    private List<String> ipsSaida;      //lista de ips de saida
    private int metrica;                //variavel contendo o valor da metrica

    /**
     * alocando as variáveis na memória e inicializando a metrica durante a execução do construtor da classe
     */
    public TabelaRoteamento() {
        tabelaCompleta = new ArrayList<>();
        ipsSaida = new ArrayList<>();
        ipsDestino = new ArrayList<>();
        metrica = 0;
    }

    private String converteStringTabela(String tabelaS) {
        String ipTabelaS = tabelaS;

        String[] ipComMetricaTabRec = ipTabelaS.split("\\*");
        String[] ipSemMetricaTabRec = ipTabelaS.split(";");

        System.out.println("split -> ips com metrica");
        for (int i = 0; i < ipComMetricaTabRec.length; i++) {
            System.out.println(ipComMetricaTabRec[i]);
        }

        System.out.println("split -> ips sem metrica");

        for (int i = 0; i < ipSemMetricaTabRec.length; i++) {
            System.out.println(ipSemMetricaTabRec[i]);
        }


        System.out.println("fim converteStringTabela()");

        return new String("flemis");
    }

    private void testeFunction(){
        String ipTabelaS = "*192.168.1.2;1*192.168.1.3;1";

        String[] ipComMetricaTabRec = ipTabelaS.split("\\*");

        System.out.println("split -> ips com metrica");
        for (int i = 0; i < ipComMetricaTabRec.length; i++) {
            if (!ipComMetricaTabRec[i].equals("")) {
                tabelaCompleta.add(ipComMetricaTabRec[i]);
                System.out.println(ipComMetricaTabRec[i]);
            }
        }

        String[] ipSemMetricaTabRec = Arrays.asList(ipComMetricaTabRec).toString().split(";");

        System.out.println("split -> ips sem metrica");

        for (int i = 0; i < ipSemMetricaTabRec.length; i++) {
            System.out.println(ipSemMetricaTabRec[i]);
        }

        System.out.println("fim testeFunction()");
    }


    public void update_tabela(String tabela_s, InetAddress IPAddress) {
        /* Atualize a tabela de rotamento a partir da string recebida. */
        String ipTabelaS = tabela_s;
        ipTabelaS = ipTabelaS.trim();

        testeFunction();

        /*if (ipTabelaS.equals("!")) {
            ipsDestino.add(IPAddress.getHostAddress());
            System.out.println("IP " + IPAddress.getHostAddress() + " adicionando a lista");
        } else {
            ipTabelaS = converteStringTabela(tabela_s);
        }*/
    }

    public String get_tabela_string() {
        StringBuilder tabela_string = new StringBuilder();

        if (tabelaCompleta.isEmpty()) {
            tabela_string.append("!");
        } else {
            tabela_string.append("*");

            for (String ip : ipsDestino) {
                tabela_string.append(ip + ";" + getMetrica() + "*");
            }
        }

        //exemplo de saida: *192.168.1.2;1*192.168.1.3;1
        //                  Ou seja, “*” (asterisco) indica uma tupla, IP de Destino e Métrica. A métrica é separada
        //                  do IP por um “;” (ponto e vírgula).
        return tabela_string.toString();
    }

    public int getMetrica() {
        return metrica;
    }

    public void addMetrica() {
        metrica++;
    }

    public List<String> getIpsDestino() {
        return ipsDestino;
    }

    public List<String> getIpsSaida() {
        return ipsSaida;
    }
}

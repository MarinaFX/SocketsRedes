package roteador;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class TabelaRoteamento {
    /*Implemente uma estrutura de dados para manter a tabela de roteamento.
     * A tabela deve possuir: IP Destino, Métrica e IP de Saída.
     */
    private List<Endereco> tabelaRoteamento;
    private boolean modificada;

    public TabelaRoteamento() {
        tabelaRoteamento = new ArrayList<>();
        modificada = false;
    }

    public void update_tabela(String tabela_s, InetAddress IPAddress) {
        /* Atualize a tabela de rotamento a partir da string recebida. */
        String tabelaString;
        tabelaString = tabela_s.trim();

        //for (Endereco end : tabelaRoteamento){
            if ((tabelaString.equals("!"))) {
                Endereco end = new Endereco(IPAddress.getHostAddress(), "1");
                end.setIpSaida(IPAddress.getHostAddress());
                tabelaRoteamento.add(end);
                modificada = true;
            } else {
                List<Endereco> tabelaRecebida = getTabelaRecebida(tabelaString);
                addNovasRotas(tabelaRecebida, IPAddress);
            }
       // }

        System.out.println(IPAddress.getHostAddress() + ": " + tabela_s);
    }

    public void addNovasRotas(List<Endereco> tabelaRecebida, InetAddress IPSaida) {
        int i = 0;
        for (Endereco end : tabelaRecebida) {
            if (!end.getIp().equals(tabelaRoteamento.get(i).getIp())){
                end.setIpSaida(IPSaida.getHostAddress());
                tabelaRoteamento.add(end);
                modificada = true;
            }
            i++;
        }
    }

    public List<Endereco> getTabelaRecebida(String tabela_s) {
        List<Endereco> tabelaRecebida = new ArrayList<>();

        String[] enderecoSemAst = tabela_s.split("\\*");

        for (int i = 0; i < enderecoSemAst.length; i++) {
            if ((!enderecoSemAst[i].equals("")) || !(enderecoSemAst[i] == null)) {
                String[] enderecosEmetricas = enderecoSemAst[i].split(";");
                tabelaRecebida.add(new Endereco(enderecosEmetricas[0], enderecosEmetricas[1]));
            }
        }

        return tabelaRecebida;
    }


    public String get_tabela_string() {
        modificada = false;
        String tabela_string = "";

        if (tabelaRoteamento.isEmpty()) {
            /* Tabela de roteamento vazia conforme especificado no protocolo */
            tabela_string = "!";
        } else {
            /* Converta a tabela de rotamento para string, conforme formato definido no protocolo . */
            StringBuilder sb = new StringBuilder();

            for (Endereco end : tabelaRoteamento) {
                sb.append(end.toString());
            }
            tabela_string = sb.toString();
        }

        return tabela_string;
    }

    public boolean mudou() {
        return true;
    }

    public boolean nMudou() {
        return false;
    }

    public boolean getEstado() {
        return modificada;
    }
}
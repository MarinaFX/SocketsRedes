package roteador;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
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

        if ((tabelaString.equals("!"))) {
            Endereco end = new Endereco(IPAddress.getHostAddress(), "1");
            end.setIpSaida(IPAddress.getHostAddress());
            if (!verificaRepitidos(end)) {
                tabelaRoteamento.add(end);
                modificada = true;
            }

        } else {
            List<Endereco> tabelaRecebida = getTabelaRecebida(tabelaString);
            addNovasRotas(tabelaRecebida, IPAddress);
        }

        System.out.println(IPAddress.getHostAddress() + ": " + tabela_s);
    }

    private boolean verificaRepitidos(Endereco endereco) {
        for (Endereco end : tabelaRoteamento) {
            if (end.equals(endereco)) {
                return true;
            }
        }

        return false;
    }

    public void addNovasRotas(List<Endereco> tabelaRecebida, InetAddress IPSaida) {
        int i = 0;
        for (Endereco end : tabelaRecebida) {
            if ((!tabelaRoteamento.isEmpty()) && (!(end.getIp().equals(tabelaRoteamento.get(i).getIp())))) {
                end.setIpSaida(IPSaida.getHostAddress());
                tabelaRoteamento.add(end);
                modificada = true;
            }
            i++;
        }
    }

    public List<Endereco> getTabelaRecebida(String tabela_s) {
        List<Endereco> tabelaRecebida = new ArrayList<>();

        String[] aux = tabela_s.split("\\*");
        List<String> enderecoSemAst = new ArrayList<>(Arrays.asList(aux));

        for (int i = 0; i < enderecoSemAst.size(); i++) {
            if (enderecoSemAst.get(i).equals("") || !(enderecoSemAst.get(i) == null)) {
                enderecoSemAst.remove(i);
            }
        }

        for (int i = 0; i < enderecoSemAst.size(); i++) {
            String[] enderecosEmetricas = enderecoSemAst.get(i).split(";");
            tabelaRecebida.add(new Endereco(enderecosEmetricas[0], enderecosEmetricas[1]));
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

    public void mudou() {
        modificada = true;
    }

    public void nMudou() {
        modificada = false;
    }

    public boolean getEstado() {
        return modificada;
    }
}
package roteador;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

    /**
     * Função padrão já disponibilizada no código esqueleto. Atualiza a tabela com a tabela_s recebida de outro roteador
     *
     * @param tabela_s  tabela de roteamento de outro roteador
     * @param IPAddress ip desse outro roteador
     */
    public void update_tabela(String tabela_s, InetAddress IPAddress) {
        /* Atualize a tabela de rotamento a partir da string recebida. */
        String tabelaString;
        tabelaString = tabela_s.trim();

        System.out.println("IP vizinho | tabela recebida ");
        System.out.println(IPAddress.getHostAddress() + " | " + tabela_s);
        System.out.println();

        if (tabelaString.equals("!")) {
            Endereco end = new Endereco(IPAddress.getHostAddress(), "1");
            end.setIpSaida(IPAddress.getHostAddress());

            if (!isMeuProprioIP(tabelaString)) {
                if (!verificaRepitidos(end)) {
                    tabelaRoteamento.add(end);
                    mudou();
                }
            }
        } else {
            List<Endereco> tabelaRecebida = getTabelaRecebida(tabelaString);
            addNovasRotas(tabelaRecebida, IPAddress);
        }

        mostraTabela();
    }

    private void mostraTabela() {
        System.out.println("Tabela de roteamento atualizada");
        System.out.println("IP ; metrica");
        System.out.println();
        for (Endereco end : tabelaRoteamento) {
            System.out.println(end.toString());
        }
    }

    /**
     * Método para verificar se o próprio ip está presente na tabela recebida
     * @param tabela
     * @return
     */
    private boolean isMeuProprioIP(String tabela) {
        List<Endereco> novaTabela = new ArrayList<>();

        if (tabela.equals("!"))
            return false;
        else {
            novaTabela = getTabelaRecebida(tabela);
            for (Endereco end : novaTabela) {
                try {
                    if (end.getIp().equals(InetAddress.getLocalHost().getHostAddress()))
                        return true;
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    /**
     * Função básica para verificar duplicadas na tabela de roteamento
     *
     * @param endereco endereco a ser verficado
     * @return caso esteja repetido -> TRUE, caso não esteja na tabela (endereco novo) -> FALSE
     */
    private boolean verificaRepitidos(Endereco endereco) {
        for (Endereco end : tabelaRoteamento) {
            try {
                if (end.compareTo(endereco) > 0) {
                    return true;
                }
            } catch (IllegalArgumentException f) {
                System.out.println(Arrays.toString(f.getStackTrace()) + "\nError: " + f.getMessage());
            }
        }

        return false;
    }

    /**
     * Função para adicionar novas rotas a estrutura de dados tabelaRoteamento
     *
     * @param tabelaRecebida Lista do tipo Endereco com endereços da tabela_s recebida no update_tabela()
     * @param IPSaida        IP recebido no update_tabela()
     */
    public void addNovasRotas(List<Endereco> tabelaRecebida, InetAddress IPSaida) {
        for (Endereco end : tabelaRecebida) {
            for (Endereco end2 : tabelaRoteamento) {
                if (end.compareTo(end2) > 0) {
                    end.setIpSaida(IPSaida.getHostAddress());
                    tabelaRoteamento.add(end);
                    mudou();
                } else {
                    if (Integer.parseInt(end.getMetrica()) < Integer.parseInt(end2.getMetrica())) {
                        end2.setIpSaida(IPSaida.getHostAddress());
                    }
                }
            }
        }
    }

    /**
     * Função para armazenar a tabela recebida de outro roteador no update_tabela()
     *
     * @param tabela_s tabela do outro roteador recebida
     * @return retorna uma Lista do tipo Endereco com os Strings da @param tabela_s convertidos em dados do tipo Endereco
     */
    public List<Endereco> getTabelaRecebida(String tabela_s) {
        List<Endereco> tabelaRecebida = new ArrayList<>();

        String[] aux = tabela_s.split("\\*");
        List<String> enderecoSemAst = new ArrayList<>(Arrays.asList(aux));
        List<String> listaAuxiliar = new ArrayList<>();

        for (int i = 0; i < enderecoSemAst.size(); i++) {
            if (enderecoSemAst.get(i).equals("") || (enderecoSemAst.get(i) == null)) {
                listaAuxiliar.add(enderecoSemAst.get(i));
            }
        }

        for (String str : listaAuxiliar) {
            enderecoSemAst.remove(str);
        }

        for (int i = 0; i < enderecoSemAst.size(); i++) {
            String[] enderecosEmetricas = enderecoSemAst.get(i).split(";");
            tabelaRecebida.add(new Endereco(enderecosEmetricas[0], enderecosEmetricas[1]));
        }

        return tabelaRecebida;
    }

    /**
     * Função padrão disponibilizada no código esqueleto. Cria a String da nossa tabela de roteamento para ser enviada
     *
     * @return retorna a String no protocolo definido como *ENDERECO_IP;metrica
     */
    public String get_tabela_string() {
        nMudou();
        String tabela_string;

        if (tabelaRoteamento.isEmpty()) {
            /* Tabela de roteamento vazia conforme especificado no protocolo */
            System.out.println("A tabela de roteamento está vazia \n");
            tabela_string = "!";
        } else {
            /* Converta a tabela de rotamento para string, conforme formato definido no protocolo . */
            System.out.println("A tabela de roteamento possui endereços \n");
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
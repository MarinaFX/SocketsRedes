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

    /**
     * Função padrão já disponibilizada no código esqueleto. Atualiza a tabela com a tabela_s recebida de outro roteador
     * @param tabela_s tabela de roteamento de outro roteador
     * @param IPAddress ip desse outro roteador
     */
    public void update_tabela(String tabela_s, InetAddress IPAddress) {
        /* Atualize a tabela de rotamento a partir da string recebida. */
        String tabelaString;
        tabelaString = tabela_s.trim();

        if ((tabelaString.equals("!"))) {//SE a tabela estiver vazia, adicionamos o endereco como especificado no protocolo
            Endereco end = new Endereco(IPAddress.getHostAddress(), "1");//alocando endereco e adicionando IP e metrica
            end.setIpSaida(IPAddress.getHostAddress());//adicionando saida desse endereco
            if (!verificaRepitidos(end)) {//SE endereco não estiver repetido na tabela, adicionamos
                tabelaRoteamento.add(end);
                modificada = true;//acionamos a flag de que a tabela foi modificada (para a condição de 10s do message sender)
            }

        } else {//SE a tabela não estiver vazia, converteremos o "estringão" recebido em enderecos mais fáceis de manipular e adicionaremos novas rotas
            List<Endereco> tabelaRecebida = getTabelaRecebida(tabelaString);//chamada da função para converter endereços
            addNovasRotas(tabelaRecebida, IPAddress);//chamada da função para adicionar novas rotas a tabela
        }

        System.out.println(IPAddress.getHostAddress() + ": " + tabela_s);
    }

    /**
     * Função básica para verificar duplicadas na tabela de roteamento
     * @param endereco endereco a ser verficado
     * @return caso esteja repetido -> TRUE, caso não esteja na tabela (endereco novo) -> FALSE
     */
    private boolean verificaRepitidos(Endereco endereco) {
        for (Endereco end : tabelaRoteamento) {
            try {
                if (end.compareTo(endereco) > 0) {//comparando os dois objetos do tipo endereco
                    return true;
                }
            } catch (IllegalArgumentException f){
                System.out.println(Arrays.toString(f.getStackTrace()) + "\nError: " + f.getMessage());
            }
        }

        return false;
    }

    /**
     * Função para adicionar novas rotas a estrutura de dados tabelaRoteamento
     * @param tabelaRecebida Lista do tipo Endereco com endereços da tabela_s recebida no update_tabela()
     * @param IPSaida IP recebido no update_tabela()
     */
    public void addNovasRotas(List<Endereco> tabelaRecebida, InetAddress IPSaida) {
        //laço duplo para percorrer as duas estruturas e verificar individualmente os dados
        for (Endereco end : tabelaRecebida) {
            for (Endereco end2 : tabelaRoteamento) {
                if ((!(end.getIp().equals(end2.getIp())))) {//SE ip do end da tabela recebida não for igual ao da tabela de roteamento, entramos no IF
                    end.setIpSaida(IPSaida.getHostAddress());//setamos a saida do endereco (IP recebido do update_tabela())
                    tabelaRoteamento.add(end);//adicionamos o endereco na tabela de roteamento
                    modificada = true;//acionamos a flag de que a tabela foi modificada (para a condição de 10s do message sender)
                }
                else {//SE o ip do end da tabela recebida for igual ao da tabela de roteamento, entramos no ELSE
                    if (Integer.parseInt(end.getMetrica()) < Integer.parseInt(end2.getMetrica())){//verificamos as metricas dos end entre tabelas
                        end2.setIpSaida(IPSaida.getHostAddress());//caso a metrica seja menor, iremos aprender a nova saida
                    }
                }
            }
        }
    }

    /**
     * Função para armazenar a tabela recebida de outro roteador no update_tabela()
     * @param tabela_s tabela do outro roteador recebida
     * @return retorna uma Lista do tipo Endereco com os Strings da @param tabela_s convertidos em dados do tipo Endereco
     */
    public List<Endereco> getTabelaRecebida(String tabela_s) {
        List<Endereco> tabelaRecebida = new ArrayList<>();//alocacao da lista na memoria

        String[] aux = tabela_s.split("\\*");//retirando o * dos enderecos
        List<String> enderecoSemAst = new ArrayList<>(Arrays.asList(aux));//transformando o array em uma List do tipo String

        for (int i = 0; i < enderecoSemAst.size(); i++) {//laço para percorrer a estrutura de dados
            if (enderecoSemAst.get(i).equals("") || !(enderecoSemAst.get(i) == null)) {//caso o valor seja "" ou null iremos remover  da lista
                enderecoSemAst.remove(i);//removendo da lista
            }
        }

        for (int i = 0; i < enderecoSemAst.size(); i++) {//laço para percorrer a estrutura novamente
            String[] enderecosEmetricas = enderecoSemAst.get(i).split(";");//separando o IP da metrica da tabela recebida
            tabelaRecebida.add(new Endereco(enderecosEmetricas[0], enderecosEmetricas[1]));//alocando um novo endereco com o IP e metrica e já adicionando na tabela recebida
        }

        return tabelaRecebida;
    }

    /**
     * Função padrão disponibilizada no código esqueleto. Cria a String da nossa tabela de roteamento para ser enviada
     * @return retorna a String no protocolo definido como *ENDERECO_IP;metrica
     */
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
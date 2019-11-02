package roteador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TabelaRoteamento {
    /*Implemente uma estrutura de dados para manter a tabela de roteamento.
     * A tabela deve possuir: IP Destino, Métrica e IP de Saída.
     */

    //estruturas de dados para armazenar as informações da tabela de roteamento
    private List<String> ipsDestino; //lista de ips de destino
    private List<String> ipsSaida;   //lista de ips de saida
    private int metrica;             //variavel contendo o valor da metrica
    private boolean estado = false;
    //estado = true -> tabela foi alterada
    //estado = false -> tabela não foi alterada

    /**
     * alocando as variáveis na memória e inicializando a metrica durante a execução do construtor da classe
     */
    public TabelaRoteamento() {
        ipsSaida = new ArrayList<>();
        ipsDestino = new ArrayList<>();
        metrica = 0;
    }


    public void update_tabela(String tabela_s, InetAddress IPAddress) {
        /* Atualize a tabela de rotamento a partir da string recebida. */
        try ( BufferedReader inputFile = new BufferedReader(new FileReader("IPVizinhos.txt"))) {
            String ip;

            while( (ip = inputFile.readLine()) != null){
                ipsDestino.add(ip);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Roteador.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!ipsDestino.contains(IPAddress.getHostAddress())){
            ipsDestino.add(IPAddress.getHostAddress());
            addMetrica();
            mudou();

            System.out.println(IPAddress.getHostAddress() + ";" + getMetrica() + "*");
        }
    }

    public String get_tabela_string() {
        naoMudou();

        String tabela_string = "*";

        for (String ip : ipsDestino){
            tabela_string+= ip + ";" + getMetrica() + "*";
        }

        //exemplo de saida: *192.168.1.2;1*192.168.1.3;1
        //                  Ou seja, “*” (asterisco) indica uma tupla, IP de Destino e Métrica. A métrica é separada
        //                  do IP por um “;” (ponto e vírgula).
        return tabela_string;
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


    /**
     * Os dois métodos abaixo servem para controle de mundança de estado da tabela.
     * Seria muito complicado implementar o observer nesse caso, então quando atualizar a
     * tabela ela irá mudar o estado para que a classe MessageSender seja notificada e
     * faça o envio do pacote em menos de 10 minutos como especificado no protocolo.
     *
     * Quando a função get_tabela é chamada o estado é reinicializado para false, pois a tabela
     * não mudou e para retornar ao 'loop' de mudança de estado novamente.
     *
     * update_tabela() -> mudou() -> enviar pacote de novo -> get_tabela() reinicializa para
     * o estado novo;
     */
    public void mudou() {
        estado = true;
    }

    public void naoMudou(){
        estado = false;
    }

    public boolean getEstado(){
        return estado;
    }

}

package roteador;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageSender implements Runnable {
    TabelaRoteamento tabela; /*Tabela de roteamento */
    ArrayList<String> vizinhos; /* Lista de IPs dos roteadores vizinhos */

    public MessageSender(TabelaRoteamento t, ArrayList<String> v) {
        tabela = t;
        vizinhos = v;
    }

    @Override
    public void run() {
        DatagramSocket clientSocket = null;
        byte[] sendData;
        InetAddress IPAddress = null;
        DatagramPacket sendPacket = null;

        /* Cria socket para envio de mensagem */
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        while (true) {

            /* Pega a tabela de roteamento no formato string, conforme especificado pelo protocolo. */
            String tabela_string = tabela.get_tabela_string();

            /* Converte string para array de bytes para envio pelo socket. */
            sendData = tabela_string.getBytes();

            /* Anuncia a tabela de roteamento para cada um dos vizinhos */
            for (String ip : vizinhos) {
                /* Converte string com o IP do vizinho para formato InetAddress */
                try {
                    IPAddress = InetAddress.getByName(ip);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);
                    continue;
                }

                /* Configura pacote para envio da menssagem para o roteador vizinho na porta 5000*/
                sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 6000);
                System.out.println("Enviando datagrama para " + IPAddress.getHostAddress() + ":" + sendPacket.getPort());


                /* Realiza envio da mensagem. */
                try {
                    clientSocket.send(sendPacket);
                } catch (IOException ex) {
                    Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            /* Espera 10 segundos antes de realizar o próximo envio. CONTUDO, caso
             * a tabela de roteamento sofra uma alteração, ela deve ser reenvida aos
             * vizinho imediatamente.
             */
            try {
                int tempoEsperado = 0;
                while (tempoEsperado < 10000) {
                    if (tabela.getEstado()) {
                        System.out.println("Ora ora, tivemos uma atualização na tabela!");
                        tabela_string = tabela.get_tabela_string();
                        sendData = tabela_string.getBytes();

                        for (String ip : vizinhos) {
                            /* Converte string com o IP do vizinho para formato InetAddress */
                            try {
                                IPAddress = InetAddress.getByName(ip);
                            } catch (UnknownHostException ex) {
                                Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);
                                continue;
                            }

                            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 6000);
                            System.out.println("Enviando datagrama para " + IPAddress.getHostAddress() + ":" + sendPacket.getPort() + "\n");

                            try {
                                clientSocket.send(sendPacket);
                            } catch (IOException ex) {
                                Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    }
                    tempoEsperado += 1000;
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MessageSender.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }
}
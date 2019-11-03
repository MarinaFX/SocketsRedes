package roteador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

<<<<<<< HEAD
public class MessageReceiver implements Runnable {
    private TabelaRoteamento tabela;

    public MessageReceiver(TabelaRoteamento t) {
        tabela = t;
    }

    @Override
    public void run() {
        DatagramSocket serverSocket = null;

        try {

=======
public class MessageReceiver implements Runnable{
    private TabelaRoteamento tabela;
    
    public MessageReceiver(TabelaRoteamento t){
        tabela = t;
    }
    
    @Override
    public void run() {
        DatagramSocket serverSocket = null;
        
        try {
            
>>>>>>> Arrumado alguns erros. Resolver update dentro de 10 segundos
            /* Inicializa o servidor para aguardar datagramas na porta 5000 */
            serverSocket = new DatagramSocket(5000);
        } catch (SocketException ex) {
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
<<<<<<< HEAD

        byte[] receiveData = new byte[1024];

        while (true) {

            /* Cria um DatagramPacket */
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

=======
        
        byte[] receiveData = new byte[1024];
        
        while(true){
            /* Cria um DatagramPacket */
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            
>>>>>>> Arrumado alguns erros. Resolver update dentro de 10 segundos
            try {
                /* Aguarda o recebimento de uma mensagem */
                serverSocket.receive(receivePacket);
            } catch (IOException ex) {
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
<<<<<<< HEAD

            /* Transforma a mensagem em string */
            String tabela_string = new String(receivePacket.getData());

            /* Obtem o IP de origem da mensagem */
            InetAddress IPAddress = receivePacket.getAddress();

            tabela.update_tabela(tabela_string, IPAddress);
        }
    }

}
=======
            
            /* Transforma a mensagem em string */
            String tabela_string = new String( receivePacket.getData());
            
            /* Obtem o IP de origem da mensagem */
            InetAddress IPAddress = receivePacket.getAddress();
            
            tabela.update_tabela(tabela_string, IPAddress);
        }
    }
}
>>>>>>> Arrumado alguns erros. Resolver update dentro de 10 segundos

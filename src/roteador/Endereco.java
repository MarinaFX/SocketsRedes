package roteador;

public class Endereco {

    private String ip;
    private String metrica;
    private String ipSaida;

    public Endereco(String ip, String metrica) {
        this.ip = ip;
        this.metrica = metrica;
    }

    public void setIpSaida(String ipSaida) {
        this.ipSaida = ipSaida;
    }

    public String getIp() {
        return ip;
    }

    public String getMetrica() {
        return metrica;
    }

    public String toString() {
        return "*" + ip + ";" + metrica;
    }
}

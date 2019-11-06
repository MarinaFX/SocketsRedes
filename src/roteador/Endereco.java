package roteador;


/**
 * Classe Endereco criada para facilitar o armazenamento das estruturas de dados
 * Contém os atributos da tabela de roteamento específicados no trabalho, sendo eles
 * ip = IP destino
 * metrica = saltos entre máquinas
 * ipSaida = IP de saída do IP destino
 *
 * A classe implementa a interface Comparable para poder comparar o conteúdo entre dois objetos do tipo Endereco
 */
public class Endereco implements Comparable{

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

    /**
     * Função para incrementar a métrica quando um endereco novo for mapeado para tabela de roteamento.
     * Como a metrica foi implementada como String para padronização da mensagem, foi necessário fazer uma conversão entre os tipos String e inteiro.
     *
     * Basicamente o atributo do objeto recebe um valor convertido para String de uma conversão de String para inteiro do mesmo atributo, isto é,
     * primeiro convertemos a string do this.metrica em inteiro e incrementamos esse int. Após, convertemos novamente para uma string.
     *
     */
    public void incrementaMetrica(){
        this.metrica = String.valueOf(Integer.parseInt(this.metrica) + 1);
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

    /**
     * Método modificado da interface Comparable. Adotamos como parametro de comparação o toString() da classe
     * @param o objeto a ser comparado
     * @return retorna um inteiro conforme padrão da interface. Se positivo, é igual. Se negativo, é diferente.
     * Por fim, ao invés de 0, lançaremos uma exception indicando que não é do mesmo tipo Endereco o objeto "o"
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Endereco){
            if (o.toString().equals(this.toString())){
                return 1;
            }
            else {
                return -1;
            }
        }

        throw new IllegalArgumentException("Not an " + this.getClass().getName() + "type");
    }
}
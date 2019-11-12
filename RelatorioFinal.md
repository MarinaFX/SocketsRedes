
# Trabalho de Redes - Marina e Thaís

## Data de Entrega: ~~08/11/2019~~ 12/11/2019 🙏🙏🙏

### Introdução
Conforme especificado para o trabalho de redes, esse é um relatório em Markdown.md. Nesse relatório encontra-se o report sobre como foi a implementação desse trabalho e um pouco do código.

Este trabalho consiste na implementação de Sockets UDP, no qual foi necessário construir uma tabela de roteamento que se comunica com outras máquinas (roteadores).

Primeiramente, para a implementação do roteador partimos do código fonte disponibilizado no moodle pela professora. Assim, a partir dele começamos as adições e alterações do código. Abaixo, segue uma tabela com os métodos alterados e os que foram adicionados ao código: 

Método 					   | 		 Alterado 			| 			Adicionado
-------------------------- | -------------------------- | --------------------------
+ update_tabela(String  tabela_s, InetAddress  IPAddress) : void				   | ✔️ | ❌
+ get_tabela_string() : String													   | ✔️ | ❌
- mostraTabela() : void															   | ❌ | ✔️
- isMeuProprioIP(String  tabela) : boolean										   | ❌ | ✔️
- isMeuProprioIP(Endereco end) : boolean										   | ❌ | ✔️
- verificaRepitidos(Endereco  endereco) : boolean								   | ❌ | ✔️
+ addNovasRotas(List`<Endereco>` tabelaRecebida, InetAddress  IPSaida) : void	   | ❌ | ✔️
+ getTabelaRecebida(String  tabela_s) : List`<Endereco>`						   | ❌ | ✔️
+ mudou() : void																   | ❌ | ✔️
+ nMudou(): void																   | ❌ | ✔️
+ getEstado() : boolean															   | ❌ | ✔️


O código fonte entregue está comentado, explicando o escopo e função de cada método. Portanto, iremos mostrar nesse relatório mais sobre nossa experiência com a produção do trabalho e o que passamos para chegar no final dele. 

### 1ª Etapa: entendendo o código
Para começarmos de fato o trabalho, tivemos que nos ambientar com o código e entender o que era cada classe e quais eram suas funções. Assim, levamos um certo tempo estudando o código e executando ele com qualquer saída e entrada. Ao final desse processo, captamos qual era a importância da classe `MessageSender, Message receiver, TabelaRoteamento e Roteador.`

### 2ª Etapa: construindo o código
A partir do entendimento do código, começamos a produção. Nessa etapa,  Thaís montou o esqueleto e o conteúdo inicial dos métodos que adicionamos no código e Marina complementou com algumas alterações e ajustes. Essa etapa do trabalho foi a mais complexa pois envolvia a conexão e coesão dos métodos e o que eles deveriam processar para criar a tabela de roteamento pedida no trabalho. Dessa forma, essa etapa foi a mais longa. 

### 3ª Etapa: consertando os erros e ajustando o código
Após a etapa de produção, percebemos que o essencial do código havia sido construído, isto é, a atualização da tabela, a adição de novas rotas, a conversão da `String` recebida para um endereço IP codificável, entre outros. A partir desse ponto, começamos a refinar o código e fazer ajustes e proteções pedidas no protocolo. Nessa etapa, construímos (pelo menos tentamos hehe) o envio da tabela dentro de 10 segundos caso ela seja atualizada, adição da métrica e outros ajustes. 
Por fim, precisamos fazer a ressalva dos problemas de condição de corrida. Após fazermos os ajustes e melhorias no código, nos deparamos com problemas de duas ou mais Threads tentarem acessar a variável na qual armazenávamos a tabela de roteamento. Assim, fizemos a implementação de semáforos (Semaphores) e aproveitamos para aplicar o conteúdo de Sistemas Operacionais com esse trabalho.  

### Conclusão
Por fim, o aprendizado do nosso grupo é que achamos o trabalho muito difícil em sua implementação. Na teoria e na prática entendemos o conteúdo de Sockets, TCP/UDP/IP, porém é realmente complicado fazer a codificação desses protocolos. Para próximos desafios, se precisarmos implementar esses protocolos em `Java`, estaremos mais tranquilas, porém tivemos o gostinho de como se da a implementação desses protocolos em aplicações. 
Além disso, gostaríamos de agradecer novamente a oportunidade de apresentar o trabalho na aula seguinte ao dia 08/11  🤘🙏 :D

P.S: profª Cristina, juramos que o código estava funcionando. Antes de sairmos da aula do Miguel de Sistemas Operacionais tivemos o problema de condição de corrida, mas mesmo assim o código rodava. Foi apenas no laboratório que ele simplesmente congelou o não recebia os pacotes. Valeu demais por não descontar tanto por apresentar atrasado 🙏🙏🙏

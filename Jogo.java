package genius;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class Jogo{
	
	private Botao but1,but2,but3,but4;
	private int[] lista1 = new int[100];//vetor de escolhas do computador
	private int[] lista2= new int[100];//vetor de jogadas humanas 
	private int indice1,indice2;
	private int contagem; //quantidade de botoes apertados em uma rodada
        private JButton butiniciar = new JButton();
	private JFrame geniusFrame;
	
	
	
	public static void main(String[] args){
		
		
		Jogo game = new Jogo();
		game.montaJogo();
		game.Jogadas();
		
	}
	
	
	
	
	public void montaJogo(){	
		/*Configura a frame e adiciona os botoes*/
		
		geniusFrame = new JFrame();
		geniusFrame.setLayout(new GridLayout(2,2));
        geniusFrame.setSize(370, 300);
		geniusFrame.setLocationRelativeTo(null);
        geniusFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        but1 = new Botao("/Users/manoelneto/eclipse-workspace/Alunos/src/genius/Conteudo/VermelhoNormal.Gif","/Users/manoelneto/eclipse-workspace/Alunos/src/genius/Conteudo/VermelhoApertado.Gif","/Users/manoelneto/eclipse-workspace/Alunos/src/genius/Conteudo/som1.wav");
        but2 = new Botao("/Users/manoelneto/eclipse-workspace/Alunos/src/genius/Conteudo/AzulNormal.Gif","/Users/manoelneto/eclipse-workspace/Alunos/src/genius/Conteudo/AzulApertado.Gif","/Users/manoelneto/eclipse-workspace/Alunos/src/genius/Conteudo/som2.wav");
        but3 = new Botao("/Users/manoelneto/eclipse-workspace/Alunos/src/genius/Conteudo/VerdeNormal.Gif","/Users/manoelneto/eclipse-workspace/Alunos/src/genius/Conteudo/VerdeApertado.Gif","/Users/manoelneto/eclipse-workspace/Alunos/src/genius/Conteudo/som3.wav");
        but4 = new Botao("/Users/manoelneto/eclipse-workspace/Alunos/src/genius/Conteudo/AmareloNormal.Gif","/Users/manoelneto/eclipse-workspace/Alunos/src/genius/Conteudo/AmareloApertado.Gif","/Users/manoelneto/eclipse-workspace/Alunos/src/genius/Conteudo/som4.wav");
        
        but1.getBotao().addActionListener(new Innerbut1());
        but2.getBotao().addActionListener(new Innerbut2());
        but3.getBotao().addActionListener(new Innerbut3());
        but4.getBotao().addActionListener(new Innerbut4());
       
        geniusFrame.getContentPane().add(but1.getBotao());
        geniusFrame.getContentPane().add(but2.getBotao());
        geniusFrame.getContentPane().add(but3.getBotao());
        geniusFrame.getContentPane().add(but4.getBotao());	
        
        
        geniusFrame.setVisible(true);
        
        }
	
	
	
	public void escolhaPC(){
		/*Escolha dos botoes e alocacao de seus numeros no vetor de escolhas do computador.
		 *Pisca os botoes contidos no vetor.
		 */	 
			
			
		int numeroBotao = (int)(Math.random() * 4); 
			lista1[indice1] = numeroBotao;
			
			
		for (int z = 0;z<=indice1;z++){//pisca os botoes escolhidos pelo computador ate o momento
			if (lista1[z]==0){ 
				try{
					Thread.sleep(900);
					but1.apertaBotao();
					Thread.sleep(500);
					but1.desapertaBotao();
						
				}catch(Exception e){
				}
			}
			if (lista1[z]==1){ 
				try{
					Thread.sleep(900);
					but2.apertaBotao();
					Thread.sleep(500);
					but2.desapertaBotao();
						
				}catch(Exception e){
				}
			}
			if (lista1[z]==2){ 
				try{
					Thread.sleep(900);
					but3.apertaBotao();
					Thread.sleep(500);
					but3.desapertaBotao();
						
				}catch(Exception e){
				}
			}
			if (lista1[z]==3){ 
				try{
					Thread.sleep(900);
					but4.apertaBotao();
					Thread.sleep(500);
					but4.desapertaBotao();
						
				}catch(Exception e){
				}
			}
		
				
				
		}
			
			
			
	}
	
	
	
	public void Jogadas(){
		/*Roda o loop das jogadas*/
		
		//inicializa as variaveis no inicio do jogo
		indice1=0;
		indice2=0;
		contagem = 0;
		
		
		boolean acabou = true;
		boolean liberado = true;
		
		while(acabou){
			//vez do COMPUTADOR
			if (liberado == true){//Se o computador estiver liiberado , faz sua jogada
				geniusFrame.setTitle("Aguarde....");
				escolhaPC();
				
				indice1=indice1+1;//incrementa a posicao para a proxima rodada
				liberado = false;
				geniusFrame.setTitle("Sua vez!");
			}
			
			//vez do JOGADOR
			if(contagem == indice1){//Aguarda ate que o jogador aperte a quantidade certa de botoes
				
				
				for (int x = 0;x<indice1;x++){
						if (lista2[x] == lista1[x]){
					
							contagem = 0;
							liberado = true;
							//Se acertou, zera a contagem de botoes apertados e libera o computador para a proxima rodada
							
						}else{
							JOptionPane.showMessageDialog(geniusFrame,"Game Over!");
							System.exit(0);

						}
				}
				indice2 = 0;//indice de jogadas humanas retorna a 0 para a proxima rodada
			}
		}
	}
	
        
        
        
        public class Innerbut1 implements ActionListener{

        public void actionPerformed(ActionEvent e) {
                                but1.getSom().play();
				lista2[indice2]=0;
				indice2=indice2+1;
				contagem=contagem+1;
        }
                            
                        }
        class Innerbut2 implements ActionListener{

        public void actionPerformed(ActionEvent e) {
                                but2.getSom().play();
				lista2[indice2]=1;
				indice2=indice2+1;
				contagem=contagem+1;
        }
                            
                        }
        class Innerbut3 implements ActionListener{

        public void actionPerformed(ActionEvent e) {
           			but3.getSom().play();
				lista2[indice2]=2;
				indice2=indice2+1;
				contagem=contagem+1;
        }
                            
                        }
        class Innerbut4 implements ActionListener{

        public void actionPerformed(ActionEvent e) {
                                but4.getSom().play();
                                lista2[indice2]=3;
                                indice2=indice2+1;
                                contagem=contagem+1;
        }
                            
                        }
}

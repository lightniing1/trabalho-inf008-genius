package genius;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Jogo{
	
    private Botao but1,but2,but3,but4;
    private int[] lista1 = new int[100];//vetor de escolhas do computador
    private int[] lista2 = new int[100];//vetor de jogadas humanas 
    private int indice1, indice2;
    private int contagem; //quantidade de botoes apertados em uma rodada
    private JButton butiniciar = new JButton();
    private static JFrame geniusFrame;
    volatile boolean acabou = true;
    volatile boolean liberado = true;
    
    ArrayList<Jogador> jogadores = new ArrayList<>();
    
    public static void main(String[] args){
    	
        Jogo game = new Jogo();
        game.montaJogo();
        
        //Escolha modo de jogo: Normal ou Campeonato
        game.SelecaoMenu();
        
        //Pega o nome do jogador e adiciona
        game.AdicionaJogador();
        
        //Player joga at� perder. Ao perder, passa a vez.
        for(int i = 0; i < game.jogadores.size(); i++){
            game.Jogadas(game.jogadores.get(i));
        }

        game.mostraPontuacaoFinal(game.jogadores);
        System.exit(0);
    }
    
    public String SelecaoMenu() { //Talvez seria melhor retornar um integer aqui...
    	String[] opcoesMenu = {"Normal", "Campeonato"};
    	
    	String selecaoMenu = (String) JOptionPane.showInputDialog(geniusFrame, 
                "Qual modo de jogo deseja jogar?",
                "Modo de Jogo",
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                opcoesMenu, 
                opcoesMenu[0]);
    	
    	return selecaoMenu;
    }
    
    public void AdicionaJogador() {
    	Integer NumeroJogador = jogadores.size();
    	String nome = JOptionPane.showInputDialog(geniusFrame, "Qual o seu nome?", "Nome");
    	if (nome == null || nome.isEmpty() || nome.equals("Nome")) {
    		nome = "Jogador" + " " + (NumeroJogador + 1);
    	}
        jogadores.add(new Jogador(nome));
    }
    
    public void mostraPontuacaoFinal(ArrayList<Jogador> jogadores){
        String pontuacaoFinal = "";
        for(int i = 0; i < jogadores.size(); i++){
            pontuacaoFinal += jogadores.get(i).getNome() + "\nPontuacao: " + jogadores.get(i).getPontuacao() + "\n\n";
        }

        JOptionPane.showMessageDialog(geniusFrame, pontuacaoFinal);
    }

    public void montaJogo(){	
        /*Configura a frame e adiciona os botoes*/

        geniusFrame = new JFrame();
        geniusFrame.setLayout(new GridLayout(2,2));
        geniusFrame.setSize(370, 300);
        geniusFrame.setLocationRelativeTo(null);
        geniusFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        but1 = new Botao("Conteudo/VermelhoNormal.GIF","Conteudo/VermelhoApertado.GIF","Conteudo/vermelho.wav");
        but2 = new Botao("Conteudo/AzulNormal.GIF","Conteudo/AzulApertado.GIF","Conteudo/azul.wav");
        but3 = new Botao("Conteudo/VerdeNormal.GIF","Conteudo/VerdeApertado.GIF","Conteudo/verde.wav");
        but4 = new Botao("Conteudo/AmareloNormal.GIF","Conteudo/AmareloApertado.GIF","Conteudo/amarelo.wav");

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

    public void escolhaPC(int dificuldade){
        /*Escolha dos botoes e alocacao de seus numeros no vetor de escolhas do computador.
         *Pisca os botoes contidos no vetor.
         */	 

        int numeroBotao = (int)(Math.random() * 4);
        lista1[indice1] = numeroBotao;
        
        //Velocidade inicial
        int aperto_inicial = 1000; //Tempo de espera antes de aperta o bot�o
        int aperto_final = 1000; //Tempo que o bot�o fica pressionado
        
        //Verifica se dificuldade aumentou
        if (dificuldade >= 2) {
        	aperto_inicial = 300;
        	aperto_final = 300;
        }

        for (int z = 0; z <= indice1; z++){//pisca os botoes escolhidos pelo computador ate o momento
                if (lista1[z] == 0){ 
                        try{
                                Thread.sleep(aperto_inicial);
                                but1.apertaBotao();
                                Thread.sleep(aperto_final);
                                but1.desapertaBotao();

                        }catch(Exception e){
                        }
                }
                if (lista1[z]==1){ 
                        try{
                                Thread.sleep(aperto_inicial);
                                but2.apertaBotao();
                                Thread.sleep(aperto_final);
                                but2.desapertaBotao();

                        }catch(Exception e){
                        }
                }
                if (lista1[z]==2){ 
                        try{
                                Thread.sleep(aperto_inicial);
                                but3.apertaBotao();
                                Thread.sleep(aperto_final);
                                but3.desapertaBotao();

                        }catch(Exception e){
                        }
                }
                if (lista1[z]==3){ 
                        try{
                                Thread.sleep(aperto_inicial);
                                but4.apertaBotao();
                                Thread.sleep(aperto_final);
                                but4.desapertaBotao();

                        } catch(Exception e){
                        }
                }
        }

    }

    public void Jogadas(Jogador jogador){
            /*Roda o loop das jogadas*/

            //inicializa as variaveis no inicio do jogo
            indice1 = 0;
            indice2 = 0;
            contagem = 0;
            acabou = true;
            liberado = true;
            int dificuldade = 0;
          
            while(acabou){
                    //vez do COMPUTADOR
                    if (liberado == true){//Se o computador estiver liiberado , faz sua jogada
                            geniusFrame.setTitle("Aguarde....");
                            escolhaPC(dificuldade);
                            indice1 = indice1 + 1;//incrementa a posicao para a proxima rodada    
                            liberado = false;
                            geniusFrame.setTitle("Sua vez, " + jogador.getNome() + " (" + jogador.getPontuacao() + ") ");
                    }

                    //vez do JOGADOR
                    if(contagem == indice1){//Aguarda ate que o jogador aperte a quantidade certa de botoes
                       for (int x = 0; x < indice1; x++){
                    	   if (lista2[x] == lista1[x]){
                    		   contagem = 0;
                    		   liberado = true;
                    		   //Se acertou, zera a contagem de botoes apertados e libera o computador para a proxima rodada
                           } else {
                        	   JOptionPane.showMessageDialog(geniusFrame,"Game Over!");
                               return;
                           }
                       }

                       jogador.setPontuacao(jogador.getPontuacao() + 1);
                       dificuldade += 1;
                       //System.out.println("Dificuldade atual: "+dificuldade); 
                       indice2 = 0;//indice de jogadas humanas retorna a 0 para a proxima rodada
                    }
            }
    }

    public class Innerbut1 implements ActionListener{

    public void actionPerformed(ActionEvent e) {
                            but1.getSom().play();
                            lista2[indice2] = 0;
                            indice2 = indice2 + 1;
                            contagem = contagem + 1;
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

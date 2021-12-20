package genius;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Dictionary;

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
    	
    	int nivelDificuldade;
    	boolean progressaoDificuldade = false;
    	String nomeJogador = " ";
    	
        Jogo game = new Jogo();
        game.montaJogo();
        game.carregaJogadores();
        
        //Escolha modo de jogo: Normal ou Campeonato
        String modoJogo = game.SelecaoMenu();

        //Pega o numero de jogadores que irao jogar
        int numJogadores = game.setNumeroJogadores();

        //No modo campeonato, o numero mínimo de jogadores é 2
        if(modoJogo.equals("Campeonato") && numJogadores < 2){
            while(numJogadores < 2){
                JOptionPane.showMessageDialog(geniusFrame,
                        "Numero de jogadores deve ser igual ou maior que 2 para o modo campeonato");
                numJogadores = game.setNumeroJogadores();
            }
        }

        //Pega o nome do jogador e adiciona
        for(int i = 0; i < numJogadores; i++){
            nomeJogador = game.AdicionaJogador();
        }
        
        //Dificuldade
        nivelDificuldade = game.SelecaoDificuldade();
        if (nivelDificuldade == 0) {
        	progressaoDificuldade = true;
        }
        
        //Player joga até perder. Ao perder, passa a vez.
        for(int i = 0; i < game.jogadores.size(); i++){
            game.Jogadas(game.jogadores.get(i), nivelDificuldade, progressaoDificuldade);
        }

        if(modoJogo.equals("Campeonato")){
            game.encerraCampeonato();
        } else {
            game.mostraPontuacaoFinal(nomeJogador);
        }
        
        game.salvaJogadores();
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
    	
    	if (selecaoMenu == null) {
    		selecaoMenu = "Normal";
    	}
    	
    	return selecaoMenu;
    }
    
    public int SelecaoDificuldade() {
    	//Verificar se um dicionario pode ser melhor aqui
    	int selecao = 0;
    	int dificuldade = 0;
    	String[] opcoesMenuDificuldade = {"Progressao", "Fácil", "Médio", "Difícil"};
    	
    	String selecaoMenuDificuldade = (String) JOptionPane.showInputDialog(geniusFrame,
    			"Selecione a dificuldade",
    			"Dificuldade",
    			JOptionPane.QUESTION_MESSAGE,
    			null,
    			opcoesMenuDificuldade,
    			opcoesMenuDificuldade[0]);
    
    	
    	for (int i = 0; i < opcoesMenuDificuldade.length; i++) {
    		if (opcoesMenuDificuldade[i].equals(selecaoMenuDificuldade)) {
    			selecao = i;
    		}
    	}
    	
    	switch (selecao) {
    		case 0: //Progressivo
    			dificuldade = 0;
    			break;
    		case 1: //Facil
    			dificuldade = 1;
    			break;
    		case 2: //Medio
    			dificuldade = 5;
    			break;
    		case 3: //Dificil
    			dificuldade = 12;
    			break;
    	}
    	
    	return dificuldade;
    }

    public int setNumeroJogadores() {
        int numJogadores = 0;
        
        String numeroJogadoresStr = JOptionPane.showInputDialog(geniusFrame, "Quantos jogadores irão jogar?",
                "1");
        
        if (numeroJogadoresStr == null || numeroJogadoresStr.isEmpty()) {
        	numJogadores = 1;
        	return numJogadores;
        }
        
        numJogadores = Integer.parseInt(numeroJogadoresStr);
        return numJogadores;
    }
    
    public String AdicionaJogador() {
    	String nome = JOptionPane.showInputDialog(geniusFrame, "Qual o seu nome?", "Nome");
    	
    	 if (nome == null || nome.isEmpty() || nome.equals("Nome")) {
    		nome = "Jogador";
    	}
    	 
    	 for (int i = 0; i < jogadores.size(); i++) {
    		 if (jogadores.get(i).getNome().equals(nome)) {
    			 return nome;
    		 }
    	 }
    	 
    	jogadores.add(new Jogador(nome));
        return nome;
    	 
    }

    
    public void mostraPontuacaoFinal(String nomeJogador){
        String pontuacaoFinal = "";
        
        for(int i = 0; i < jogadores.size(); i++){
        	if (jogadores.get(i).getNome().equals(nomeJogador)) {
	            pontuacaoFinal += jogadores.get(i).getNome() + 
	            				"\nPontuacao: " + jogadores.get(i).getPontuacao() +
	            				"\nPontuacao máxima: " + jogadores.get(i).getPontuacaoMaxima() + "\n\n";
        	}
        }

        JOptionPane.showMessageDialog(geniusFrame, pontuacaoFinal);
    }

    public void encerraCampeonato(){
        int tempoTotalPartida = 0;
        int totalDeRounds = 0;
        String estatisticas = ""; //string de estatisticas de cada jogador
        String vencedor = "";
        int pontosVencedor = 0;

        for(int i = 0; i < jogadores.size(); i++){
            estatisticas += jogadores.get(i).getNome() + "\nPontuacao: " + jogadores.get(i).getPontuacao() +
                    "\nTempo na partida: " + jogadores.get(i).getTempoNaPartida()
                    + " segundos" + "\nRound em que perdeu: " + (jogadores.get(i).getPontuacao() + 1) + "\n\n";

            if(jogadores.get(i).getPontuacao() > pontosVencedor){
                pontosVencedor = jogadores.get(i).getPontuacao();
                vencedor = jogadores.get(i).getNome();
            }

            //o tempo total de partida é a soma do tempo total de todos os jogadores
            tempoTotalPartida += jogadores.get(i).getTempoNaPartida();
            //o numero de rounds jogados por um jogador é igual ao numero de rounds vencidos + 1 (que é onde ele perdeu)
            totalDeRounds += (jogadores.get(i).getPontuacao() + 1);
        }

        String geral = ""; //string de informacoes gerais sobre a partida
        geral += "Vencedor :" + vencedor + "\nNumero de Jogadores: " + jogadores.size() +
                "\nTempo total de partida: " + tempoTotalPartida + " segundos" + "\nNumero de rounds: " + totalDeRounds;

        JOptionPane.showMessageDialog(geniusFrame, geral);
        JOptionPane.showMessageDialog(geniusFrame, estatisticas);
    }
    
    public void salvaJogadores(){
    	try {
    		FileOutputStream arquivo = new FileOutputStream("genius.save"); //Cria o arquivo
    		ObjectOutputStream dadosSave = new ObjectOutputStream(arquivo); //Escreve o objeto no arquivo
    		
    		dadosSave.writeObject(jogadores);
    		dadosSave.close();
    		arquivo.close();
    		
    	} catch (Exception ex) {
    		//ex.printStackTrace();
    		/*
    		JOptionPane.showMessageDialog(geniusFrame, 
    				"Erro ao salvar o jogo",
    				"Erro",
    				JOptionPane.ERROR_MESSAGE);
    		*/
    		System.out.println("Erro ao salvar arquivo.");
    	}
    }
    
    public void carregaJogadores(){
    	try {
    		FileInputStream arquivo = new FileInputStream("genius.save"); 
    		ObjectInputStream dadosLoad = new ObjectInputStream (arquivo); //Carrega o objeto no arquivo
    		
    		jogadores = (ArrayList) dadosLoad.readObject();
    		dadosLoad.close();
    		arquivo.close();
    		
    	} catch (IOException ioErro) {
    		System.out.println("Save não encontrado. Inciando jogo...");
    		//ioErro.printStackTrace();
    		
    	} catch (ClassNotFoundException clErro) {
    		System.out.println("Erro de leitura: Classe 'Jogador' não pôde ser carregada");
    		//clErro.printStackTrace();
    	}
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
        
        int tempo_aperta_botao;
        int tempo_desaperta_botao;
        
        if (dificuldade < 5) {
        	tempo_aperta_botao = 600;
            tempo_desaperta_botao = 600;
            //System.out.println("Dificuldade facil | Dificuldade = " + dificuldade);
        } else if (dificuldade >= 5 && dificuldade <= 11) {
        	tempo_aperta_botao = 500;
        	tempo_desaperta_botao = 400;
        	//System.out.println("Dificuldade media | Dificuldade = " + dificuldade);
        } else {
        	tempo_aperta_botao = 250;
        	tempo_desaperta_botao = 230;
        	//System.out.println("Dificuldade dificil | Dificuldade = " + dificuldade);
        }
        
        for (int z = 0; z <= indice1; z++){//pisca os botoes escolhidos pelo computador ate o momento
                if (lista1[z] == 0){ 
                        try{
                                Thread.sleep(tempo_aperta_botao);
                                but1.apertaBotao();
                                Thread.sleep(tempo_desaperta_botao);
                                but1.desapertaBotao();

                        }catch(Exception e){
                        }
                }
                if (lista1[z]==1){ 
                        try{
                                Thread.sleep(tempo_aperta_botao);
                                but2.apertaBotao();
                                Thread.sleep(tempo_desaperta_botao);
                                but2.desapertaBotao();

                        }catch(Exception e){
                        }
                }
                if (lista1[z]==2){ 
                        try{
                                Thread.sleep(tempo_aperta_botao);
                                but3.apertaBotao();
                                Thread.sleep(tempo_desaperta_botao);
                                but3.desapertaBotao();

                        }catch(Exception e){
                        }
                }
                if (lista1[z]==3){ 
                        try{
                                Thread.sleep(tempo_aperta_botao);
                                but4.apertaBotao();
                                Thread.sleep(tempo_desaperta_botao);
                                but4.desapertaBotao();

                        } catch(Exception e){
                        }
                }
        }

    }

    public void Jogadas(Jogador jogador, int nivelDificuldade, boolean progressaoDificuldade){
    	
            /*Roda o loop das jogadas*/
            //inicializa as variaveis no inicio do jogo
            indice1 = 0;
            indice2 = 0;
            contagem = 0;
            acabou = true;
            liberado = true;
            jogador.comecarContagemTempo();

            while(acabou){
                    //vez do COMPUTADOR
                    if (liberado == true){//Se o computador estiver liiberado , faz sua jogada
                            geniusFrame.setTitle("Aguarde....");
                            try {
                            	Thread.sleep(1000); //Espera 1 segundo antes do computador iniciar a rodada. Necessario devido a velocidade dos botões aumentar a dificuldade
                            } catch (InterruptedException e){}
                            escolhaPC(nivelDificuldade);
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
                               jogador.pararContagemTempo();
                        	   JOptionPane.showMessageDialog(geniusFrame,"Game Over!");
                               return;
                           }
                       }

                       jogador.setPontuacao(jogador.getPontuacao() + 1);
                       indice2 = 0;//indice de jogadas humanas retorna a 0 para a proxima rodada
                       if (progressaoDificuldade) {
                    	   nivelDificuldade += 1; //Dificuldade aumenta a cada rodada
                       }
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

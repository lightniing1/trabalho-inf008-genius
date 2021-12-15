package genius;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Jogador implements Serializable {
	
    private String nome;
    transient private int pontuacao;
    transient private long tempoInicio;
    transient private long tempoNaPartida;
    private int pontuacaoMaxima;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacao = 0;
        this.tempoInicio = 0;
        this.tempoNaPartida = 0;
        this.pontuacaoMaxima = 0;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String getNome() {
        return nome;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public void comecarContagemTempo(){
        this.tempoInicio = System.nanoTime();
    }

    public long getTempoNaPartida() {
        return tempoNaPartida;
    }

    public void pararContagemTempo(){
        long diferenca = System.nanoTime() - this.tempoInicio;
        this.tempoNaPartida = TimeUnit.SECONDS.convert(diferenca, TimeUnit.NANOSECONDS);
    }
    
    public int getPontuacaoMaxima() {
    	if (this.pontuacao > this.pontuacaoMaxima) {
    		this.pontuacaoMaxima = this.pontuacao;
    	}
    	
    	return pontuacaoMaxima;
    }
}

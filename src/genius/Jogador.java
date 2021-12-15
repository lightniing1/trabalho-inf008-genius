package genius;

import java.util.concurrent.TimeUnit;

public class Jogador {
    private String nome;
    private int pontuacao;
    private long tempoInicio;
    private long tempoNaPartida;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacao = 0;
        this.tempoInicio = 0;
        this.tempoNaPartida = 0;
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
}

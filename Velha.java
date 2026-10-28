import jogo.*;
import jogador.*;
import java.io.*;

public class Velha {
    public static void main(String args[]) {
        Tabuleiro tabDoJogo;

        Jogador jogador = null;
        JogadorHumano jogadorH = null;
        JogadorAutomatico jogadorA = null;

        AnalistaSituacional analista;

        boolean jogadorHumanoComeca = (int) (Math.random() * 2) == 0;

        do {
            try {
                tabDoJogo = new Tabuleiro();

                if (jogadorHumanoComeca) {
                    jogadorH = new JogadorHumano(tabDoJogo);
                    jogadorA = new JogadorAutomatico(
                        tabDoJogo,
                        new Marca(jogadorH.getMarca().getSimbolo() == 'X' ? 'O' : 'X')
                    );
                } else {
                    jogadorA = new JogadorAutomatico(tabDoJogo);
                    jogadorH = new JogadorHumano(
                        tabDoJogo,
                        new Marca(jogadorA.getMarca().getSimbolo() == 'X' ? 'O' : 'X')
                    );
                }

                analista = new AnalistaSituacional(tabDoJogo);

                jogador = jogadorHumanoComeca ? jogadorH : jogadorA;

                do {
                    jogador.facaSuaJogada();

                    if (jogador == jogadorH)
                        jogador = jogadorA;
                    else
                        jogador = jogadorH;
                        
                } while (!analista.fimDeJogo());

                if (analista.getMarcaDoVencedor() != null) {
                    if (analista.getMarcaDoVencedor().equals(jogadorH.getMarca())) {
                        jogadorH.vcGanhou();
                    } else if (analista.getMarcaDoVencedor().equals(jogadorA.getMarca())) {
                        jogadorH.vcPerdeu(); // Humano perdeu
                    }
                } else {
                    jogadorH.deuVelha();
                }

                jogadorHumanoComeca = !jogadorHumanoComeca;
                
            } catch (Exception e) {
                System.err.println("Deu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
            
        } while (jogadorH != null && jogadorH.querMaisUmaPartida());
    }
}
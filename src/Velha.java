import jogo.*;
import jogador.*;
import java.io.*;

public class Velha
{
    public static void main (String args [])
    {
        Tabuleiro           tabDoJogo;

        Jogador             jogador  = null;
        JogadorHumano       jogadorH = null;
        JogadorAutomatico   jogadorA = null;

        AnalistaSituacional analista;

        boolean jogadorHumanoComeca = (int)(Math.random()*2) == 0;

        do
        {
            try
            {
                tabDoJogo = new Tabuleiro ();

                if (jogadorHumanoComeca)
                {
                    jogadorH = new JogadorHumano (tabDoJogo);
                    jogadorA = new JogadorAutomatico
                              (tabDoJogo,
                               new Marca(jogadorH.getMarca().getSimbolo()=='X'?'O':'X'));
                }
                else
                {
                    jogadorA = new JogadorAutomatico (tabDoJogo);
                    jogadorH = new JogadorHumano
                              (tabDoJogo,
                               new Marca(jogadorA.getMarca().getSimbolo()=='X'?'O':'X'));
                }

                analista = new AnalistaSituacional (tabDoJogo);

                if (jogadorHumanoComeca)
                    jogador=jogadorH;
                else
                    jogador=jogadorA;

                do
                {
                    jogador.facaSuaJogada();

                    if (jogador==jogadorH)
                        jogador=jogadorA;
                    else
                        jogador=jogadorH;
                }
                while (!analista.fimDeJogo());

                System.out.println();
                System.out.println(tabDoJogo);

                // getMarcaDoVencedor() devolve null quando da velha; testar antes do equals
                Marca vencedor = analista.getMarcaDoVencedor();
                if (vencedor == null)
                    jogadorH.deuVelha ();
                else
                    if (vencedor.equals(jogadorH.getMarca()))
                        jogadorH.vcGanhou ();
                    else
                        jogadorH.vcPerdeu ();

                jogadorHumanoComeca = !jogadorHumanoComeca;
            }
            catch (Exception erro)
            {
                System.err.println(erro.getMessage());
            }
        }
        while (jogadorH != null && jogadorH.querMaisUmaPartida());
    }
}

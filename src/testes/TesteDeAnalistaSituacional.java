package testes;

import jogo.AnalistaSituacional;
import jogo.Marca;
import jogo.Tabuleiro;

public class TesteDeAnalistaSituacional
{
    private static int testes = 0;
    private static int falhas = 0;

    private static void verifique (boolean condicao, String descricao)
    {
        testes++;

        if (condicao)
            System.out.println ("OK     " + descricao);
        else
        {
            falhas++;
            System.out.println ("FALHOU " + descricao);
        }
    }

    // monta um tabuleiro a partir de 9 caracteres: X, O ou . (casa vazia), linha a linha
    private static Tabuleiro monte (String casas) throws Exception
    {
        Tabuleiro tab = new Tabuleiro ();

        for (int i=0; i<9; i++)
        {
            char c = casas.charAt (i);
            if (c!='.')
                tab.setMarcaNaPosicao (new Marca (c), i/3, i%3);
        }

        return tab;
    }

    public static void main (String[] args) throws Exception
    {
        System.out.println ("=== Testes da classe AnalistaSituacional ===\n");

        Marca x = new Marca ('X');
        Marca o = new Marca ('O');

        boolean lancou = false;
        try
        {
            new AnalistaSituacional (null);
        }
        catch (Exception erro)
        {
            lancou = true;
        }
        verifique (lancou, "construtor com tabuleiro null lanca excecao");

        AnalistaSituacional vazio = new AnalistaSituacional (monte ("........."));
        verifique (vazio.getMarcaDoVencedor()==null, "tabuleiro vazio: sem vencedor");
        verifique (!vazio.fimDeJogo(), "tabuleiro vazio: jogo nao acabou");

        AnalistaSituacional linha = new AnalistaSituacional (monte ("XXXOO...."));
        verifique (linha.getMarcaDoVencedor().equals (x), "vitoria de X na linha 0");
        verifique (linha.fimDeJogo(), "vitoria na linha: fim de jogo");

        AnalistaSituacional coluna = new AnalistaSituacional (monte ("XO.XO..OX"));
        verifique (coluna.getMarcaDoVencedor().equals (o), "vitoria de O na coluna 1");
        verifique (coluna.fimDeJogo(), "vitoria na coluna: fim de jogo");

        AnalistaSituacional diagonal = new AnalistaSituacional (monte ("XO..X...X"));
        verifique (diagonal.getMarcaDoVencedor().equals (x), "vitoria de X na diagonal principal");
        verifique (diagonal.fimDeJogo(), "vitoria na diagonal: fim de jogo");

        AnalistaSituacional antiDiagonal = new AnalistaSituacional (monte ("XXO.O.O.."));
        verifique (antiDiagonal.getMarcaDoVencedor().equals (o), "vitoria de O na diagonal secundaria");
        verifique (antiDiagonal.fimDeJogo(), "vitoria na diagonal secundaria: fim de jogo");

        AnalistaSituacional velha = new AnalistaSituacional (monte ("XOXXOOOXX"));
        verifique (velha.getMarcaDoVencedor()==null, "velha: sem vencedor (retorna null)");
        verifique (velha.fimDeJogo(), "velha: tabuleiro cheio e fim de jogo");

        AnalistaSituacional meio = new AnalistaSituacional (monte ("X........"));
        verifique (meio.getMarcaDoVencedor()==null, "jogo em andamento: sem vencedor");
        verifique (!meio.fimDeJogo(), "jogo em andamento: nao e fim de jogo");

        Tabuleiro tab = monte (".........");
        AnalistaSituacional guarda = new AnalistaSituacional (tab);
        tab.setMarcaNaPosicao (x, 0, 0);
        tab.setMarcaNaPosicao (x, 0, 1);
        tab.setMarcaNaPosicao (x, 0, 2);
        verifique (guarda.getMarcaDoVencedor()!=null, "analista guarda a referencia do tabuleiro (ve a mudanca)");

        AnalistaSituacional a1 = new AnalistaSituacional (monte ("X........"));
        AnalistaSituacional a2 = new AnalistaSituacional (monte ("X........"));
        AnalistaSituacional a3 = new AnalistaSituacional (monte ("O........"));

        verifique (a1.equals (a1), "equals: objeto com ele mesmo e true");
        verifique (a1.equals (a2), "equals: tabuleiros iguais e true");
        verifique (!a1.equals (a3), "equals: tabuleiros diferentes e false");
        verifique (!a1.equals (null), "equals: null e false");
        verifique (a1.hashCode()==a2.hashCode(), "hashCode igual para analistas iguais");
        verifique (a1.toString().equals (monte ("X........").toString()), "toString devolve o do tabuleiro");

        System.out.println ("\n" + testes + " testes, " + falhas + " falhas");
    }
}

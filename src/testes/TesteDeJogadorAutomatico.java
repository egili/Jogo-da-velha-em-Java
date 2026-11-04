package testes;

import jogador.JogadorAutomatico;
import jogo.Marca;
import jogo.Tabuleiro;

public class TesteDeJogadorAutomatico
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

    private static int contaMarcas (Tabuleiro tab, char simbolo) throws Exception
    {
        int qtd = 0;

        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
                if (tab.haMarcaNaPosicao (i,j) && tab.getMarcaNaPosicao (i,j).getSimbolo()==simbolo)
                    qtd++;

        return qtd;
    }

    public static void main (String[] args) throws Exception
    {
        System.out.println ("=== Testes da classe JogadorAutomatico ===\n");

        Marca x = new Marca ('X');
        Marca o = new Marca ('O');

        boolean lancou = false;
        try
        {
            new JogadorAutomatico ((Tabuleiro) null);
        }
        catch (Exception erro)
        {
            lancou = true;
        }
        verifique (lancou, "construtor com tabuleiro null lanca excecao");

        lancou = false;
        try
        {
            new JogadorAutomatico (monte ("........."), (Marca) null);
        }
        catch (Exception erro)
        {
            lancou = true;
        }
        verifique (lancou, "construtor com marca null lanca excecao");

        verifique (new JogadorAutomatico (monte ("........."))
                   .getMarca()!=null, "construtor sem marca sorteia uma marca");

        Tabuleiro t1 = monte ("OO.XX....");
        new JogadorAutomatico (t1, o).facaSuaJogada();
        verifique (t1.getMarcaNaPosicao (0,2).equals (o), "vence: completa a propria linha (prioridade sobre bloquear)");

        Tabuleiro t2 = monte ("XX.......");
        new JogadorAutomatico (t2, o).facaSuaJogada();
        verifique (t2.getMarcaNaPosicao (0,2).equals (o), "bloqueia: impede a linha de X");

        Tabuleiro t3 = monte ("....O....");
        new JogadorAutomatico (t3, o).facaSuaJogada();
        verifique (contaMarcas (t3, 'O')==2, "linha com uma marca propria: joga na mesma linha, coluna ou diagonal");

        Tabuleiro t4 = monte (".........");
        new JogadorAutomatico (t4, o).facaSuaJogada();
        verifique (t4.getMarcaNaPosicao (0,0).equals (o), "tabuleiro vazio: joga na primeira casa livre");

        Tabuleiro cheio = monte ("XOXXOOOXX");
        Tabuleiro antes = monte ("XOXXOOOXX");
        new JogadorAutomatico (cheio, o).facaSuaJogada();
        verifique (cheio.toString().equals (antes.toString()), "tabuleiro cheio: nada muda e nao lanca excecao");

        JogadorAutomatico j1 = new JogadorAutomatico (t4, o);
        JogadorAutomatico j2 = new JogadorAutomatico (t4, o);
        JogadorAutomatico j3 = new JogadorAutomatico (t4, x);

        verifique (j1.equals (j1), "equals: objeto com ele mesmo e true");
        verifique (j1.equals (j2), "equals: mesma marca e mesmo tabuleiro e true");
        verifique (!j1.equals (j3), "equals: marca diferente e false");
        verifique (!j1.equals (null), "equals: null e false");
        verifique (j1.hashCode()==j2.hashCode(), "hashCode igual para jogadores iguais");

        JogadorAutomatico copia = new JogadorAutomatico (j1);
        verifique (copia.equals (j1), "construtor de copia gera jogador igual");

        lancou = false;
        try
        {
            new JogadorAutomatico ((JogadorAutomatico) null);
        }
        catch (Exception erro)
        {
            lancou = true;
        }
        verifique (lancou, "construtor de copia com null lanca excecao");

        Object objeto = j1.clone();
        JogadorAutomatico clone = (JogadorAutomatico) objeto;
        verifique (clone != j1 && clone.equals (j1), "clone devolve jogador igual e outro objeto");

        System.out.println ("\n" + testes + " testes, " + falhas + " falhas");
    }
}

package testes;

import jogador.JogadorHumano;
import jogo.Marca;
import jogo.Tabuleiro;

public class TesteDeJogadorHumano
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

    public static void main (String[] args) throws Exception
    {
        // a entrada simulada precisa ser definida antes do primeiro uso de Teclado
        // sequencia: (0,0) valida; (0,0) ocupada e depois (1,1); "x" invalido e depois (2,2);
        // depois S invalido e N para querMaisUmaPartida
        String entrada = "0\n0\n" + "0\n0\n1\n1\n" + "x\n2\n2\n2\n" + "x\nS\nN\n";
        System.setIn (new java.io.ByteArrayInputStream (entrada.getBytes ("UTF-8")));

        System.out.println ("=== Testes da classe JogadorHumano ===\n");

        Marca x = new Marca ('X');
        Marca o = new Marca ('O');
        Tabuleiro tab = new Tabuleiro ();

        boolean lancou = false;
        try
        {
            new JogadorHumano ((Tabuleiro) null, x);
        }
        catch (Exception erro)
        {
            lancou = true;
        }
        verifique (lancou, "construtor com tabuleiro null lanca excecao");

        lancou = false;
        try
        {
            new JogadorHumano (tab, (Marca) null);
        }
        catch (Exception erro)
        {
            lancou = true;
        }
        verifique (lancou, "construtor com marca null lanca excecao");

        JogadorHumano h = new JogadorHumano (tab, x);
        verifique (h.getMarca().equals (x), "getMarca devolve a marca recebida");

        h.facaSuaJogada();
        verifique (tab.getMarcaNaPosicao (0,0).equals (x), "facaSuaJogada: 0,0 digitado e marcado");

        h.facaSuaJogada();
        verifique (tab.getMarcaNaPosicao (1,1).equals (x), "facaSuaJogada: casa ocupada pede de novo, 1,1 e marcado");
        verifique (tab.getMarcaNaPosicao (0,0).equals (x), "facaSuaJogada: casa ocupada nao foi sobrescrita");

        h.facaSuaJogada();
        verifique (tab.getMarcaNaPosicao (2,2).equals (x), "facaSuaJogada: entrada nao numerica e repetida, 2,2 e marcado");

        boolean continua = h.querMaisUmaPartida();
        verifique (continua, "querMaisUmaPartida: resposta invalida e repetida, S devolve true");
        verifique (!h.querMaisUmaPartida(), "querMaisUmaPartida: N devolve false");

        JogadorHumano outro = new JogadorHumano (tab, o);
        verifique (h.equals (h), "equals: objeto com ele mesmo e true");
        verifique (!h.equals (outro), "equals: marca diferente e false");
        verifique (!h.equals (null), "equals: null e false");
        verifique (h.hashCode()==new JogadorHumano (tab, x).hashCode(), "hashCode igual para jogadores iguais");

        JogadorHumano copia = new JogadorHumano (h);
        verifique (copia.equals (h), "construtor de copia gera jogador igual");

        lancou = false;
        try
        {
            new JogadorHumano ((JogadorHumano) null);
        }
        catch (Exception erro)
        {
            lancou = true;
        }
        verifique (lancou, "construtor de copia com null lanca excecao");

        Object objeto = h.clone();
        JogadorHumano clone = (JogadorHumano) objeto;
        verifique (clone != h && clone.equals (h), "clone devolve jogador igual e outro objeto");

        System.out.println ("\n" + testes + " testes, " + falhas + " falhas");
    }
}

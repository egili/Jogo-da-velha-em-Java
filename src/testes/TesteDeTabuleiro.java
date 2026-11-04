package testes;

import jogo.Marca;
import jogo.Tabuleiro;

public class TesteDeTabuleiro
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
        System.out.println ("=== Testes da classe Tabuleiro ===\n");

        Marca x = new Marca ('X');
        Marca o = new Marca ('O');
        Tabuleiro t = new Tabuleiro ();

        boolean vazio = true;
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
                if (t.haMarcaNaPosicao (i,j))
                    vazio = false;
        verifique (vazio, "tabuleiro novo esta vazio");

        int[][] invalidas = {{-1,0},{0,-1},{3,0},{0,3}};
        boolean lancouHa = true;
        boolean lancouGet = true;
        boolean lancouSet = true;
        for (int k=0; k<invalidas.length; k++)
        {
            boolean lancou = false;
            try
            {
                t.haMarcaNaPosicao (invalidas[k][0], invalidas[k][1]);
            }
            catch (Exception erro)
            {
                lancou = true;
            }
            lancouHa = lancouHa && lancou;

            lancou = false;
            try
            {
                t.getMarcaNaPosicao (invalidas[k][0], invalidas[k][1]);
            }
            catch (Exception erro)
            {
                lancou = true;
            }
            lancouGet = lancouGet && lancou;

            lancou = false;
            try
            {
                t.setMarcaNaPosicao (x, invalidas[k][0], invalidas[k][1]);
            }
            catch (Exception erro)
            {
                lancou = true;
            }
            lancouSet = lancouSet && lancou;
        }
        verifique (lancouHa, "haMarcaNaPosicao lanca excecao para posicoes invalidas");
        verifique (lancouGet, "getMarcaNaPosicao lanca excecao para posicoes invalidas");
        verifique (lancouSet, "setMarcaNaPosicao lanca excecao para posicoes invalidas");

        boolean lancou = false;
        try
        {
            t.setMarcaNaPosicao (null, 0, 0);
        }
        catch (Exception erro)
        {
            lancou = true;
        }
        verifique (lancou, "setMarcaNaPosicao com marca null lanca excecao");

        t.setMarcaNaPosicao (x, 0, 0);
        t.setMarcaNaPosicao (o, 1, 1);
        verifique (t.haMarcaNaPosicao (0,0), "haMarcaNaPosicao e true apos a marca");
        verifique (t.getMarcaNaPosicao (0,0).equals (x), "getMarcaNaPosicao devolve a marca colocada");
        verifique (!t.haMarcaNaPosicao (2,2), "haMarcaNaPosicao e false em casa vazia");

        lancou = false;
        try
        {
            t.setMarcaNaPosicao (o, 0, 0);
        }
        catch (Exception erro)
        {
            lancou = true;
        }
        verifique (lancou, "setMarcaNaPosicao em casa ocupada lanca excecao");
        verifique (t.getMarcaNaPosicao (0,0).equals (x), "casa ocupada nao muda apos a excecao");

        String[] linhas = t.toString().split ("\n");
        verifique (linhas.length==5, "toString tem 5 linhas (3 de casas e 2 separadores)");
        verifique (linhas.length==5 && linhas[1].equals ("--+---+--"), "toString: primeiro separador e '--+---+--'");
        verifique (linhas.length==5 && linhas[0].startsWith ("X"), "toString: X aparece na primeira casa");

        Tabuleiro outro = new Tabuleiro ();
        outro.setMarcaNaPosicao (x, 0, 0);
        outro.setMarcaNaPosicao (o, 1, 1);

        verifique (t.equals (t), "equals: objeto com ele mesmo e true");
        verifique (t.equals (outro), "equals: mesmas marcas e true");
        verifique (t.hashCode()==outro.hashCode(), "hashCode igual para tabuleiros iguais");
        verifique (!t.equals (new Tabuleiro ()), "equals: tabuleiro vazio e false");
        verifique (!t.equals (null), "equals: null e false");

        Tabuleiro copia = new Tabuleiro (t);
        verifique (copia != t && copia.equals (t), "construtor de copia gera tabuleiro igual");

        Object objeto = t.clone();
        Tabuleiro clone = (Tabuleiro) objeto;
        verifique (clone != t && clone.equals (t), "clone devolve tabuleiro igual");

        t.setMarcaNaPosicao (x, 2, 2);
        verifique (!copia.haMarcaNaPosicao (2,2), "copia nao muda quando a original muda");
        verifique (!clone.haMarcaNaPosicao (2,2), "clone nao muda quando a original muda");

        lancou = false;
        try
        {
            new Tabuleiro ((Tabuleiro) null);
        }
        catch (Exception erro)
        {
            lancou = true;
        }
        verifique (lancou, "construtor de copia com null lanca excecao");

        System.out.println ("\n" + testes + " testes, " + falhas + " falhas");
    }
}

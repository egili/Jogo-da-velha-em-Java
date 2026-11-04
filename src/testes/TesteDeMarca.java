package testes;

import jogo.Marca;

public class TesteDeMarca
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
        System.out.println ("=== Testes da classe Marca ===\n");

        Marca x = new Marca ('X');
        Marca o = new Marca ('O');

        verifique (x.getSimbolo()=='X', "Marca('X') guarda 'X'");
        verifique (o.getSimbolo()=='O', "Marca('O') guarda 'O'");
        verifique (x.toString().equals ("X"), "toString de Marca('X') e 'X'");

        boolean lancou = false;
        try
        {
            new Marca ('A');
        }
        catch (Exception erro)
        {
            lancou = true;
        }
        verifique (lancou, "Marca('A') lanca excecao");

        lancou = false;
        try
        {
            new Marca (' ');
        }
        catch (Exception erro)
        {
            lancou = true;
        }
        verifique (lancou, "Marca(' ') lanca excecao");

        verifique (x.equals (x), "equals: objeto com ele mesmo e true");
        verifique (x.equals (new Marca ('X')), "equals: mesmo simbolo e true");
        verifique (!x.equals (o), "equals: simbolo diferente e false");
        verifique (!x.equals (null), "equals: null e false");
        verifique (!x.equals ("X"), "equals: String e false (outra classe)");
        verifique (x.hashCode()==new Marca ('X').hashCode(), "hashCode igual para objetos iguais");

        System.out.println ("\n" + testes + " testes, " + falhas + " falhas");
    }
}

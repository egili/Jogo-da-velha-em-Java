package jogo;

public class Marca
{
    private char simbolo;

    public Marca (char smb) throws Exception
    {
        char maiusculo = Character.toUpperCase(smb);
        if (maiusculo != 'X' && maiusculo != 'O')
            throw new Exception("Símbolo inválido. Deve ser 'X' ou 'O'.");
        
        this.simbolo = maiusculo;
    }

    public char getSimbolo ()
    {
        return this.simbolo;
    }

    @Override
    public String toString ()
    {
        return ""+simbolo;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
            
        Marca outraMarca = (Marca) obj;
        if (this.simbolo != outraMarca.simbolo)
            return false;
            
        return true;
    }

    @Override
    public int hashCode ()
    {
        int ret = 1;
        ret = 31 * ret + Character.hashCode(this.simbolo);
        return ret < 0 ? -ret : ret;
    }
}
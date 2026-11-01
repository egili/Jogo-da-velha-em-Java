package jogo;

public class Tabuleiro implements Cloneable
{
    // a principio, toda igual a null
    private Marca matriz [][];

    public Tabuleiro ()
    {
        this.matriz = new Marca [3][3];
    }

    public boolean haMarcaNaPosicao (int lin, int col) throws Exception
    {
        if (lin < 0 || lin > 2 || col < 0 || col > 2)
            throw new Exception("Posição inválida.");

        return this.matriz[lin][col] != null;
    }

    public Marca getMarcaNaPosicao (int lin, int col) throws Exception
    {
        if (lin < 0 || lin > 2 || col < 0 || col > 2)
            throw new Exception("Posição inválida.");

        return this.matriz[lin][col];
    }

    public void setMarcaNaPosicao (Marca mrc, int lin, int col) throws Exception
    {
        if (lin < 0 || lin > 2 || col < 0 || col > 2)
            throw new Exception("Posição inválida.");
        if (mrc == null)
            throw new Exception("Marca nula.");

        this.matriz[lin][col] = new Marca(mrc.getSimbolo());
    }

    @Override
    public String toString ()
    {
        String ret = "";
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (this.matriz[i][j] == null)
                    ret += " ";
                else
                    ret += this.matriz[i][j];

                if (j < 2)
                    ret += " | ";
            }
            ret += "\n";
            if (i < 2)
                ret += "-----------\n";
        }
        return ret;
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

        Tabuleiro outroTab = (Tabuleiro) obj;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (this.matriz[i][j] == null && outroTab.matriz[i][j] != null)
                    return false;
                if (this.matriz[i][j] != null && !this.matriz[i][j].equals(outroTab.matriz[i][j]))
                    return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode ()
    {
        int ret = 1;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (this.matriz[i][j] != null)
                    ret = 31 * ret + this.matriz[i][j].hashCode();
                else
                    ret = 31 * ret;
            }
        }
        return ret < 0 ? -ret : ret;
    }

    // construtor de copia
    public Tabuleiro (Tabuleiro modelo) throws Exception
    {
        if (modelo == null)
            throw new Exception("Modelo ausente!");

        this.matriz = new Marca[3][3];
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (modelo.matriz[i][j] != null)
                    this.matriz[i][j] = new Marca(modelo.matriz[i][j].getSimbolo());
                else
                    this.matriz[i][j] = null;
            }
        }
    }

    @Override
    public Object clone ()
    {
        Tabuleiro ret = null;
        try
        {
            ret = new Tabuleiro(this);
        }
        catch (Exception e)
        {}
        return ret;
    }
}
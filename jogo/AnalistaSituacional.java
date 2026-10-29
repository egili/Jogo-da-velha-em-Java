package jogo;

public class AnalistaSituacional
{
    private Tabuleiro tabDoJogo;

    public AnalistaSituacional (Tabuleiro tab) throws Exception
    {
        if (tab == null)
            throw new Exception("Tabuleiro ausente.");
            
        this.tabDoJogo = tab;
    }

    public Marca getMarcaDoVencedor ()
    {
        try
        {
            for (int i = 0; i < 3; i++)
            {
                if (tabDoJogo.getMarcaNaPosicao(i, 0) != null &&
                    tabDoJogo.getMarcaNaPosicao(i, 0).equals(tabDoJogo.getMarcaNaPosicao(i, 1)) &&
                    tabDoJogo.getMarcaNaPosicao(i, 1).equals(tabDoJogo.getMarcaNaPosicao(i, 2)))
                {
                    return tabDoJogo.getMarcaNaPosicao(i, 0);
                }
            }

            for (int j = 0; j < 3; j++)
            {
                if (tabDoJogo.getMarcaNaPosicao(0, j) != null &&
                    tabDoJogo.getMarcaNaPosicao(0, j).equals(tabDoJogo.getMarcaNaPosicao(1, j)) &&
                    tabDoJogo.getMarcaNaPosicao(1, j).equals(tabDoJogo.getMarcaNaPosicao(2, j)))
                {
                    return tabDoJogo.getMarcaNaPosicao(0, j);
                }
            }

            if (tabDoJogo.getMarcaNaPosicao(0, 0) != null &&
                tabDoJogo.getMarcaNaPosicao(0, 0).equals(tabDoJogo.getMarcaNaPosicao(1, 1)) &&
                tabDoJogo.getMarcaNaPosicao(1, 1).equals(tabDoJogo.getMarcaNaPosicao(2, 2)))
            {
                return tabDoJogo.getMarcaNaPosicao(0, 0);
            }

            if (tabDoJogo.getMarcaNaPosicao(0, 2) != null &&
                tabDoJogo.getMarcaNaPosicao(0, 2).equals(tabDoJogo.getMarcaNaPosicao(1, 1)) &&
                tabDoJogo.getMarcaNaPosicao(1, 1).equals(tabDoJogo.getMarcaNaPosicao(2, 0)))
            {
                return tabDoJogo.getMarcaNaPosicao(0, 2);
            }
        }
        catch (Exception e)
        {}

        return null;
    }

    private boolean tabuleiroCheio ()
    {
        try
        {
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (!tabDoJogo.haMarcaNaPosicao(i, j))
                        return false;
                }
            }
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public boolean fimDeJogo ()
    {
        if (getMarcaDoVencedor() != null)
            return true;
            
        if (tabuleiroCheio())
            return true;
            
        return false;
    }

    @Override
    public String toString ()
    {
        return tabDoJogo.toString();
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

        AnalistaSituacional outroAnalista = (AnalistaSituacional) obj;
        if (!this.tabDoJogo.equals(outroAnalista.tabDoJogo))
            return false;

        return true;
    }

    @Override
    public int hashCode ()
    {
        int ret = 1;
        ret = 31 * ret + (this.tabDoJogo == null ? 0 : this.tabDoJogo.hashCode());
        return ret < 0 ? -ret : ret;
    }
}
package jogador;

import jogo.Marca;
import jogo.Tabuleiro;

public class JogadorAutomatico implements Jogador, Cloneable
{
    private Marca     marca     = null;
    private Tabuleiro tabDoJogo = null;

    public JogadorAutomatico (Tabuleiro tab) throws Exception
    {
        if (tab == null)
            throw new Exception ("Tabuleiro ausente");

        // guarda a referencia (sem clone), pois precisa jogar no tabuleiro do jogo
        this.tabDoJogo = tab;

        if ((int)(Math.random()*2) == 0)
            this.marca = new Marca ('X');
        else
            this.marca = new Marca ('O');
    }

    public JogadorAutomatico (Tabuleiro tab, Marca mrc) throws Exception
    {
        if (tab == null)
            throw new Exception ("Tabuleiro ausente");
        if (mrc == null)
            throw new Exception ("Marca ausente");

        this.tabDoJogo = tab;
        this.marca     = mrc; // Marca e imutavel, entao nao precisa de copia
    }

    public Marca getMarca ()
    {
        return this.marca;
    }

    public void facaSuaJogada ()
    {
        try
        {
            Marca oponente = new Marca (this.marca.getSimbolo()=='X'?'O':'X');

            // tentando ganhar
            if (this.facaJogadaEstrategica(this.marca, 2))
                return;

            // tentando atrapalhar vitoria
            if (this.facaJogadaEstrategica(oponente, 2))
                return;

            // tentando jogada de futuro (ha 1 marca do jogador e mais nada; vamos la e plantamos outra marca)
            if (this.facaJogadaEstrategica(this.marca, 1))
                return;

            // termine jogando num lugar qualquer que esteja livre
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (!this.tabDoJogo.haMarcaNaPosicao(i, j))
                    {
                        this.tabDoJogo.setMarcaNaPosicao(this.marca, i, j);
                        return;
                    }
        }
        catch (Exception erro)
        {} // ignorando pois sei que não estou usando posição inválida nem comparando com equals um null
    }

    // percorre as 3 linhas, as 3 colunas e as 2 diagonais
    private boolean facaJogadaEstrategica (Marca alvo, int qtdNecessaria) throws Exception
    {
        for (int i = 0; i < 3; i++)
            if (this.chequeEJogue(alvo, qtdNecessaria, i, 0, i, 1, i, 2))
                return true;

        for (int j = 0; j < 3; j++)
            if (this.chequeEJogue(alvo, qtdNecessaria, 0, j, 1, j, 2, j))
                return true;

        if (this.chequeEJogue(alvo, qtdNecessaria, 0, 0, 1, 1, 2, 2))
            return true;

        return this.chequeEJogue(alvo, qtdNecessaria, 0, 2, 1, 1, 2, 0);
    }

    // joga numa casa vazia da trinca se ela tiver qtdNecessaria marcas alvo e o resto vazio
    private boolean chequeEJogue (Marca alvo, int qtdNecessaria,
                                  int l1, int c1, int l2, int c2, int l3, int c3) throws Exception
    {
        int lins [] = {l1, l2, l3};
        int cols [] = {c1, c2, c3};

        int qtdAlvo  = 0;
        int qtdVazio = 0;
        int linVazia = -1;
        int colVazia = -1;

        for (int p = 0; p < 3; p++)
        {
            if (!this.tabDoJogo.haMarcaNaPosicao(lins[p], cols[p]))
            {
                qtdVazio++;
                linVazia = lins[p];
                colVazia = cols[p];
            }
            else if (this.tabDoJogo.getMarcaNaPosicao(lins[p], cols[p]).equals(alvo))
                qtdAlvo++;
        }

        if (qtdAlvo != qtdNecessaria || qtdVazio != 3 - qtdNecessaria)
            return false;

        this.tabDoJogo.setMarcaNaPosicao(this.marca, linVazia, colVazia);
        return true;
    }

    @Override
    public String toString ()
    {
        return "Jogador Automático [Marca: " + this.marca + "]";
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

        JogadorAutomatico outro = (JogadorAutomatico) obj;
        if (!this.marca.equals(outro.marca))
            return false;
        if (!this.tabDoJogo.equals(outro.tabDoJogo))
            return false;

        return true;
    }

    @Override
    public int hashCode ()
    {
        int ret = 1;

        ret = ret*2 + this.marca    .hashCode();
        ret = ret*2 + this.tabDoJogo.hashCode();

        if (ret<0) ret=-ret;

        return ret;
    }

    // construtor de cópia
    public JogadorAutomatico (JogadorAutomatico modelo) throws Exception
    {
        if (modelo == null)
            throw new Exception ("Modelo ausente");

        this.marca     = modelo.marca;     // Marca e imutavel
        this.tabDoJogo = modelo.tabDoJogo; // de proposito sem clone: a copia joga no mesmo tabuleiro
    }

    @Override
    public Object clone ()
    {
        JogadorAutomatico ret = null;
        try
        {
            ret = new JogadorAutomatico (this);
        }
        catch (Exception erro)
        {} // sei que nao vai dar erro, pois this nunca e null
        return ret;
    }
}

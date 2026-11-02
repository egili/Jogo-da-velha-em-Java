package jogo;

public class AnalistaSituacional {

    private Tabuleiro tabDoJogo;

    public AnalistaSituacional(Tabuleiro tab) throws Exception {
        
        if (tab == null) {
            throw new Exception ("Tabuleiro ausente");
        }

        this.tabDoJogo = tab;
    }

    public Marca getMarcaDoVencedor() {
        try {
            for (int i = 0; i < 3; i++) {
                if (this.tabDoJogo.getMarcaNaPosicao(i, 0) != null && this.tabDoJogo.getMarcaNaPosicao(i, 0).equals(this.tabDoJogo.getMarcaNaPosicao(i, 1)) && this.tabDoJogo.getMarcaNaPosicao(i, 1).equals(this.tabDoJogo.getMarcaNaPosicao(i, 2))) {
                    return this.tabDoJogo.getMarcaNaPosicao(i, 0);
                }
            }

            for (int j = 0; j < 3; j++) {
                if (this.tabDoJogo.getMarcaNaPosicao(0, j) != null && this.tabDoJogo.getMarcaNaPosicao(0, j).equals(this.tabDoJogo.getMarcaNaPosicao(1, j)) && this.tabDoJogo.getMarcaNaPosicao(1, j).equals(this.tabDoJogo.getMarcaNaPosicao(2, j))) {
                    return this.tabDoJogo.getMarcaNaPosicao(0, j);
                }
            }

            if (this.tabDoJogo.getMarcaNaPosicao(0, 0) != null && this.tabDoJogo.getMarcaNaPosicao(0, 0).equals(this.tabDoJogo.getMarcaNaPosicao(1, 1)) && this.tabDoJogo.getMarcaNaPosicao(1, 1).equals(this.tabDoJogo.getMarcaNaPosicao(2, 2))) {
                return this.tabDoJogo.getMarcaNaPosicao(0, 0);
            }

            if (this.tabDoJogo.getMarcaNaPosicao(0, 2) != null && this.tabDoJogo.getMarcaNaPosicao(0, 2).equals(this.tabDoJogo.getMarcaNaPosicao(1, 1)) && this.tabDoJogo.getMarcaNaPosicao(1, 1).equals(this.tabDoJogo.getMarcaNaPosicao(2, 0))) {
                return this.tabDoJogo.getMarcaNaPosicao(0, 2);
            }
        }
        catch (Exception erro)
        {} 

        return null;
    }

    private boolean tabuleiroCheio() {
        try {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (!this.tabDoJogo.haMarcaNaPosicao(i, j)) {
                        return false;
                    }
                }
            }
        }
        catch (Exception erro)
        {}
        return true;
    }

    public boolean fimDeJogo() {
        return this.getMarcaDoVencedor() != null || this.tabuleiroCheio();
    }

    @Override
    public String toString() {
        return this.tabDoJogo.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }

        AnalistaSituacional outroAnalista = (AnalistaSituacional) obj;
        if (!this.tabDoJogo.equals(outroAnalista.tabDoJogo)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {

        int ret = 1;

        ret = ret * 2 + this.tabDoJogo.hashCode();

        return ret < 0 ? -ret : ret;
    }
}

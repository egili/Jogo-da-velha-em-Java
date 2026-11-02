package jogo;

public class Tabuleiro implements Cloneable {

    private Marca matriz [][];

    public Tabuleiro() {
        this.matriz = new Marca [3][3];
    }

    public boolean haMarcaNaPosicao(int lin, int col) throws Exception {

        if (lin < 0 || lin > 2 || col < 0 || col > 2) {
            throw new Exception ("Posição inválida");
        }

        return this.matriz[lin][col] != null;
    }

    public Marca getMarcaNaPosicao(int lin, int col) throws Exception {

        if (lin < 0 || lin > 2 || col < 0 || col > 2) {
            throw new Exception ("Posição inválida");
        }

        return this.matriz[lin][col];
    }

    public void setMarcaNaPosicao(Marca mrc, int lin, int col) throws Exception {
        
        if (lin < 0 || lin > 2 || col < 0 || col > 2) {
            throw new Exception ("Posição inválida");
        }
        if (mrc == null) {
            throw new Exception ("Marca ausente");
        }
        if (this.matriz[lin][col] != null) {
            throw new Exception ("Posição ocupada");
        }

        this.matriz[lin][col] = mrc;
    }

    @Override
    public String toString() {

        String ret = "";

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++ ) {
                if (this.matriz[i][j] == null) {
                    ret += " ";
                } else {
                    ret += this.matriz[i][j];
                }

                if (j < 2) {
                    ret += " | ";
                }
            }
            ret += "\n";
            if (i < 2) {
                ret += "--+---+--\n";
            }
        }
        return ret;
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

        Tabuleiro outroTab = (Tabuleiro) obj;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.matriz[i][j] == null && outroTab.matriz[i][j] != null) {
                    return false;
                }
                if (this.matriz[i][j] != null && !this.matriz[i][j].equals(outroTab.matriz[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {

        int ret = 1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ret = ret*2;
                if (this.matriz[i][j] != null) {
                    ret = ret + this.matriz[i][j].hashCode();
                }
            }
        }

        return ret < 0 ? -ret : ret;
    }

    public Tabuleiro(Tabuleiro modelo) throws Exception {
        
        if (modelo == null) {
            throw new Exception ("Modelo ausente");
        }

        this.matriz = new Marca [3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.matriz[i][j] = modelo.matriz[i][j];
            }
        }
    }

    @Override
    public Object clone() {
        
        Tabuleiro ret = null;
        try {
            ret = new Tabuleiro (this);
        } catch (Exception erro)
        {} 
        return ret;
    }
}

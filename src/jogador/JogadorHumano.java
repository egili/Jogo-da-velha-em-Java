package jogador;

import jogo.Marca;
import jogo.Tabuleiro;

public class JogadorHumano implements Jogador, Cloneable {

    private Marca     marca     = null;
    private Tabuleiro tabDoJogo = null;

    public JogadorHumano(Tabuleiro tab) throws Exception {

        if (tab == null) {
            throw new Exception ("Tabuleiro ausente");
        }

        this.tabDoJogo = tab;

        while (this.marca == null) {
            System.out.print("Escolha sua marca (X ou O): ");
            String entrada = Teclado.getUmString();

            if (entrada == null) {
                throw new Exception ("Entrada encerrada");
            }

            try {
                if (entrada.trim().length() != 1) {
                    throw new Exception ("Marca inválida");
                }
                this.marca = new Marca (entrada.trim().charAt(0));
            } catch (Exception erro) {
                System.err.println(erro.getMessage());
            }
        }
    }

    public JogadorHumano(Tabuleiro tab, Marca mrc) throws Exception {
        
        if (tab == null) {
            throw new Exception ("Tabuleiro ausente");
        }
        if (mrc == null) {
            throw new Exception ("Marca ausente");
        }

        this.tabDoJogo = tab;
        this.marca     = mrc; 
    }

    public Marca getMarca() {
        return this.marca;
    }

    public void facaSuaJogada() {

        System.out.println();
        System.out.println(this.tabDoJogo);

        boolean jogou = false;
        while (!jogou) {
            try {
                System.out.print("Sua vez (" + this.marca + ")! Digite a linha (0-2): ");
                String lin = Teclado.getUmString();

                System.out.print("Digite a coluna (0-2): ");
                String col = Teclado.getUmString();

                if (lin == null || col == null) {
                    System.exit(0);
                }

                this.tabDoJogo.setMarcaNaPosicao(this.marca, Integer.parseInt(lin.trim()), Integer.parseInt(col.trim()));
                jogou = true;
            } catch (NumberFormatException erro) {
                System.err.println("Digite um número entre 0 e 2");
            } catch (Exception erro) {
                System.err.println(erro.getMessage());
            }
        }
    }

    public void vcGanhou() {
        System.out.println("Parabéns! Você ganhou!");
    }

    public void vcPerdeu() {
        System.out.println("Que pena! Você perdeu!");
    }

    public void deuVelha() {
        System.out.println("Deu velha! Ninguém ganhou.");
    }

    public boolean querMaisUmaPartida () {
        for (;;) {
            System.out.print("Quer jogar de novo? (S/N): ");

            String resposta = Teclado.getUmString();

            if (resposta == null) {
                return false;
            }

            resposta = resposta.trim().toUpperCase();

            if (resposta.equals("S") || resposta.equals("N")) {
                return resposta.equals("S") ? true : false;
            }

            System.err.println("Resposta inválida");
        }
    }

    @Override
    public String toString() {
        return "Jogador Humano [Marca: " + this.marca + "]";
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

        JogadorHumano outro = (JogadorHumano) obj;
        if (!this.marca.equals(outro.marca)) {
            return false;
        }
        if (!this.tabDoJogo.equals(outro.tabDoJogo)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int ret = 1;

        ret = ret * 2 + this.marca.hashCode();
        ret = ret * 2 + this.tabDoJogo.hashCode();

        return ret < 0 ? -ret : ret;
    }

    public JogadorHumano(JogadorHumano modelo) throws Exception {
        
        if (modelo == null) {
            throw new Exception ("Modelo ausente");
        }

        this.marca     = modelo.marca;
        this.tabDoJogo = modelo.tabDoJogo;
    }

    @Override
    public Object clone() {
        
        JogadorHumano ret = null;
        try {
            ret = new JogadorHumano (this);
        } catch (Exception erro)
        {} 
        return ret;
    }
}

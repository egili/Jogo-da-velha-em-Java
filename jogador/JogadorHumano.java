package jogador;

import jogo.Marca;
import jogo.Tabuleiro;
import java.io.*;

public class JogadorHumano implements Jogador, Cloneable {
    private Marca marca = null;
    private Tabuleiro tabDoJogo = null;
    private BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

    public JogadorHumano(Tabuleiro tab) throws Exception {
        this.tabDoJogo = tab;
        System.out.print("Escolha sua marca (X ou O): ");
        String entrada = teclado.readLine();
        if (entrada == null || entrada.trim().isEmpty()) {
            throw new Exception("Marca não pode ser vazia!");
        }
        this.marca = new Marca(entrada.trim().toUpperCase().charAt(0));
    }

    public JogadorHumano(Tabuleiro tab, Marca mrc) throws Exception {
        this.tabDoJogo = tab;
        this.marca = mrc;
    }

    public Marca getMarca() {
        return this.marca;
    }

    public void facaSuaJogada() {
        boolean jogadaValida = false;
        while (!jogadaValida) {
            try {
                System.out.print("Sua vez (" + this.marca + ")! Digite a linha (0-2): ");
                int linha = Integer.parseInt(teclado.readLine());
                
                System.out.print("Digite a coluna (0-2): ");
                int coluna = Integer.parseInt(teclado.readLine());

                if (this.tabDoJogo.haMarcaNaPosicao(linha, coluna)) {
                    System.out.println("Posição já ocupada! Tente outra.");
                } else {
                    this.tabDoJogo.setMarcaNaPosicao(this.marca, linha, coluna);
                    jogadaValida = true;
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida! Digite números entre 0 e 2.");
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

    public boolean querMaisUmaPartida() {
        try {
            System.out.print("Quer jogar de novo? (S/N): ");
            String resposta = teclado.readLine();
            return resposta != null && resposta.trim().toUpperCase().startsWith("S");
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Jogador Humano [Marca: " + marca + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JogadorHumano outro = (JogadorHumano) obj;
        return this.marca.equals(outro.marca) && this.tabDoJogo.equals(outro.tabDoJogo);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.marca != null ? this.marca.hashCode() : 0);
        return hash;
    }

    public JogadorHumano(JogadorHumano modelo) throws Exception {
        if (modelo == null) throw new Exception("Modelo ausente");
        this.marca = modelo.marca;
        this.tabDoJogo = modelo.tabDoJogo; 
    }

    @Override
    public Object clone() {
        JogadorHumano ret = null;
        try {
            ret = new JogadorHumano(this);
        } catch (Exception erro) {}
        return ret;
    }
}
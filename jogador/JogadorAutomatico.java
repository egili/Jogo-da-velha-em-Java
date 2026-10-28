package jogador;

import jogo.Marca;
import jogo.Tabuleiro;

public class JogadorAutomatico implements Jogador, Cloneable {
    private Marca marca = null;
    private Tabuleiro tabDoJogo = null;

    public JogadorAutomatico(Tabuleiro tab) throws Exception {
        this.tabDoJogo = tab;
        if ((int) (Math.random() * 2) == 0)
            this.marca = new Marca('X');
        else
            this.marca = new Marca('O');
    }

    public JogadorAutomatico(Tabuleiro tab, Marca mrc) throws Exception {
        this.tabDoJogo = tab;
        this.marca = mrc;
    }

    public Marca getMarca() {
        return this.marca;
    }

    public void facaSuaJogada() {
        try {
            Marca oponente = new Marca(this.marca.getSimbolo() == 'X' ? 'O' : 'X');

            // 1. Tentando ganhar
            if (fazerJogadaEstrategica(this.marca, 2)) return;

            // 2. Tentando atrapalhar
            if (fazerJogadaEstrategica(oponente, 2)) return;

            // 3. Tentando jogada de futuro
            if (fazerJogadaEstrategica(this.marca, 1)) return;

            // 4. Termine jogando num lugar qualquer que esteja livre
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (!this.tabDoJogo.haMarcaNaPosicao(i, j)) {
                        this.tabDoJogo.setMarcaNaPosicao(this.marca, i, j);
                        return;
                    }
                }
            }
        } catch (Exception erro) {
            // ignorando pois sei que não estou usando posicao invalida
        }
    }

    // Método auxiliar para varrer linhas, colunas e diagonais sem repetir os IFs
    private boolean fazerJogadaEstrategica(Marca alvo, int qtdNecessaria) throws Exception {
        // Checando linhas
        for (int i = 0; i < 3; i++) {
            if (checarEJogar(alvo, qtdNecessaria, i, 0, i, 1, i, 2)) return true;
        }
        // Checando colunas
        for (int j = 0; j < 3; j++) {
            if (checarEJogar(alvo, qtdNecessaria, 0, j, 1, j, 2, j)) return true;
        }
        // Checando diagonais
        if (checarEJogar(alvo, qtdNecessaria, 0, 0, 1, 1, 2, 2)) return true; // Principal
        if (checarEJogar(alvo, qtdNecessaria, 0, 2, 1, 1, 2, 0)) return true; // Secundária

        return false;
    }

    // Verifica um trio de coordenadas e joga na vazia se o número de marcas alvo bater
    private boolean checarEJogar(Marca alvo, int qtdNecessaria, int l1, int c1, int l2, int c2, int l3, int c3) throws Exception {
        int contAlvo = 0;
        int contVazio = 0;
        int[] posVazia = new int[]{-1, -1};

        int[][] posicoes = {{l1, c1}, {l2, c2}, {l3, c3}};

        for (int[] pos : posicoes) {
            if (!this.tabDoJogo.haMarcaNaPosicao(pos[0], pos[1])) {
                contVazio++;
                posVazia[0] = pos[0];
                posVazia[1] = pos[1];
            } else if (this.tabDoJogo.getMarcaNaPosicao(pos[0], pos[1]).equals(alvo)) {
                contAlvo++;
            }
        }

        // Se encontrou a quantidade certa da marca alvo e tem espaço vazio, faz a jogada
        if (contAlvo == qtdNecessaria && contVazio == 3 - qtdNecessaria) {
            this.tabDoJogo.setMarcaNaPosicao(this.marca, posVazia[0], posVazia[1]);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Jogador Automático [Marca: " + marca + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JogadorAutomatico outro = (JogadorAutomatico) obj;
        return this.marca.equals(outro.marca) && this.tabDoJogo.equals(outro.tabDoJogo);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.marca != null ? this.marca.hashCode() : 0);
        return hash;
    }

    public JogadorAutomatico(JogadorAutomatico modelo) throws Exception {
        if (modelo == null) throw new Exception("Modelo ausente");
        this.marca = modelo.marca;
        this.tabDoJogo = modelo.tabDoJogo;
    }

    @Override
    public Object clone() {
        JogadorAutomatico ret = null;
        try {
            ret = new JogadorAutomatico(this);
        } catch (Exception erro) {}
        return ret;
    }
}
# Jogo da Velha em Java

Jogo da velha (tic-tac-toe) para terminal, escrito em Java puro, sem dependências externas. Você joga contra um jogador automático que segue uma estratégia simples baseada em regras.

## Requisitos

- JDK 8 ou superior (`javac` e `java` no `PATH`)
- Terminal com suporte a entrada padrão (stdin)

## Como compilar e executar

```bash
git clone https://github.com/egili/Jogo-da-velha-em-Java.git
cd Jogo-da-velha-em-Java

# compila tudo para a pasta out/
javac -d out Velha.java jogo/*.java jogador/*.java

# executa
java -cp out Velha
```

Os arquivos `.class` são ignorados pelo Git (`.gitignore`). Se preferir compilar na própria pasta, basta `javac Velha.java` seguido de `java Velha` — o `javac` resolve os pacotes `jogo` e `jogador` automaticamente a partir dos imports.

## Como jogar

1. Ao iniciar, é sorteado quem começa: você ou o computador.
2. Quem começa escolhe a marca. Se for você, digite `X` ou `O` (maiúscula ou minúscula). Se for o computador, ele sorteia a dele e você fica com a outra.
3. Na sua vez, o tabuleiro é exibido e você informa a **linha** e depois a **coluna**, ambas de `0` a `2`:

```
col: 0   1   2
     X |   | O     <- linha 0
     --+---+--
       | X |       <- linha 1
     --+---+--
       |   | O     <- linha 2
```

Exemplo: linha `1`, coluna `1` é o centro.

4. A partida termina quando alguém completa uma linha, coluna ou diagonal, ou quando o tabuleiro enche (deu velha).
5. Ao final, responda `S` para jogar outra partida ou `N` para sair. A cada nova partida, quem começa é alternado.

### Exemplo de sessão

```
Escolha sua marca (X ou O): x

  |   |
--+---+--
  |   |
--+---+--
  |   |

Sua vez (X)! Digite a linha (0-2): 1
Digite a coluna (0-2): 1
...
Parabéns! Você ganhou!
Quer jogar de novo? (S/N): n
```

### Entradas inválidas

| Situação                         | Comportamento                                  |
|----------------------------------|------------------------------------------------|
| Marca diferente de X/O           | Mostra `Marca inválida` e pergunta de novo     |
| Linha/coluna não numérica        | Mostra `Digite um número entre 0 e 2` e repete |
| Linha/coluna fora de 0–2         | Mostra `Posição inválida` e repete             |
| Casa já ocupada                  | Mostra `Posição ocupada` e repete              |
| Fim da entrada (Ctrl+D / EOF)    | Encerra o programa                             |

## Estrutura do projeto

```
.
├── Velha.java                  # ponto de entrada (main) e loop das partidas
├── jogo/                       # modelo e regras
│   ├── Marca.java              # símbolo X ou O (validado e normalizado para maiúscula)
│   ├── Tabuleiro.java          # matriz 3x3 de Marca, com validação de posição
│   └── AnalistaSituacional.java# detecta vencedor e fim de jogo
└── jogador/                    # participantes
    ├── Jogador.java            # interface comum
    ├── JogadorHumano.java      # lê jogadas do teclado
    ├── JogadorAutomatico.java  # jogador controlado pelo computador
    └── Teclado.java            # utilitário de leitura do stdin
```

## Arquitetura

### Fluxo principal (`Velha.java`)

```
sorteia quem começa
repete enquanto o humano quiser jogar:
    cria Tabuleiro
    cria JogadorHumano e JogadorAutomatico (o segundo recebe a marca oposta)
    cria AnalistaSituacional sobre o tabuleiro
    alterna facaSuaJogada() entre os jogadores até analista.fimDeJogo()
    mostra o tabuleiro e o resultado (vcGanhou / vcPerdeu / deuVelha)
    inverte quem começa
```

Todos os objetos compartilham a **mesma instância** de `Tabuleiro`; os jogadores escrevem nela e o analista apenas lê.

### Pacote `jogo`

**`Marca`** — encapsula um `char`. O construtor aceita `x`, `X`, `o` ou `O`, converte para maiúscula e lança `Exception("Marca inválida")` para qualquer outro valor.

**`Tabuleiro`** — matriz `Marca[3][3]`, onde `null` representa casa vazia.

| Método                                   | Descrição                                                    |
|------------------------------------------|--------------------------------------------------------------|
| `haMarcaNaPosicao(lin, col)`             | `true` se a casa está ocupada                                |
| `getMarcaNaPosicao(lin, col)`            | retorna a `Marca` ou `null`                                  |
| `setMarcaNaPosicao(marca, lin, col)`     | marca a casa; erro se posição inválida, marca nula ou ocupada |
| `toString()`                             | desenho do tabuleiro em ASCII                                |

Todos os acessos validam `0 <= lin, col <= 2` e lançam `Exception("Posição inválida")` caso contrário.

**`AnalistaSituacional`** — avalia o estado do tabuleiro:

- `getMarcaDoVencedor()`: verifica as 3 linhas, 3 colunas e 2 diagonais; retorna a `Marca` vencedora ou `null` se não houver.
- `fimDeJogo()`: `true` se há vencedor ou o tabuleiro está cheio.

### Pacote `jogador`

**`Jogador`** (interface)

```java
Marca getMarca();
void facaSuaJogada();
```

**`JogadorHumano`** — lê a marca (quando é o primeiro a jogar) e as jogadas via `Teclado`, repetindo a pergunta até receber uma posição válida. Também expõe as mensagens de resultado e `querMaisUmaPartida()`.

**`JogadorAutomatico`** — escolhe a jogada por prioridade:

1. **Vencer**: se existe uma linha/coluna/diagonal com 2 marcas próprias e 1 casa vazia, completa.
2. **Bloquear**: se o oponente tem 2 marcas e 1 casa vazia numa linha, bloqueia.
3. **Avançar**: se há uma linha com 1 marca própria e 2 casas vazias, joga nela.
4. **Fallback**: ocupa a primeira casa vazia, varrendo da posição (0,0) em diante.

A verificação é feita por `chequeEJogue`, que recebe as três coordenadas de uma linha e a quantidade de marcas exigida. É uma heurística gulosa, não um minimax, então o computador pode perder.

**`Teclado`** — wrapper estático sobre um `BufferedReader(System.in)` com métodos `getUmString`, `getUmInt`, `getUmChar` etc. O jogo usa apenas `getUmString`, que retorna `null` em EOF.

### Convenções do código

- Erros de domínio são sinalizados com `java.lang.Exception` e mensagens em português.
- `Marca`, `Tabuleiro`, `AnalistaSituacional` e os jogadores sobrescrevem `equals`, `hashCode` e `toString`.
- `Tabuleiro`, `JogadorHumano` e `JogadorAutomatico` implementam `Cloneable` por meio de um construtor de cópia. A cópia de `Tabuleiro` é rasa nas marcas (o que é seguro, já que `Marca` é imutável); a cópia dos jogadores compartilha o mesmo tabuleiro.

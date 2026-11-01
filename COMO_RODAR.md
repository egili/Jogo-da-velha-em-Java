# Como rodar o Jogo da Velha

## Requisito

JDK 8 ou mais novo. Confira com:

```bash
javac -version
```

## Compilar e executar

No terminal, dentro da pasta do projeto:

```bash
cd Jogo-da-velha-em-Java
javac -encoding UTF-8 Velha.java jogo/*.java jogador/*.java
java Velha
```

- O `javac` gera os `.class` ao lado de cada `.java`. O `-encoding UTF-8` é para os acentos das mensagens saírem certos.
- O `java Velha` precisa ser rodado da pasta raiz do projeto, onde fica o `Velha.java`. Assim o Java encontra os pacotes `jogo/` e `jogador/`.

### Opção: separar os `.class` do código-fonte

```bash
javac -encoding UTF-8 -d bin Velha.java jogo/*.java jogador/*.java
java -cp bin Velha
```

## Como jogar

1. O jogo sorteia quem começa. Se for você, ele pede a sua marca (X ou O).
2. Ele mostra o tabuleiro e pede a linha e depois a coluna, de 0 a 2.
3. No fim, mostra o resultado e pergunta se você quer jogar de novo (S/N).

> Se a pasta for versionada, não faça commit dos `.class`.

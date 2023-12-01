package ArvAVL;

public class No {
    ContaBanco conta;
    No esquerda, direita;
    int altura;

    // Construtor
    public No(ContaBanco conta) {
        this.conta = conta;
        this.esquerda = this.direita = null;
        this.altura = 0;
    }
}

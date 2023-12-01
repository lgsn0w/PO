package ArvAVL;
import java.io.BufferedWriter;
import java.io.IOException;

// Classe que representa uma árvore AVL de contas bancárias
public class ArvoreAVL {
    // Nó raiz da árvore
    private No raiz;

    // Construtor da árvore AVL
    public ArvoreAVL() {
        this.raiz = null;
    }

    // Método para inserir uma conta na árvore AVL
    public void inserir(ContaBanco conta) {
        this.raiz = inserirRec(this.raiz, conta);
    }

    // Método auxiliar para realizar a inserção recursiva em um nó da árvore
    private No inserirRec(No no, ContaBanco conta) {
        // Se o nó é nulo, cria um novo nó com a conta
        if (no == null) {
            return new No(conta);
        }

        // Compara as contas para determinar em qual subárvore inserir
        int comparacao = conta.compareTo(no.conta);

        // Realiza a inserção na subárvore apropriada
        if (comparacao < 0) {
            no.esquerda = inserirRec(no.esquerda, conta);
        } else if (comparacao > 0) {
            no.direita = inserirRec(no.direita, conta);
        } else {
            // Conta com o mesmo CPF, decide o que fazer (por exemplo, não inserir duplicatas).
            return no;
        }

        // Atualiza a altura do nó atual
        atualizarAltura(no);

        // Realiza o balanceamento após a inserção
        return balancear(no);
    }

    // Método para procurar uma conta por CPF e gerar o resultado
    public void buscarPorCPF(long cpf) {
        buscarPorCPFRec(this.raiz, cpf);
    }

    // Método auxiliar para realizar a busca por CPF de forma recursiva
    private void buscarPorCPFRec(No no, long cpf) {
        if (no == null) {
            // CPF não encontrado
            System.out.println("CPF " + cpf + ":\nINEXISTENTE\n");
        } else {
            int comparacao = Long.compare(cpf, no.conta.cpf);

            if (comparacao < 0) {
                buscarPorCPFRec(no.esquerda, cpf);
            } else if (comparacao > 0) {
                buscarPorCPFRec(no.direita, cpf);
            } else {
                // CPF encontrado, gerar o resultado
                System.out.println("CPF " + cpf + ":");
                gerarResultado(no.conta);
                System.out.println();
            }
        }
    }

    // Método para gerar o resultado para uma conta específica
    private void gerarResultado(ContaBanco conta) {
        // Itera sobre as contas com o mesmo CPF
        No noAtual = this.raiz;
        while (noAtual != null && noAtual.conta.cpf == conta.cpf) {
            if (noAtual.conta.equals(conta)) {
                System.out.printf("Agencia %d Conta %d Saldo: %.2f\n",
                        noAtual.conta.agencia, noAtual.conta.numero, noAtual.conta.balanco);
            }
            noAtual = noAtual.direita;
        }

        // Calcula e imprime o saldo total
        double saldoTotal = calcularSaldoTotal(conta.cpf);
        System.out.printf("Saldo total: %.2f\n", saldoTotal);
    }

    // Método para calcular o saldo total para um CPF
    private double calcularSaldoTotal(long cpf) {
        double saldoTotal = 0.0;
        No noAtual = this.raiz;

        while (noAtual != null) {
            if (noAtual.conta.cpf == cpf) {
                saldoTotal += noAtual.conta.balanco;
            }

            if (cpf < noAtual.conta.cpf) {
                noAtual = noAtual.esquerda;
            } else if (cpf > noAtual.conta.cpf) {
                noAtual = noAtual.direita;
            } else {
                noAtual = noAtual.direita;
            }
        }

        return saldoTotal;
    }

    // Método para obter a altura de um nó (retorna -1 se o nó for nulo)
    private int altura(No no) {
        return (no == null) ? -1 : no.altura;
    }

    // Método para atualizar a altura de um nó
    private void atualizarAltura(No no) {
        if (no != null) {
            no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        }
    }

    // Método para realizar a rotação simples à direita
    private No rotacaoDireita(No y) {
        No x = y.esquerda;
        No T2 = x.direita;

        // Realiza a rotação
        x.direita = y;
        y.esquerda = T2;

        // Atualiza as alturas
        atualizarAltura(y);
        atualizarAltura(x);

        return x;
    }

    // Método para realizar a rotação simples à esquerda
    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        No T2 = y.esquerda;

        // Realiza a rotação
        y.esquerda = x;
        x.direita = T2;

        // Atualiza as alturas
        atualizarAltura(x);
        atualizarAltura(y);

        return y;
    }

    // Método para realizar a rotação dupla direita-esquerda
    private No rotacaoDuplaDireitaEsquerda(No z) {
        z.direita = rotacaoDireita(z.direita);
        return rotacaoEsquerda(z);
    }

    // Método para realizar a rotação dupla esquerda-direita
    private No rotacaoDuplaEsquerdaDireita(No z) {
        z.esquerda = rotacaoEsquerda(z.esquerda);
        return rotacaoDireita(z);
    }

    // Método para obter o fator de balanceamento de um nó
    private int fatorBalanceamento(No no) {
        return altura(no.esquerda) - altura(no.direita);
    }

    // Método para realizar o balanceamento de um nó
    private No balancear(No no) {
        // Atualiza a altura do nó
        atualizarAltura(no);

        // Obtém o fator de balanceamento do nó
        int balanceamento = fatorBalanceamento(no);

        // Casos de desequilíbrio e possíveis rotações
        if (balanceamento > 1) {
            if (fatorBalanceamento(no.esquerda) >= 0) {
                // Rotação simples à direita
                return rotacaoDireita(no);
            } else {
                // Rotação dupla esquerda-direita
                return rotacaoDuplaEsquerdaDireita(no);
            }
        } else if (balanceamento < -1) {
            if (fatorBalanceamento(no.direita) <= 0) {
                // Rotação simples à esquerda
                return rotacaoEsquerda(no);
            } else {
                // Rotação dupla direita-esquerda
                return rotacaoDuplaDireitaEsquerda(no);
            }
        }

        // Sem necessidade de balanceamento
        return no;
    }
}

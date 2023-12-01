import java.io.BufferedWriter;
import java.io.IOException;

// Árvore Binária de Busca (ABB) para armazenar contas bancárias
public class ABB {
    // Nó raiz da árvore
    private No raiz;

    // Construtor da Árvore Binária de Busca
    public ABB() {
        this.raiz = null;
    }

    // Método para inserir uma conta na árvore
    public void inserir(ContaBanco conta) {
        this.raiz = inserirRec(this.raiz, conta);
    }

    // Função recursiva para inserir uma conta na árvore
    private No inserirRec(No no, ContaBanco conta) {
        // Se o nó é nulo, cria um novo nó com a conta
        if (no == null) {
            return new No(conta);
        }

        // Compara as contas para decidir em qual subárvore inserir
        int comparacao = conta.compareTo(no.conta);

        if (comparacao < 0) {
            no.esquerda = inserirRec(no.esquerda, conta);
        } else if (comparacao > 0) {
            no.direita = inserirRec(no.direita, conta);
        } else {
            // Conta com o mesmo CPF, decisão de tratamento (por exemplo, não inserir duplicatas).
            return no;
        }

        return no;
    }

    // Método para buscar por CPF e gerar o resultado
    public void buscarPorCPF(long cpf, BufferedWriter writer) throws IOException {
        buscarPorCPFRec(this.raiz, cpf, writer);
    }

    // Função recursiva para buscar por CPF e gerar o resultado
    private double buscarPorCPFRec(No no, long cpf, BufferedWriter writer) throws IOException {
        // Se o nó é nulo, o CPF não foi encontrado, grava no arquivo
        if (no == null) {
            writer.write("CPF " + cpf + ":\nINEXISTENTE\n\n");
            return 0.0;
        } else {
            // Compara o CPF da conta no nó com o CPF buscado
            int comparacao = Long.compare(cpf, no.conta.cpf);

            if (comparacao < 0) {
                // Busca na subárvore esquerda
                return buscarPorCPFRec(no.esquerda, cpf, writer);
            } else if (comparacao > 0) {
                // Busca na subárvore direita
                return buscarPorCPFRec(no.direita, cpf, writer);
            } else {
                // CPF encontrado, gera o resultado
                writer.write("CPF " + cpf + ":\n");
                double saldoTotal = gerarResultado(no, writer);
                writer.write(String.format("Saldo total: %.2f\n\n", saldoTotal));
                return saldoTotal;
            }
        }
    }

    // Função para gerar o resultado para contas associadas a um CPF
    private double gerarResultado(No no, BufferedWriter writer) throws IOException {
        return gerarResultadoRec(no, writer);
    }

    // Função recursiva para gerar o resultado para contas associadas a um CPF
    private double gerarResultadoRec(No no, BufferedWriter writer) throws IOException {
        // Se o nó é nulo, retorna saldo zero
        if (no == null) {
            return 0.0;
        }

        // Recursivamente busca e escreve os saldos da subárvore esquerda e direita
        double saldoEsquerda = gerarResultadoRec(no.esquerda, writer);
        double saldoDireita = gerarResultadoRec(no.direita, writer);

        // Obtém o saldo da conta no nó
        double saldoConta = no.conta.balanco;

        // Escreve informações da conta no arquivo
        writer.write(String.format("Agencia %d Conta %d Saldo: %.2f\n",
                no.conta.agencia, no.conta.numero, saldoConta));

        // Retorna o saldo total da subárvore
        return saldoEsquerda + saldoDireita + saldoConta;
    }

    // Classe interna para representar um nó na árvore
    private static class No {
        ContaBanco conta;
        No esquerda, direita;

        // Construtor do nó
        public No(ContaBanco conta) {
            this.conta = conta;
            this.esquerda = this.direita = null;
        }
    }
}

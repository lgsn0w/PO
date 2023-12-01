package HashTb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class TabelaHash {
    private static final int TAMANHO_TABELA = 1000;  // Tamanho da tabela de hash
    private LinkedList<ContaBanco>[] tabela;  // Array de listas vinculadas

    // Construtor
    public TabelaHash() {
        // Inicializar a tabela de hash como um array de listas vinculadas
        this.tabela = new LinkedList[TAMANHO_TABELA];
        for (int i = 0; i < TAMANHO_TABELA; i++) {
            this.tabela[i] = new LinkedList<>();
        }
    }

    // Método para inserir uma conta na tabela de hash
    public void inserir(ContaBanco conta) {
        int indice = calcularIndice(conta.cpf);
        tabela[indice].add(conta);
    }

    // Método para procurar uma conta por CPF e gerar o resultado
    public void buscarPorCPF(long cpf, BufferedWriter writer) throws IOException {
        int indice = calcularIndice(cpf);

        LinkedList<ContaBanco> contasEncontradas = new LinkedList<>();

        // Buscar contas associadas ao CPF
        for (ContaBanco conta : tabela[indice]) {
            if (conta.cpf == cpf) {
                contasEncontradas.add(conta);
            }
        }

        if (!contasEncontradas.isEmpty()) {
            // CPF encontrado, gerar o resultado
            writer.write("CPF " + cpf + ":\n");
            gerarResultado(contasEncontradas, writer);
            writer.write("\n");
        } else {
            // CPF não encontrado, gravar no arquivo
            writer.write("CPF " + cpf + ":\nINEXISTENTE\n\n");
        }
    }

    // Método para calcular o índice na tabela de hash
    private int calcularIndice(long cpf) {
        return (int) (cpf % TAMANHO_TABELA);
    }

    // Método para gerar o resultado para uma conta específica
    private void gerarResultado(LinkedList<ContaBanco> contas, BufferedWriter writer) throws IOException {
        for (ContaBanco conta : contas) {
            writer.write(String.format("Agencia %d Conta %d Saldo: %.2f\n",
                    conta.agencia, conta.numero, conta.balanco));
        }

        // Calcular e imprimir o saldo total
        double saldoTotal = contas.stream().mapToDouble(conta -> conta.balanco).sum();
        writer.write(String.format("Saldo total: %.2f\n", saldoTotal));
    }

    public static void main(String[] args) {
        // Definir os caminhos dos arquivos de entrada e saída
        String cpfFilePath = "cpfs.txt";
        String contasFilePath = "contas.txt";
        String outputFilePath = "resultado_hashing.txt";

        // Criar uma instância da classe TabelaHash
        TabelaHash tabelaHash = new TabelaHash();

        // Ler contas bancárias do arquivo e inserir na tabela de hash
        ContaBanco[] contas = lerContasBancarias(contasFilePath);
        for (ContaBanco conta : contas) {
            tabelaHash.inserir(conta);
        }

        // Realizar buscas por CPFs e gerar resultados
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            lerCPFsEProcessar(tabelaHash, cpfFilePath, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para ler contas bancárias a partir de um arquivo
    private static ContaBanco[] lerContasBancarias(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(line -> {
                        String[] partes = line.split(";");
                        int agencia = Integer.parseInt(partes[0]);
                        int numero = Integer.parseInt(partes[1]);
                        double saldo = Double.parseDouble(partes[2]);
                        long cpf = Long.parseLong(partes[3]);
                        return new ContaBanco(agencia, numero, saldo, cpf);
                    })
                    .toArray(ContaBanco[]::new);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para ler CPFs a partir de um arquivo, buscar na tabela de hash e escrever resultados
    private static void lerCPFsEProcessar(TabelaHash tabelaHash, String cpfFilePath, BufferedWriter writer) {
        try (BufferedReader reader = new BufferedReader(new FileReader(cpfFilePath))) {
            reader.lines()
                    .map(Long::parseLong)
                    .forEach(cpf -> {
                        try {
                            tabelaHash.buscarPorCPF(cpf, writer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

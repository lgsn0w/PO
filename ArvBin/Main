package ArvoreBinaria;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Caminhos dos arquivos de entrada e saída
        String cpfFilePath = "cpf.txt";
        String contasFilePath = "conta500.txt";
        String outputFilePath = "resultado.txt";

        // Lê as contas bancárias do arquivo e constrói a árvore binária de busca (ABB)
        ContaBanco[] contas = lerContasBancarias(contasFilePath);
        ABB arvore = construirArvore(contas);

        // Se a árvore foi construída com sucesso, processa os CPFs e escreve os resultados no arquivo de saída
        if (arvore != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                lerCPFsEProcessar(arvore, cpfFilePath, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Lê as contas bancárias do arquivo e retorna um array de ContaBanco
    private static ContaBanco[] lerContasBancarias(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(line -> {
                        // Divide a linha em partes e cria um objeto ContaBanco
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

    // Constrói a árvore binária de busca (ABB) a partir do array de ContaBanco
    private static ABB construirArvore(ContaBanco[] contas) {
        ABB arvore = new ABB();
        for (ContaBanco conta : contas) {
            arvore.inserir(conta);
        }
        return arvore;
    }

    // Lê os CPFs do arquivo, busca na árvore e escreve os resultados no arquivo de saída
    private static void lerCPFsEProcessar(ABB arvore, String cpfFilePath, BufferedWriter writer) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(cpfFilePath))) {
            reader.lines()
                    .map(Long::parseLong)
                    .forEach(cpf -> {
                        try {
                            // Busca o CPF na árvore e escreve no arquivo de saída
                            arvore.buscarPorCPF(cpf, writer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}

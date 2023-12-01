package HashTb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        // Caminhos dos arquivos de entrada e saída
        String cpfFilePath = "cpf.txt";
        String contasFilePath = "conta500.txt";
        String outputFilePath = "resultado_hashing.txt";

        // Criação de uma tabela de hash
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
            // Mapeia cada linha do arquivo para um objeto ContaBanco e retorna um array
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

    // Método para ler CPFs de um arquivo, buscar na tabela de hash e gerar resultados
    private static void lerCPFsEProcessar(TabelaHash tabelaHash, String cpfFilePath, BufferedWriter writer) {
        try (BufferedReader reader = new BufferedReader(new FileReader(cpfFilePath))) {
            // Para cada CPF lido, busca na tabela de hash e escreve o resultado no arquivo de saída
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

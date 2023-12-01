package ArvAVL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Definir os caminhos dos arquivos de entrada e saída
        String cpfFilePath = "cpf.txt";
        String contasFilePath = "conta500.txt";
        String outputFilePath = "resultado_avl.txt";

        // Criar uma instância da classe ArvoreAVL para armazenar as contas bancárias
        ArvoreAVL arvore = new ArvoreAVL();

        // Ler contas bancárias do arquivo e inserir na árvore AVL
        ContaBanco[] contas = lerContasBancarias(contasFilePath);
        for (ContaBanco conta : contas) {
            arvore.inserir(conta);
        }

        // Realizar buscas por CPFs e gerar resultados
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            lerCPFsEProcessar(arvore, cpfFilePath, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para ler contas bancárias a partir de um arquivo
    private static ContaBanco[] lerContasBancarias(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Mapear cada linha do arquivo para uma instância de ContaBanco
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

    // Método para ler CPFs a partir de um arquivo, buscar na árvore AVL e escrever resultados
    private static void lerCPFsEProcessar(ArvoreAVL arvore, String cpfFilePath, BufferedWriter writer) {
        try (BufferedReader reader = new BufferedReader(new FileReader(cpfFilePath))) {
            reader.lines()
                    .map(Long::parseLong)
                    .forEach(cpf -> {
                        // Realizar busca na árvore AVL e escrever o resultado no arquivo de saída
                        arvore.buscarPorCPF(cpf);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

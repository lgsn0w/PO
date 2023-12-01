import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class QuickSortContas {
    public static void main(String[] args) {
        // Caminho do arquivo contendo informações das contas
        String filePath = "conta500.txt";
        
        // Ler informações das contas do arquivo
        ContaBanco[] contas = lerContasBancarias(filePath);

        // Verificar se as contas foram lidas com sucesso
        if (contas != null) {
            // Realizar QuickSort no array de contas
            quickSort(contas, 0, contas.length - 1);

            // Imprimir contas ordenadas
            for (ContaBanco conta : contas) {
                System.out.println(conta);
            }
        }
    }

    // Ler informações das contas de um arquivo e criar um array de objetos ContaBanco
    private static ContaBanco[] lerContasBancarias(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(line -> {
                        // Dividir a linha em partes e fazer o parsing dos valores para criar um objeto ContaBanco
                        String[] partes = line.split(";");
                        int agencia = Integer.parseInt(partes[0]);
                        int numero = Integer.parseInt(partes[1]);
                        double saldo = Double.parseDouble(partes[2]);
                        long cpf = Long.parseLong(partes[3]);
                        return new ContaBanco(agencia, numero, saldo, cpf);
                    })
                    .toArray(ContaBanco[]::new);
        } catch (IOException e) {
            // Tratar IOException imprimindo a stack trace
            e.printStackTrace();
            return null;
        }
    }

    // Executar o algoritmo QuickSort em um array de objetos ContaBanco
    private static void quickSort(ContaBanco[] array, int baixo, int alto) {
        // Ordenar recursivamente o array até atingir o caso base
        if (baixo < alto) {
            int indicePivo = particionar(array, baixo, alto);
            quickSort(array, baixo, indicePivo - 1);
            quickSort(array, indicePivo + 1, alto);
        }
    }

    // Particionar o array para o QuickSort e retornar o índice do elemento pivô
    private static int particionar(ContaBanco[] array, int baixo, int alto) {
        // Escolher o último elemento como pivô
        ContaBanco pivo = array[alto];
        int i = baixo - 1;

        // Iterar pelo array e reorganizar os elementos com base na comparação com o pivô
        for (int j = baixo; j < alto; j++) {
            int comparacao = array[j].compareTo(pivo);

            // Se os CPFs forem iguais, comparar por agência e número
            if (comparacao == 0) {
                comparacao = Long.compare(array[j].cpf, pivo.cpf);
            }

            if (comparacao < 0) {
                // Incrementar o índice e trocar os elementos
                i++;
                trocar(array, i, j);
            }
        }

        // Trocar o pivô para a sua posição correta
        trocar(array, i + 1, alto);
        return i + 1;
    }

    // Trocar dois elementos no array
    private static void trocar(ContaBanco[] array, int i, int j) {
        ContaBanco temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ShellSortContas {
    public static void main(String[] args) {
        // Caminho do arquivo contendo informações de contas bancárias
        String filePath = "conta50000.txt";
        
        // Lê as contas bancárias do arquivo
        ContaBanco[] contas = lerContasBancarias(filePath);

        // Verifica se as contas foram lidas com sucesso
        if (contas != null) {
            // Aplica o algoritmo Shell Sort para ordenar as contas
            shellsort(contas);

            // Imprime as contas ordenadas
            for (ContaBanco conta : contas) {
                System.out.println(conta);
            }
        }
    }

    // Método para ler as contas bancárias de um arquivo e retornar um array de objetos ContaBanco
    private static ContaBanco[] lerContasBancarias(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(line -> {
                        // Divide a linha em partes usando o ponto e vírgula como delimitador
                        String[] partes = line.split(";");
                        
                        // Converte as partes para os tipos apropriados e cria um objeto ContaBanco
                        int agencia = Integer.parseInt(partes[0]);
                        int numero = Integer.parseInt(partes[1]);
                        double saldo = Double.parseDouble(partes[2]);
                        long cpf = Long.parseLong(partes[3]);
                        return new ContaBanco(agencia, numero, saldo, cpf);
                    })
                    .toArray(ContaBanco[]::new);
        } catch (IOException e) {
            // Imprime detalhes sobre exceções de leitura do arquivo
            e.printStackTrace();
            
            // Retorna null se houver uma exceção
            return null;
        }
    }

    // Método que implementa o algoritmo Shell Sort para ordenar um array de contas bancárias
    private static void shellsort(ContaBanco[] array) {
        int n = array.length;

        // Começa com um grande intervalo e, em seguida, reduz o intervalo gradualmente
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // Realiza uma ordenação por inserção com o intervalo atual
            for (int i = gap; i < n; i++) {
                // Salva o valor atual em 'temp' e cria um "buraco" na posição i
                ContaBanco temp = array[i];
                
                // Desloca os elementos ordenados com intervalo para encontrar a posição correta para 'temp'
                int j;
                for (j = i; j >= gap && array[j - gap].compareTo(temp) > 0; j -= gap) {
                    array[j] = array[j - gap];
                }
                
                // Coloca 'temp' na sua posição correta
                array[j] = temp;
            }
        }
    }
}

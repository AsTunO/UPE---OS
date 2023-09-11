import java.util.ArrayList;
import java.util.Arrays;

public class ProcessesManager extends Thread {

    String commands;
    Buffer memories;
    int threadNumber;

    public ProcessesManager(String commands, Buffer memories, int threadNumber) {
        this.commands = commands;
        this.memories = memories;
        this.threadNumber = threadNumber;
    }

    public void run() {
        String commandsWithoutBrackets = commands.substring(1, commands.length() - 1); // Remova os caracteres "{" e "}" da string de comandos
        String[] commandArray = commandsWithoutBrackets.split(", "); // Separe os comandos usando a vírgula e espaço como delimitadores
        ArrayList<String> commandQueue = new ArrayList<>(Arrays.asList(commandArray)); // Crie uma lista para armazenar os comandos

        System.out.println("Processo de Nº " + threadNumber + " Lista de comandos -> " + commandQueue);

        for (int index = 0; index < commandQueue.size(); index++) {
            String command = commandQueue.get(index);
            if (command.contains("W")) { // Lida com comandos de escrita
                String[] parts = command.split("-");
                if (parts.length == 3) { // Certifica-se de que há três partes na divisão
                    String posicao = parts[0];
                    String valor = parts[2];

                    System.out.println("Processo de Nº " + threadNumber + " Comando Nº " + (index + 1) + " Escrita ->" + " [Posicao: " + posicao + " Valor: " + valor + " ]");
                }
            }else { // Lida com comandos de leitura
                String[] parts = command.split("-");
                if (parts.length == 2) { // Certifica-se de que há duas partes na divisão
                    String posicao = parts[0];

                    System.out.println("Processo de Nº " + threadNumber + " Comando Nº " + (index + 1) + " Leitura ->" + " [Posicao: " + posicao + " ]");
                }
            }
        }
        System.out.println("Processo Nº " + threadNumber + " não possui mais comando a serem executados");
    }
}
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // cria as listas com os comandos
        List<String> commands1 = new ArrayList<String>();
        List<String> commands2 = new ArrayList<String>();
        commands1.add("2-w-8");
        commands1.add("1-w-17");
        commands1.add("5-w-49");
        commands1.add("14-w-56");
        commands1.add("10-w-1");
        commands1.add("0-w-5");
        commands1.add("9-w-12");
        commands2.add("6-w-69");
        commands2.add("3-w-45");
        commands2.add("2-w-4");
        commands2.add("8-w-9");
        commands2.add("9-w-2");
        commands1.add("0-r");
        commands2.add("0-w-7");
        commands2.add("1-r");

        // inicia as duas threads que irão simular os programas, cada uma com sua lista de comandos
        Program program1 = new Program(commands1);
        Program program2 = new Program(commands2);

        program1.start();
        program2.start();

        // espera as threads terminarem
        try {
            program1.join();
            program2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        printMemories();
    }

    private static void printMemories() {
        System.out.println("VIRTUAL MEMORY");
        for (int i = Memories.VIRTUAL_MEMORY_SIZE - 1; i >= 0 ; i--) {
            try {
                System.out.print(i + "| " + Memories.virtualMemory[i].getPageFrame());
                if (Memories.virtualMemory[i].getPresentBit() == true) {
                    System.out.println(" | physical memory");
                } else {
                    System.out.println(" | swap area");
                }
            } catch (Exception e) {
                System.out.println(i + "| X");
            }
        }

        System.out.println("PHYSICAL MEMORY");
        for (int i = Memories.PHYSICAL_MEMORY_SIZE - 1; i >= 0 ; i--) {
            System.out.println(i + "| " + Memories.physicalMemory[i]);
        }

        System.out.println("SWAP AREA");
        for (int i = Memories.PHYSICAL_MEMORY_SIZE - 1; i >= 0 ; i--) {
            System.out.println(i + "| " + Memories.swapArea[i]);
        }
    }
}

/*      - DONE: quando a memória física estiver cheia, fazer a troca da memória com a swap
 *      - DONE: fazer a substituição de páginas usando o algoritmo de segunda chance
 *      - DONE: criar as threads para simular a execução de processos
 *      - TODO: função para imprimir a memória física e virtual e a swap
 *      - DONE: separar código adequadamente em funções */
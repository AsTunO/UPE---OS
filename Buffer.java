import java.util.Random;

public class Buffer {

    final int VIRTUALMEMORYSIZE = 16;
    final int RANDOMACCESSMEMORYSIZE = 8;
    final int SWAPSIZE = 8;

    int[] virtualMemory;
    int[] physicalMemory;
    int[] SWAPMemory;
    int swapPointer; // Ponteiro para o SWAP para usar o algoritmo FIFO

    public Buffer() {
        virtualMemory = new int[VIRTUALMEMORYSIZE];
        physicalMemory = new int[RANDOMACCESSMEMORYSIZE];
        SWAPMemory = new int[SWAPSIZE];
        swapPointer = 0; // Inicializa o ponteiro do SWAP

        populateMemory(SWAPMemory);
    }

    public static void populateMemory(int[] currentMemory) {
        Random random = new Random();
        for (int i = 0; i < currentMemory.length; i++) {
            currentMemory[i] = random.nextInt(100);
        }
    }

    public synchronized void write(String position, String value, int threadNumber, int index) {
        int positionInt = Integer.parseInt(position);
        int valueInt = Integer.parseInt(value);

        System.out.println("Processo de Nº " + threadNumber + " Comando Nº " + (index + 1) + " Escrita ->" + " [Posicao: " + positionInt + " Valor: " + valueInt + " ]");

        // Verifique se a posição está na memória física
        if (positionInt < physicalMemory.length) {
            if (physicalMemory[positionInt] == 0) {
                physicalMemory[positionInt] = valueInt;
                System.out.println("Escreveu o valor " + valueInt + " na memória física na posição " + positionInt);
            } else {
                System.out.println("A posição na memória física já está ocupada. Tentando no SWAP...");
                writeInSWAP(positionInt, valueInt);
            }
        } else {
            System.out.println("Posição inválida na memória física. Tentando no SWAP...");
            writeInSWAP(positionInt, valueInt);
        }
    }

    private void writeInSWAP(int position, int value) {
        // Verifique se há espaço no SWAP
        boolean swapFull = true;
        for (int i = 0; i < SWAPMemory.length; i++) {
            if (SWAPMemory[i] == 0) {
                SWAPMemory[i] = value;
                System.out.println("Escreveu o valor " + value + " do SWAP na posição " + i);
                swapFull = false;
                break;
            }
        }

        if (swapFull) {
            System.out.println("O SWAP está cheio. Memória cheia.");
        }
    }

    public synchronized void read(String position, int threadNumber, int index) {
        int positionInt = Integer.parseInt(position);

        System.out.println("Processo de Nº " + threadNumber + " Comando Nº " + (index + 1) + " Leitura ->" + " [Posicao: " + position + " ]");

        int isInVirtualMemory = -1;
        System.out.println("Buscando na memória virtual ...");
        for (int i = 0; i < virtualMemory.length; i++) {
            if (virtualMemory[i] == positionInt) {
                isInVirtualMemory = i;
                break; // Encontrou na memória virtual, saia do loop
            }
        }

        if (isInVirtualMemory != -1) {
            System.out.println("Leu o valor : " + virtualMemory[isInVirtualMemory] + " da memoria virtual");
        } else {
            System.out.println("Buscando na memória fisica ...");
            if (positionInt < physicalMemory.length && physicalMemory[positionInt] != 0) {
                System.out.println("Leu o valor :" + physicalMemory[positionInt] + " da memória fisica");
            } else {
                System.out.println("Pegando o valor do SWAP e salvando na memoria fisica e virtual");
                int valueToSwap = SWAPMemory[swapPointer]; // Obtenha o valor do SWAP usando o algoritmo FIFO
                int positionToSwap = positionInt; // Use a posição como índice
                physicalMemory[positionToSwap] = valueToSwap;
                virtualMemory[positionToSwap] = positionInt; // Salve o endereço na memória virtual
                System.out.println("Leu o valor :" + valueToSwap + " da memória fisica");
                swapPointer = (swapPointer + 1) % SWAPSIZE; // Atualiza o ponteiro do SWAP usando FIFO
                reorganizeSWAP(); // Reorganize o SWAP após a remoção de um elemento
            }
        }
    }

    private void reorganizeSWAP() {
        int[] tempSWAP = new int[SWAPSIZE];

        for (int i = 0; i < SWAPSIZE - 1; i++) {
            tempSWAP[i] = SWAPMemory[(swapPointer + i + 1) % SWAPSIZE];
        }

        tempSWAP[SWAPSIZE - 1] = 0; // Define o último elemento como 0

        for (int i = 0; i < SWAPSIZE; i++) {
            SWAPMemory[i] = tempSWAP[i];
        }
    }

    public void showStateOfMemory() {
        System.out.println("Virtual Memory:");
        for (int i = 0; i < virtualMemory.length; i++) {
            System.out.println("Posição [" + i + "] = " + virtualMemory[i]);
        }

        System.out.println("\nPhysical Memory:");
        for (int i = 0; i < physicalMemory.length; i++) {
            System.out.println("Posição [" + i + "] = " + physicalMemory[i]);
        }

        System.out.println("\nSWAP Memory:");
        for (int i = 0; i < SWAPMemory.length; i++) {
            System.out.println("Posição [" + i + "] = " + SWAPMemory[i]);
        }
    }
}

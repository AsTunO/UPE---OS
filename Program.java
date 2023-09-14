import java.util.List;

public class Program extends Thread {
    List<String> commands;
    private static final Object monitor = new Object();

    public Program(List<String> commands) {
        this.commands = commands;
    }
    
    public void run() {
        for (String cmd : commands) {
            executeCommand(cmd);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // interpreta se o comando é para escrita ou leitura e chama a função correspondente
    public void executeCommand(String cmd) {
        String[] command = cmd.split("-");
        int pos = Integer.parseInt(command[0]);
        if (command[1].equals("w")) {
            Integer val = Integer.parseInt(command[2]);
            synchronized (monitor) {
                writeCommand(pos, val);
            }
        } else if (command[1].equals("r")) {
            int x;
            synchronized (monitor) {
                x = readCommand(pos);
            }
            if (x == -1) {
                System.out.println("A posição " + pos + " não existe\n");
            } else {
                System.out.println("Valor da posição " + pos + ": " + x + "\n");
            }
        }
    }

    // função para escrever na memória
    public synchronized void writeCommand(int pos, int val) {
        // quando a posição da memória virtual já tem uma página alocada
        if (Memories.virtualMemory[pos] != null && Memories.virtualMemory[pos].getPresentBit() == true) {
            Integer pag = Memories.virtualMemory[pos].getPageFrame();
            Memories.virtualMemory[pos].setReferencedBit(true);
            Memories.physicalMemory[pag] = val;
            System.out.println("A posição " + pos + " já está alocada na memória física na posição " + pag);
            System.out.println("Foi escrito o valor " + val + " na posição " + pag + " da memória física\n");
            return;
        }

        // quando a posição da memória virtual está apontando para a swap
        if (Memories.virtualMemory[pos] != null && Memories.virtualMemory[pos].getPresentBit() == false) {
            System.out.println("A posição " + pos + " está apontando para a área de troca");
            for (int i = 0; i < Memories.VIRTUAL_MEMORY_SIZE; i++) {
                if (Memories.virtualMemory[i] != null && Memories.virtualMemory[i].getReferencedBit() == true && Memories.virtualMemory[i].getPresentBit() == true) {
                    Memories.virtualMemory[i].setReferencedBit(false);
                }
                if (Memories.virtualMemory[i] != null && Memories.virtualMemory[i].getReferencedBit() == false && Memories.virtualMemory[i].getPresentBit() == true) {
                    int temp = Memories.physicalMemory[Memories.virtualMemory[i].getPageFrame()];
                    Memories.physicalMemory[Memories.virtualMemory[i].getPageFrame()] = Memories.swapArea[Memories.virtualMemory[pos].getPageFrame()];
                    Memories.swapArea[Memories.virtualMemory[pos].getPageFrame()] = temp;
                    Page tempPage = Memories.virtualMemory[pos];
                    Memories.virtualMemory[pos] = Memories.virtualMemory[i];
                    Memories.virtualMemory[i] = tempPage;
                    Memories.virtualMemory[i].setPresentBit(false);
                    Memories.virtualMemory[pos].setPresentBit(true);
                    System.out.println("O valor da memória virtual na posição " + pos + " foi trocado com o valor da memória virtual na posição " + i);
                    System.out.println("Foi escrito o valor " + val + " na posição " + Memories.virtualMemory[pos].getPageFrame() + " da memória física\n");
                    return;
                }
            }
        }

        // quando a posição da memória virtual está vazia e a memória física também
        for (int i = 0; i < Memories.PHYSICAL_MEMORY_SIZE; i++) {
            if (Memories.physicalMemory[i] == null) {
                Memories.physicalMemory[i] = val;
                Memories.virtualMemory[pos] = new Page(i);
                System.out.println("A memória virtual na posição " + pos + " foi alocada na memória física na posição " + i);
                System.out.println("Foi escrito o valor " + val + " na posição " + i + " da memória física\n");
                return;
            }
        }

        // quando a posição da memória virtual está vazia e a memória física está cheia
        for (int i = 0; i < Memories.VIRTUAL_MEMORY_SIZE; i++) {
            if (Memories.virtualMemory[i] != null && Memories.virtualMemory[i].getReferencedBit() == true && Memories.virtualMemory[i].getPresentBit() == true) {
                Memories.virtualMemory[i].setReferencedBit(false);
                continue;
            }
            if (Memories.virtualMemory[i] != null && Memories.virtualMemory[i].getReferencedBit() == false && Memories.virtualMemory[i].getPresentBit() == true) {
                for (int j = 0; j < Memories.PHYSICAL_MEMORY_SIZE; j++) {
                    if (Memories.swapArea[j] == null) {
                        Memories.swapArea[j] = Memories.physicalMemory[Memories.virtualMemory[i].getPageFrame()];
                        Memories.physicalMemory[Memories.virtualMemory[i].getPageFrame()] = val;
                        Memories.virtualMemory[pos] = new Page(Memories.virtualMemory[i].getPageFrame());
                        Memories.virtualMemory[pos].setReferencedBit(true);
                        Memories.virtualMemory[i].setPageFrame(j);
                        Memories.virtualMemory[i].setPresentBit(false);
                        System.out.print(Utils.RED + "Page Fault " + Utils.RESET);
                        System.out.println("A memória virtual na posição " + i + " foi transferida para a área de troca");
                        System.out.println("A memória virtual na posição " + pos + " foi alocada na memória física na posição " + Memories.virtualMemory[i].getPageFrame());
                        System.out.println("Foi escrito o valor " + val + " na posição " + Memories.virtualMemory[i].getPageFrame() + " da memória física\n");
                        return;
                    }
                }
            }
        }
    }

    // função para ler da memória
    public synchronized int readCommand(int pos) {
        // quando a posição da memória virtual está vazia
        if (Memories.virtualMemory[pos] == null) {
            System.out.println("Posição vazia");
            return -1;
        }

        // quando a posição da memória virtual está apontando para a swap
        if (Memories.virtualMemory[pos].getPresentBit() == false) {
            for (int i = 0; i < Memories.VIRTUAL_MEMORY_SIZE; i++) {
                if (Memories.virtualMemory[i] != null && Memories.virtualMemory[i].getReferencedBit() == true && Memories.virtualMemory[i].getPresentBit() == true) {
                    Memories.virtualMemory[i].setReferencedBit(false);
                }
                if (Memories.virtualMemory[i] != null && Memories.virtualMemory[i].getReferencedBit() == false && Memories.virtualMemory[i].getPresentBit() == true) {
                    int temp = Memories.physicalMemory[Memories.virtualMemory[i].getPageFrame()];
                    Memories.physicalMemory[Memories.virtualMemory[i].getPageFrame()] = Memories.swapArea[Memories.virtualMemory[pos].getPageFrame()];
                    Memories.swapArea[Memories.virtualMemory[pos].getPageFrame()] = temp;
                    Page tempPage = Memories.virtualMemory[pos];
                    Memories.virtualMemory[pos] = Memories.virtualMemory[i];
                    Memories.virtualMemory[i] = tempPage;
                    Memories.virtualMemory[i].setPresentBit(false);
                    Memories.virtualMemory[pos].setPresentBit(true);
                    return Memories.physicalMemory[Memories.virtualMemory[pos].getPageFrame()];
                }
            }
        }

        // quando a posição da memória virtual já tem uma página alocada
        Integer pag = Memories.virtualMemory[pos].getPageFrame();
        Memories.virtualMemory[pos].setReferencedBit(true);
        Memories.physicalMemory[pag] = Memories.virtualMemory[pos].getPageFrame();
        return Memories.physicalMemory[pag];
    }
}

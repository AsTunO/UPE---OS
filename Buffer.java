import java.util.Random;

public class Buffer {

    final int VIRTUALMEMORYSIZE = 16;
    final int RANDOMACCESSMEMORYSIZE = 8;
    final int SWAPSIZE = 8;

    int[] virtualMemory; 
    int[] physicalMemory;
    int[] SWAPMemory;

    public Buffer() {
        virtualMemory = new int[VIRTUALMEMORYSIZE];
        physicalMemory = new int[RANDOMACCESSMEMORYSIZE];
        SWAPMemory = new int[SWAPSIZE];

        populateMemory(virtualMemory);
        populateMemory(physicalMemory);
        populateMemory(SWAPMemory);
    }

    public static void populateMemory(int[] currentMemory) {
        Random random = new Random();
        for (int i = 0; i < currentMemory.length; i++) {
            currentMemory[i] = random.nextInt(100); 
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
public class Main {
    final static int PHYSICAL_MEMORY_SIZE = 8;
    final static int VIRTUAL_MEMORY_SIZE = 2 * PHYSICAL_MEMORY_SIZE;

    static String[] virtualMemory = new String[VIRTUAL_MEMORY_SIZE];
    static String[] physicalMemory = new String[PHYSICAL_MEMORY_SIZE];
    public static void main(String[] args) {

        executeCommand("2w8");
        executeCommand("1w17");
        executeCommand("5w49");
        executeCommand("14w56");
        executeCommand("10w1");
        executeCommand("0w5");

        System.out.println("VIRTUAL MEMORY");
        for (int i = VIRTUAL_MEMORY_SIZE - 1; i >= 0 ; i--) {
            System.out.println(i + "| " + virtualMemory[i]);
        }

        System.out.println("PHYSICAL MEMORY");
        for (int i = PHYSICAL_MEMORY_SIZE - 1; i >= 0 ; i--) {
            System.out.println(i + "| " + physicalMemory[i]);
        }
    }

    public static void executeCommand(String cmd) {
        if (cmd.contains("w")) {
            String[] command = cmd.split("w", -1);
            String val = command[1];
            int pos = Integer.parseInt(command[0]);
            String pag = "";

            while (pag.equals("") || pag.equals("-1")) {
                for (int i = 0; i < PHYSICAL_MEMORY_SIZE; i++) {
                    if (physicalMemory[i] == null) {
                        pag = Byte.toString((byte) i);
                        physicalMemory[i] = val;
                        break;
                    } else if (pag.equals("-1")) {
                        physicalMemory[i] = val;
                        break;
                    } else if (i + 1 == PHYSICAL_MEMORY_SIZE) {
                        i = 0;
                        pag = "-1";
                        break;
                    }
                }
            }
            if (virtualMemory[pos] == null) {
                virtualMemory[pos] = pag;
            }
        }
    }
}
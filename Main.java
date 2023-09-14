public class Main {
    final static int PHYSICAL_MEMORY_SIZE = 8;
    final static int VIRTUAL_MEMORY_SIZE = 2 * PHYSICAL_MEMORY_SIZE;

    static Page[] virtualMemory = new Page[VIRTUAL_MEMORY_SIZE];
    static String[] physicalMemory = new String[PHYSICAL_MEMORY_SIZE];
    static Integer[] swapArea = new Integer[VIRTUAL_MEMORY_SIZE];
    public static void main(String[] args) {
        executeCommand("2-w-8");
        executeCommand("1-w-17");
        executeCommand("5-w-49");
        executeCommand("14-w-56");
        executeCommand("10-w-1");
        executeCommand("0-w-5");

        System.out.println("VIRTUAL MEMORY");
        for (int i = VIRTUAL_MEMORY_SIZE - 1; i >= 0 ; i--) {
            try {
                System.out.println(i + "| " + virtualMemory[i].getPageFrame());
            } catch (Exception e) {
                System.out.println(i + "| X");
            }
        }

        System.out.println("PHYSICAL MEMORY");
        for (int i = PHYSICAL_MEMORY_SIZE - 1; i >= 0 ; i--) {
            System.out.println(i + "| " + physicalMemory[i]);
        }
    }

    public static void executeCommand(String cmd) {
        String[] command = cmd.split("-");
        if (cmd.contains("w")) {
            String val = command[2];
            int pos = Integer.parseInt(command[0]);
            Integer pag = -1;

            while (pag == -1 || pag == -2) {
                for (int i = 0; i < PHYSICAL_MEMORY_SIZE; i++) {
                    if (physicalMemory[i] == null) {
                        pag = i;
                        physicalMemory[i] = val;
                        break;
                    } else if (pag == -2) {
                        pag = i;
                        physicalMemory[i] = val;
                        break;
                    } else if (i + 1 == PHYSICAL_MEMORY_SIZE) {
                        i = 0;
                        pag = -2;
                        break;
                    }
                }
            }
            // if (virtualMemory[pos] == null) {
            virtualMemory[pos] = new Page(pag);
            // }
        }
    }
}

/* [TODO)[TODO)[TODO)[TODO)[TODO)[TODO)[TODO)[TODO)[TODO)[TODO)[TODO)[TODO)[TODO)
 *      - criar uma classe para funcionar como a página da memória virtual
 */
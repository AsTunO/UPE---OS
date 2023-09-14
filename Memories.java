public class Memories {
    final static int PHYSICAL_MEMORY_SIZE = 8;
    final static int VIRTUAL_MEMORY_SIZE = 2 * PHYSICAL_MEMORY_SIZE;

    static Page[] virtualMemory = new Page[VIRTUAL_MEMORY_SIZE];
    static Integer[] physicalMemory = new Integer[PHYSICAL_MEMORY_SIZE];
    static Integer[] swapArea = new Integer[VIRTUAL_MEMORY_SIZE];
}

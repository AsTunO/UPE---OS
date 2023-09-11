public class GraphicInterface {

    public static void main(String[] args) {

        Buffer memories = new Buffer();
        memories.showStateOfMemory();

        ProcessesManager process01 = new ProcessesManager("{4-R, 5-R, 0-R,4-W-2}",memories, 01);
        ProcessesManager process02 = new ProcessesManager("{1-R, 5-W-4, 2-R,2-W-6}",memories, 02);

        process01.start();
        process02.start();
    }   
}
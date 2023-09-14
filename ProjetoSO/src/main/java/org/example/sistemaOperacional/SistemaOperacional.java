package org.example.sistemaOperacional;

import org.example.memory.DiscoSwap;
import org.example.memory.Principal;
import org.example.memory.Virtual;
import org.example.page.VirtualPage;

public class SistemaOperacional {
    private String jobUm = "5-R,5-R,0-R,4-W-2";
    private String jobDois = "1-R,5-W-4,8-R,2-W-6";
//    private String jobDois = "1-R,5-W-4,2-R,2-W-6,10-R,13-W-14,12-R";

    private static Principal memoriaPrincipal = new Principal();
    private static Virtual memoriaVirtual = new Virtual();
    private static DiscoSwap discoSwap = new DiscoSwap();


    EntradaDeDados entradaDeDadosUm = new EntradaDeDados(this.jobUm);
    EntradaDeDados entradaDeDadosDois = new EntradaDeDados(this.jobDois);

    public SistemaOperacional(){
        Thread threadUm = new Thread(entradaDeDadosUm, "Thread 1");
        Thread threadDois = new Thread(entradaDeDadosDois, "Thread 2");

        threadUm.start();
        threadDois.start();
    }

    // Esse método execulta os jobs
    public synchronized static void executarJobs(String jobs) {
        String[] jobsSeparados = jobs.split(",");
        for (String job : jobsSeparados) {
            String[] jobTarefas = job.split("-");
            if (jobTarefas.length == 2){
                executaJobLeitura(jobTarefas);
            } else if (jobTarefas.length == 3) {
                executaJobEscrita(jobTarefas);
            }
        }
    }

    // Esse método execulta o job de leitura.
    public  static void executaJobLeitura(String[] job){
        System.out.println("\u001B[32m" + "\nA Thread " + Thread.currentThread().getName() + " iniciou o job: " + job[0] + "-" + job[1] + "\u001B[0m");
        mostraMemorias();

        if (memoriaPrincipal.checkMappedPage(Integer.parseInt(job[0]))){  // Verifico se a página está mapeada na memória principal.
            VirtualPage virtualPage = memoriaVirtual.getPage(Integer.parseInt(job[0]));  // Pego a página da memória virtual.
            memoriaPrincipal.executeInstruction(virtualPage, job);  // Execulto a instrução.
        }
        else if (discoSwap.checkMappedPage(Integer.parseInt(job[0]))){  // Verifico se a página está mapeada na memória principal.
            trocaReferencia(job);
        }
        else if(memoriaVirtual.verificarSeExistePage(Integer.parseInt(job[0]))) {  // Verifica se a página está mapeada na memoria virtual.
            trocaReferencia(job);
        }
        else{
            System.out.println("Segment fault");
        }
    }

    // Método que execulta o job de escrita.
    public  static void executaJobEscrita(String[] job){
        System.out.println("\u001B[32m" + "\nA Thread " + Thread.currentThread().getName() + " iniciou o job: " + job[0] + "-" + job[1] + "-" + job[2] + "\u001B[0m");

        if (memoriaPrincipal.checkMappedPage(Integer.parseInt(job[0]))){  // Verifico se a página está mapeada na memória principal.
            VirtualPage virtualPage = memoriaVirtual.getPage(Integer.parseInt(job[0]));  // Pego a página da memória virtual.
            memoriaPrincipal.executeInstruction(virtualPage, job);  // Execulto a instrução.
        }
        else if (discoSwap.checkMappedPage(Integer.parseInt(job[0]))){  // Verifico se a página está mapeada na memória principal.
            trocaReferencia(job);
        }
        else if(memoriaVirtual.verificarSeExistePage(Integer.parseInt(job[0]))) {  // Verifica se a página está mapeada na memoria virtual.
            trocaReferencia(job);
        }
        else{
            System.out.println("Segment fault");
        }
    }

    // Esse método é responsável por fazer a troca de referência de uma memoria para outro.
    public static void trocaReferencia(String[] job){
        VirtualPage virtualPage = memoriaVirtual.getPage(Integer.parseInt(job[0]));// Pego a página da memória virtual.
        if(memoriaPrincipal.fazMapemento(virtualPage)) {  // Faço o mapeamento da página na memória principal.
            memoriaPrincipal.executeInstruction(virtualPage, job);  // Execulto a instrução.
        }
        if (memoriaPrincipal.getUltimaPageRemovida() != null){  // Verifico se algum item foi removido da memória principal.
            VirtualPage virtualPageDisco = memoriaPrincipal.getUltimaPageRemovida(); // Pega a página removida da memória principal.
            virtualPageDisco.setPresentAunsete(0); // Seto o bit de presente para 0.
            discoSwap.fazMapemento(virtualPageDisco);  // Faço o mapeamento da página no disco de swap.
        }
    }

    public static void mostraMemorias(){
        memoriaPrincipal.printPageTable();
        memoriaVirtual.printPageTable();
        discoSwap.printPageTable();
    }
}

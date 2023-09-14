package org.example.sistemaOperacional;

import static org.example.sistemaOperacional.SistemaOperacional.executarJobs;

public class EntradaDeDados implements Runnable{
    private String jobs;

    public EntradaDeDados(String jobs){
        this.jobs = jobs;
    }

    public void run(){
        this.iniciaJobs();
    }

    //   Método que execulta os jobs
    public void iniciaJobs(){
        // colar na cor verde
        executarJobs(this.jobs);
    }
}

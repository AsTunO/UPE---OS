package org.example.memory;

import org.example.page.VirtualPage;
import org.example.sistemaOperacional.PageTable;

import java.util.Objects;

public class Principal {
    PageTable pageTable = new PageTable();


// Esse método é responsável por verificar se a página está mapeada ou não. Se estiver mapeada, podemos excutar a instrução leitura ou escrita.
    public boolean checkMappedPage(int pageNumber) {
        if (pageTable.verificaSeEstaMapeado(pageNumber)) {
            System.out.println("Page " + pageNumber + " está mapeado.");
            return true;

        } else {
            return false;
        }
    }

 // Esse método é responsável por fazer o mapeamento da página na memória principal.
    public boolean fazMapemento(VirtualPage virtualPage){
        if (pageTable.addPageTable(virtualPage)){  // Adiciono a página na tabela de páginas da memória principal.
            System.out.println("A página " + virtualPage.getNumeroPagina() + " foi mapeada na memória principal.");
            return true;  // Retorna True se for verdadeiro
        } else {
            System.out.println("A tabela de páginas está cheia.");
            pageTable.removePageTable();  // Removo a página mais antiga da tabela de páginas.
            fazMapemento(virtualPage);  // Chamo o método novamente para fazer o mapeamento da página.
        }
        return false;
    }

// Esse método retorna o número da ultima página removida.
    public VirtualPage getUltimaPageRemovida(){
        return pageTable.getPaginaRemovida();
    }

    // Esse método é responsável por executar a instrução.
    public void executeInstruction(VirtualPage virtualPage, String[] job) {
        if (Objects.equals(job[1], "R")) {
            virtualPage.setPresentAunsete(1);
            System.out.println("A página " + virtualPage.getNumeroPagina() + " foi lida. Seu valor é: " + virtualPage.getOffSet());
        } else if (Objects.equals(job[1], "W")) {
            virtualPage.setOffSet(Integer.parseInt(job[2]));
            System.out.println("A página " + virtualPage.getNumeroPagina() + " foi escrita. O valor escrito foi: " + virtualPage.getOffSet());
        }
    }

    public void printPageTable(){
        System.out.println("Tabela de páginas da memória principal");
        pageTable.mostaTabelaPagina();
    }
}

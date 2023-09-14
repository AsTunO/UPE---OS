package org.example.memory;

import org.example.page.VirtualPage;
import org.example.sistemaOperacional.PageTable;

public class DiscoSwap {
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
            System.out.println("A página " + virtualPage.getNumeroPagina() + " foi mapeada na memória Swap.");
            return true;  // Retorna True se for verdadeiro
        } else {
            System.out.println("A tabela de páginas está cheia.");
            pageTable.removePageTable();  // Removo a página mais antiga da tabela de páginas.
            fazMapemento(virtualPage);  // Chamo o método novamente para fazer o mapeamento da página.
        }
        return false;
    }

    public void printPageTable(){
        System.out.println("Tabela de páginas da memória Swap");
        pageTable.mostaTabelaPagina();
        System.out.println("---------------------------------------------------");
    }
}

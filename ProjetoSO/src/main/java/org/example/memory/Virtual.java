package org.example.memory;

import org.example.page.VirtualPage;

public class Virtual {

    public Virtual(){
        populatePages();
    }

    private final int QTD_QUADRO_PAGINA = 16;
    VirtualPage[] pages = new VirtualPage[QTD_QUADRO_PAGINA];

    public void populatePages() {
        for (int i = 0; i < pages.length; i++) {
            int random = (int) (Math.random() * 100);
            pages[i] = new VirtualPage(random , i, 0, false, 0, 0);
        }
    }

    // Esse método retorna uma página específica da memória virtual.
    public VirtualPage getPage(int idPagina){
        for (int i = 0; i < pages.length; i++) {
            if (pages[i].getNumeroPagina() == idPagina){
                return pages[i];
            }
        }
        return null;
    }

    public boolean verificarSeExistePage(int idPagina){
        for (int i = 0; i < pages.length; i++) {
            if (pages[i].getNumeroPagina() == idPagina){
                System.out.println("A página " + idPagina + " está na memória virtual e será mapeada para a memória principal");
                return true;
            }
        }
        return false;
    }

    public void printPageTable(){
        System.out.println("Tabela de páginas da memória virtual");
        for (int i = 0; i < pages.length; i++) {
            System.out.println("Página: " + pages[i].getNumeroPagina() + "| Valor OffSet: " + pages[i].getOffSet() + "| Protection Bit: " + pages[i].getProtectionBit() + "| Present/Aunsete: " + pages[i].getPresentAunsete()); ;
        }
        System.out.println("---------------------------------------------------");
    }
}

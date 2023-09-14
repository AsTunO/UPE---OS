package org.example.sistemaOperacional;
import org.example.page.VirtualPage;
import java.util.ArrayDeque;
import java.util.Queue;

// Essa classe page table tem o objetivo de fazer o mapeamento entre a memória virtual e a memória física.
public class PageTable {
    VirtualPage virtualPage = null;

    private int QTD_QUADRO_PAGINA = 8;
    Queue<VirtualPage> mapeamentoPaginas = new ArrayDeque<>(QTD_QUADRO_PAGINA);

    // Esse método é responsável por fazer a ligação entre a memória virtual e a memória física.
    public boolean addPageTable(VirtualPage virtualPage){
        if (qtdQuandros()) {
            virtualPage.setPresentAunsete(1);
            mapeamentoPaginas.add(virtualPage);
            return true;
        }
        return false;
    }

    // Essa método remove quando a tabela de páginas está cheia. Retorna o id da página removida.
    public void removePageTable(){
        System.out.println("A tabela de páginas está cheia. Removendo a página mais antiga.");
        this.virtualPage = mapeamentoPaginas.remove();
        virtualPage.setPresentAunsete(0);
        System.out.println("Vamos mapear o página removida para o disco de swap.");
    }

    // Esse método retorna a página removida.
    public VirtualPage getPaginaRemovida(){
        return virtualPage;
    }
    // Esse método mostra a tabela de páginas.
    public void mostaTabelaPagina(){
        for (VirtualPage mapeamentoPagina: mapeamentoPaginas){
            System.out.println("Id da página: " + mapeamentoPagina.getNumeroPagina());
        }
        System.out.println("---------------------------------------------------");
    }

// Esse método verifcar se a página está mapeada na memoria ram.
    public boolean verificaSeEstaMapeado(int idPagina){
        return mapeamentoPaginas.contains(idPagina);
    }

    // Retonar a quantidade de quandros mapeados para começar o uso da fina para liberar páginas.
    public boolean qtdQuandros(){
        return mapeamentoPaginas.size() < QTD_QUADRO_PAGINA;
    }

}

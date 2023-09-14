package org.example.page;

public class VirtualPage {
    // O offSet é o 'endereço' onde essa page está
    private int offSet; // Esse é o offset da página. Ele é usado para calcular o endereço físico. Ela será o dado que será lido ou escrito.
    private int numeroPagina; // Número da página que está sendo acessada.
    private int reference; // Bit de referência representa se o dado foi acessado ou não.
    private boolean dirtyBit; // Bit de modificação
    private int presentAunsete;  // Essa variável verifica se está em na memória principal ou não. se for 0 significa que não está na memória principal. se for 1 significa que está na memória principal.
    private int protectionBit;  // Essa variável verifica se a página está protegida ou não. se for igual a 1 significa que está protegida. e nao
    // pode ser escrita. se for igual a 0 significa que não está protegida e pode ser escrita.


    public VirtualPage(int offSet, int numeroPagina, int reference, boolean dirtyBit, int presentAunsete, int protectionBit) {
        this.offSet = offSet;
        this.numeroPagina = numeroPagina;
        this.reference = reference;
        this.dirtyBit = dirtyBit;
        this.presentAunsete = presentAunsete;
        this.protectionBit = protectionBit;
    }

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }

    public int getNumeroPagina() {
        return numeroPagina;
    }

    public int getProtectionBit() {
        return protectionBit;
    }

    public int getPresentAunsete() {
        return presentAunsete;
    }

    public void setPresentAunsete(int presentAunsete) {
        this.presentAunsete = presentAunsete;
    }

    public void setDirtyBit(boolean dirtyBit) {
        this.dirtyBit = dirtyBit;
    }
}

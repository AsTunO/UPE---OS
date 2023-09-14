public class Page {
    private Boolean referencedBit;
    private Boolean presentBit;
    private Integer pageFrame;
    
    public Page(Integer pageFrame) {
        this.referencedBit = false;
        this.presentBit = true;
        this.pageFrame = pageFrame;
    }

    public Boolean getReferencedBit() {
        return referencedBit;
    }
    public void setReferencedBit(Boolean referencedBit) {
        this.referencedBit = referencedBit;
    }
    public Integer getPageFrame() {
        return pageFrame;
    }
    public void setPageFrame(Integer pageFrame) {
        this.pageFrame = pageFrame;
        setReferencedBit(false);
    }
    public Boolean getPresentBit() {
        return presentBit;
    }
    public void setPresentBit(Boolean presentBit) {
        this.presentBit = presentBit;
    }

    @Override
    public String toString() {
        return "Page [referencedBit=" + referencedBit + ", presentBit=" + presentBit + ", pageFrame=" + pageFrame + "]";
    }
}
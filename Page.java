public class Page {
    private Integer referencedBit;
    private Integer modifiedBit;
    private Integer presentBit;
    private Integer pageFrame;
    
    public Page(Integer pageFrame) {
        this.referencedBit = 1;
        this.modifiedBit = 1;
        this.presentBit = 1;
        this.pageFrame = pageFrame;
    }

    public Integer getReferencedBit() {
        return referencedBit;
    }
    public void setReferencedBit(Integer referencedBit) {
        this.referencedBit = referencedBit;
    }
    public Integer getModifiedBit() {
        return modifiedBit;
    }
    public void setModifiedBit(Integer modifiedBit) {
        this.modifiedBit = modifiedBit;
    }
    public Integer getPageFrame() {
        return pageFrame;
    }
    public void setPageFrame(Integer pageFrame) {
        this.pageFrame = pageFrame;
        setModifiedBit(1);
        setReferencedBit(1);
    }
    public Integer getPresentBit() {
        return presentBit;
    }
    public void setPresentBit(Integer presentBit) {
        this.presentBit = presentBit;
    }

    @Override
    public String toString() {
        return "Page [referencedBit=" + referencedBit + ", modifiedBit=" + modifiedBit + ", presentBit=" + presentBit
                + ", pageFrame=" + pageFrame + "]";
    }
}
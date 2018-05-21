package ro.utcluj.student;

public class PageTableEntry {
    private int index;
    private int physicalPageNumber;

    public PageTableEntry(int index, int physicalPageNumber) {
        this.index = index;
        this.physicalPageNumber = physicalPageNumber;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPhysicalPageNumber() {
        return physicalPageNumber;
    }

    public void setPhysicalPageNumber(int physicalPageNumber) {
        this.physicalPageNumber = physicalPageNumber;
    }
}

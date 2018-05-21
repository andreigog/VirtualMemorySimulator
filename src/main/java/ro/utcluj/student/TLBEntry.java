package ro.utcluj.student;

public class TLBEntry {
    private int virtualPageNumber;
    private int physicalPageNumber;

    public TLBEntry(int virtualPageNumber, int physicalPageNumber) {
        this.virtualPageNumber = virtualPageNumber;
        this.physicalPageNumber = physicalPageNumber;
    }

    public int getVirtualPageNumber() {
        return virtualPageNumber;
    }

    public void setVirtualPageNumber(int virtualPageNumber) {
        this.virtualPageNumber = virtualPageNumber;
    }

    public int getPhysicalPageNumber() {
        return physicalPageNumber;
    }

    public void setPhysicalPageNumber(int physicalPageNumber) {
        this.physicalPageNumber = physicalPageNumber;
    }
}

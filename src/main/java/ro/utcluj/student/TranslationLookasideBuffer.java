package ro.utcluj.student;

import java.util.LinkedList;

public class TranslationLookasideBuffer {
    private int size;
    private LinkedList tlb;
    int space=0;

    public TranslationLookasideBuffer(int size){
        this.size=size;
        tlb=new LinkedList<TLBEntry>();
    }

    public void addNewEntry(TLBEntry entry){
       if (space<this.size){
           tlb.addLast(entry);
           space++;
       }else {
           tlb.removeFirst();
           tlb.addLast(entry);
       }
    }

    public LinkedList<TLBEntry> getTlb() {
        return tlb;
    }
}

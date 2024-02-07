package org.example.quoters;

@Profiling
public class T1000 extends TerminatorQuoter implements Quoter {
    @Override
    public void sayQuote() {
        System.out.println("ß ÆÈÄÊÈÉ");
    }
}

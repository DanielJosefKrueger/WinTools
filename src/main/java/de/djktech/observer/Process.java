package de.djktech.observer;

public class Process {



    private final String name;
    private final int pid;
    private final String sitzungsname;
    private final int sitzungsNUmmer;
    private final int speichernutzung;


    public Process(String name, int pid, String sitzungsname, int sitzungsNUmmer, int speichernutzung) {
        this.name = name;
        this.pid = pid;
        this.sitzungsname = sitzungsname;
        this.sitzungsNUmmer = sitzungsNUmmer;
        this.speichernutzung = speichernutzung;
    }


    public static Process fromString(String line){
        
        return null; //TODO implement
    }

}

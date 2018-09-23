package de.djktech.observer;

import java.util.ArrayList;
import java.util.List;

public class ProcessInformation {


    private final String name;
    private final int pid;
    private final String sitzungsname;
    private final int sitzungsNUmmer;
    private final int speichernutzung;


    public ProcessInformation(String name, int pid, String sitzungsname, int sitzungsNUmmer, int speichernutzung) {
        this.name = name;
        this.pid = pid;
        this.sitzungsname = sitzungsname;
        this.sitzungsNUmmer = sitzungsNUmmer;
        this.speichernutzung = speichernutzung;
    }

    public String toString(){
        return "[Name: " + name + ", pid: " + pid + "]\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessInformation that = (ProcessInformation) o;

        if (pid != that.pid) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + pid;
        return result;
    }

    static ArrayList<ProcessInformation> loadInformation (List<String> output){

        ArrayList<ProcessInformation> information = new ArrayList<>();
        String headline = output.get(2); // we orientate on the ####
        final int endOfName = headline.indexOf(" ");
        String restOfHeadline = headline.substring(endOfName+1);
        final int endOfPid = endOfName+1 + restOfHeadline.indexOf(" ");
        restOfHeadline = headline.substring(endOfPid+1);
        final int endOfSitzungsname =endOfPid +1 +  restOfHeadline.indexOf(" ");
        restOfHeadline = headline.substring(endOfSitzungsname+1);
        final int endOfSitzungsNummer = endOfSitzungsname +1+restOfHeadline.indexOf(" ");


        for (int i = 3; i < output.size(); i++) {
            String line = output.get(i);
            String name = line.substring(0, endOfName).trim();
            int pid = Integer.valueOf(line.substring(endOfName+1, endOfPid).trim());
            String sitzungsName = line.substring(endOfPid+1, endOfSitzungsname);
            int sitzungsNummer = Integer.valueOf(line.substring(endOfSitzungsname+1, endOfSitzungsNummer).trim());
            int memory = Integer.valueOf(line.substring(endOfSitzungsNummer+1, line.length()-1).replaceAll("\\.","" ).trim());
            information.add(new ProcessInformation(name, pid, sitzungsName, sitzungsNummer, memory));
        }
        return information;
    }





}

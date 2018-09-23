package de.djktech.observer;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import de.djktech.observer.ProcessInformation;

public class ProgrammObserver {


    private final static int INTERVALL = 30000;
    static ArrayList<ProcessInformation> oldProcesses = new ArrayList<>();

    // THX to https://www.tutorials.de/threads/prozesse-auslesen-via-java.376847/

    public static void main(String[] args) throws Exception {
        // System.setOut(new PrintStream(new FileOutputStream("out.log")));
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("out.log", true)))) {
            while (true) {
                bufferedWriter.write("\n###\n" + formatter.format(System.currentTimeMillis()) + "\n");
                listProcesses(bufferedWriter);
                listChanges();
                Thread.sleep(INTERVALL);
            }
        }
    }


    private static  void listChanges() throws IOException, InterruptedException {
        ArrayList<ProcessInformation> newProcesses = ProcessInformation.loadInformation(loadOutput());
        List<ProcessInformation> newClone = (ArrayList<ProcessInformation>)newProcesses.clone();
        List<ProcessInformation> oldClone = (ArrayList<ProcessInformation>)oldProcesses.clone();


        oldClone.removeAll(newProcesses);
        newClone.removeAll(oldProcesses);



        System.out.println("Following Processes have ended:\n" + oldClone);
        System.out.println("Following Processes have started:\n" + newClone);
        oldProcesses = newProcesses;
    }



    private static List<String> loadOutput() throws IOException, InterruptedException {
        List<String> output = new ArrayList<>();
        Process process = new ProcessBuilder("cmd", "/c", "tasklist").start();
        Scanner scanner = new Scanner(process.getInputStream());
        while (scanner.hasNextLine()) {
            output.add(scanner.nextLine());
        }
        scanner.close();
        process.waitFor();
        return output;

    }

    private static void listProcesses(BufferedWriter bufferedWriter) throws IOException, InterruptedException {
        int counter = -2;
        System.out.println("Listing running programs");
        for (String s : loadOutput()) {
            bufferedWriter.write(s + "\n");
        }
        ;
        bufferedWriter.write("Anzahl der Prozesse: " + counter);

    }


}

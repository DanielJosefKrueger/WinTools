package de.djktech.observer;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ProgrammObserver {

    // THX to https://www.tutorials.de/threads/prozesse-auslesen-via-java.376847/

    public static void main(String[] args) throws Exception {
       // System.setOut(new PrintStream(new FileOutputStream("out.log")));
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");



            try(BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("out.log", true)))){
                while(true) {
                    bufferedWriter.write("\n###\n" + formatter.format(System.currentTimeMillis())+ "\n");
                    listProcesses(bufferedWriter);
                    Thread.sleep(5000);
                }
            }


        }


    private static void listProcesses(BufferedWriter bufferedWriter) throws IOException, InterruptedException {
        System.out.println("Listing running programs");
        Process process = new ProcessBuilder("cmd", "/c", "tasklist").start();
        Scanner scanner = new Scanner(process.getInputStream());
        while (scanner.hasNextLine()) {
            bufferedWriter.write(scanner.nextLine() + "\n");
        }
        scanner.close();
        process.waitFor();
    }


}

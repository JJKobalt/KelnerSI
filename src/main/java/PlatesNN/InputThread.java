package PlatesNN;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;




/*Tworzy nowy proces który w sposób stały nasłuchuje odpowiedzi i zapisuje ostatnią linie jaka pojawiła się na wejściu*/
public class InputThread extends Thread {
    private Process process;
    private String lastLine = null;
    boolean endofinput;

    InputThread(Process process) {
        super();
        this.process = process;

    }

    @Override
    public void run() {

        BufferedReader r;
        r = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String bufferedLine = null;
        while (true) {
            try {
                bufferedLine = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bufferedLine != null) {

                lastLine = bufferedLine;


            } else {

            }


        }
    }

    public synchronized String getLastLine() {
        return lastLine;
    }
}

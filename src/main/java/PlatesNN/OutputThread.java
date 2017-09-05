package PlatesNN;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


public class OutputThread {
    private Process process;
    private BufferedWriter output;

    /*Konstruktor na podstawie przekazanego procesu tworzy stream którym są wysyłane do niego zapytania.*/
    public OutputThread(Process process) {
        this.process = process;
        try {
            output = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(),"windows-1250"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    /*Wysyłanie zapytanie do podprocesu, nowa linia potwierdza zapytanie.*/
    public void writeToOutput(String toOutput) {

        try {

            output.write(toOutput);
            output.newLine();
            output.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

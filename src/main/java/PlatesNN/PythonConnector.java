package PlatesNN;


import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.IOException;

public class PythonConnector {

        Process pythonProcess;
        InputThread input;
        OutputThread output;

        /*PRzy tworzenie nowego obiektu typu prolog uruchamiany jest nowy podproces prolog oraz
         nowy wątek odpowiedzialny za nasluchiwanie odpowiedzi od niego*/
        public PythonConnector() {
            try {
                ProcessBuilder builder = new ProcessBuilder("python", "C:\\Users\\Dominika\\PycharmProjects\\SI\\classifier.py");
                builder.redirectErrorStream(true);
                pythonProcess = builder.start();
                input = new InputThread(pythonProcess);
                output = new OutputThread(pythonProcess);
                input.start();
            }
            catch (Exception ex){
                throw new RuntimeException();
            }
        }

        /*Metoda wysyła odpowiednio sformatowanie zapytania do pythona i zwraca odpowiedź (jest czy nie pelnym talerzem) */
        public boolean parse(String path) {



            output.writeToOutput(path);

            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String pythonOutput = input.getLastLine();
            switch (pythonOutput) {
                case "['Danie']":

                    return true;

                case "['Puste']":
                    return false;

                default: return false;
            }

        }

    }




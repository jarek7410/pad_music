package github.jarek7410;

import com.studiohartman.jamepad.ControllerButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class Config {
    private String[] traks;
    public String[] traks() {
        return traks;
    }
    private HashMap<String,String> buttonsAction;

    public HashMap<String, String> getButtonsAction() {
        return buttonsAction;
    }

    private int numberOfTreks;
    public int getNumberOfTreks(){
        return numberOfTreks;
    }

    File config;
    public Config(){
        this.config=new File("config.txt");
    }

    protected void getConfigs(){

        try {
            Vector<String> vv=new Vector<>();
            Scanner scanner = new Scanner(config);
            class Local {
                String s;
                String line(){
                    do {

                        s = scanner.nextLine();
                    }while(s.isEmpty() || s.charAt(0) == '#');

                    return s;
                }
            }
            Local l=new Local();
            numberOfTreks= Integer.parseInt(l.line());
            String[]ss=new String[numberOfTreks];
            String s;

            for (int i = 0; i < numberOfTreks; i++) {
                ss[i]=l.line();
            }
            traks=ss;

            ss=new String[(ControllerButton.values().length)];
            buttonsAction= new HashMap<>();

            for (int i = 0; i < (ControllerButton.values().length); i++) {
                s=l.line();
                s=s.replace(" ","");
                ss=s.split(":");
                buttonsAction.put(ss[0],ss[1]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

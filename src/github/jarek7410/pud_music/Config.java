package github.jarek7410.pud_music;

import com.studiohartman.jamepad.ControllerButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

public class Config {
    private String[] traks;
    public String[] traks() {
        return traks;
    }
    private HashMap<String,String>  buttonsAction,
                                    actionButtons;

    public HashMap<String, String> getButtonsAction() {
        return buttonsAction;
    }
    public HashMap<String, String> getActionButtons() {
        return actionButtons;
    }

    private int numberOfTreks;
    public int getNumberOfTreks(){
        return numberOfTreks;
    }

    private File config;

    public Config(){
        this.config=new File("config.txt");
    }

    public void setConfig(String[] traks,HashMap<String,String> actionButtons){

    }

    public void loadConfigs(){
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
            buttonsAction = new HashMap<>();
            actionButtons = new HashMap<>();

            for (int i = 0; i < (Actions.values().length); i++) {
                s=l.line();
                s=s.replace(" ","");
                ss=s.split(":");
                if(!(correctButton(ss[1])&&correctAction(ss[0])))
                    throw new Exception("incorrect config file! >"+ss[1]+"|"+ss[0]);
                buttonsAction.put(ss[1],ss[0]);
                if(ss[1]!="NULL"){
                       actionButtons.put(ss[0],ss[1]);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public boolean correctButton(String s){
        for(ControllerButton b:ControllerButton.values()){
            if(Objects.equals(s, b.name()))return true;
        }
        return false;
    }
    public boolean correctAction(String s){
        for(Actions a:Actions.values()){
            if(Objects.equals(s, a.name()))return true;
        }
        return false;
    }
}

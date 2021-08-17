package github.jarek7410.pud_music;

import com.studiohartman.jamepad.ControllerButton;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
    public Dimension winDim=new Dimension(640,300);

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

    public boolean frameRun =true;

    public Config(){
        this.config=new File("config.txt");
    }

    public Config(int width,int height){
        this.winDim=new Dimension(width,height);
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
            int numberOfActions = Integer.parseInt(l.line());
            buttonsAction = new HashMap<>();
            actionButtons = new HashMap<>();

            for (int i = 0; i < (numberOfActions); i++) {
                s=l.line();
                s=s.replace(" ","");
                ss=s.split(":");
                ss[0]=ss[0].strip();
                ss[1]=ss[1].strip();
                if(!(correctButton(ss[1])&&correctAction(ss[0])))
                    throw new Exception("incorrect config file! >"+ss[1]+"|"+ss[0]+"<\n"+
                            "button: "+ss[1]+" is "+ correctButton(ss[1])+"\n"+
                            "action: "+ss[0]+" is "+ correctAction(ss[0])+"\n");
                buttonsAction.put(ss[1],ss[0]);
                if(!Objects.equals(ss[1], "NULL")){
                       actionButtons.put(ss[0],ss[1]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void saveConfigs(){
        try {
            FileWriter configWriter=new FileWriter(config);
            /*configWriter.write("""
                        4
                        C:\\Users\\jarek\\Downloads\\X
                        C:\\Users\\jarek\\Downloads\\Y
                        C:\\Users\\jarek\\Downloads\\A
                        C:\\Users\\jarek\\Downloads\\B
                        7
                        ONE         :   A
                        TWO         :   B
                        THREE       :   X
                        FOUR        :   Y
                        CLOSE       :   BACK
                        STOP        :   LEFTBUMPER
                        CHANGE      :   RIGHTBUMPER""");*/
            configWriter.write(traks.length+"\r\n");
            for(String ss:traks){
                configWriter.write(ss+"\r\n");
            }
            configWriter.write(actionButtons.size()+"\r\n");
            Actions[] actions = Actions.values();
            for(int i =0;i<Actions.values().length;++i){
                configWriter.write(actions[i].name()+
                        "\t:\t"
                        +actionButtons.get(actions[i].name())+"\r\n");
            }


            configWriter.close();

        } catch (IOException e) {
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

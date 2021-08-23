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
    private String[] tracksNames;
    public Dimension winDim=new Dimension(640,320);

    public HashMap<String, String> getButtonsAction() {
        return buttonsAction;
    }
    public HashMap<String, String> getActionButtons() {
        return actionButtons;
    }

    public String[] getTracksNames(){
        return tracksNames;
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
            String[]ss=new String[numberOfTreks],split;
            String s,s1;
            String []trackName=new String[numberOfTreks];

            for (int i = 0; i < numberOfTreks; i++) {
                split=l.line().split("=");
                ss[i]=split[1].strip();
                trackName[i]=split[0].strip();
            }
            traks=ss;
            this.tracksNames=trackName;
            int numberOfActions = Integer.parseInt(l.line());
            buttonsAction = new HashMap<>();
            actionButtons = new HashMap<>();

            for (int i = 0; i < (numberOfActions); i++) {
                s=l.line();
                s=s.replace(" ","");
                split=s.split(":");
                split[0]=split[0].strip();
                split[1]=split[1].strip();
                if(!(correctButton(split[1])&&correctAction(split[0])))
                    throw new Exception("incorrect config file! >"+split[1]+"|"+split[0]+"<\n"+
                            "button: "+split[1]+" is "+ correctButton(split[1])+"\n"+
                            "action: "+split[0]+" is "+ correctAction(split[0])+"\n");
                buttonsAction.put(split[1],split[0]);
                if(!Objects.equals(split[1], "NULL")){
                       actionButtons.put(split[0],split[1]);
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

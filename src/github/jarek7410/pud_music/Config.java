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
    public int track=0;
    public void setTrack(int i){
        if(i<getNumberOfTreks())track=i;
    }
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
    public boolean running=true;

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
            Vector<String> ve=new Vector<>();
            while(l.line().equals("BEGIN:"));
            String []split;
            String s,s1;
            Vector<String>trackName=new Vector<>();
            for(s1=l.line();!s1.equals("ACTIONS:");s1=l.line()){
                split=s1.split("[=|]");
                ve.add(split[1].strip());
                trackName.add(split[0].strip());
            }
                traks=ListOfFiles.VtoS(ve);
            this.tracksNames=ListOfFiles.VtoS(trackName);
            this.numberOfTreks= ve.size();
            buttonsAction = new HashMap<>();
            actionButtons = new HashMap<>();

            for(s1=l.line();!s1.equals("END;");s1=l.line()){
                split=s1.split("[:=|]");
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
            System.err.println("""
                    your config file is invalid
                    please check it or redownland from app source""");
            e.printStackTrace();
            Main.use("CLOSE");
        }

    }
    public void saveDefaultConfig(){
        try {
            FileWriter configWriter = new FileWriter(config);
            configWriter.write("""
                                   BEGIN:
                                   TRACKS:
                                   trill    = C:\\Users\\jarek\\Downloads\\X
                                   fight    = C:\\Users\\jarek\\Downloads\\Y
                                   combat   = C:\\Users\\jarek\\Downloads\\A
                                   tavern   = C:\\Users\\jarek\\Downloads\\B
                                   wl2      = C:\\Users\\jarek\\Downloads\\wl2_choir_songs_mp3
                                   witcher  = C:\\Users\\jarek\\Downloads\\the_witcher_soundtrack_mp3
                                   ACTIONS:
                                   CLOSE	:	BACK
                                   STOP	    :	LEFTBUMPER
                                   CHANGE	:	RIGHTBUMPER
                                   ONE	    :	A
                                   TWO	    :	B
                                   THREE	:	X
                                   FOUR	    :	Y
                                   FIVE     : DPAD_UP
                                   SIX      : DPAD_DOWN
                                   END;
                                   """);

            configWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void changeOptionsBAAB(String button,String action){
        actionButtons.put(action,button);
        buttonsAction.put(button,action);
    }
    public void saveConfigs(){
        try {
            FileWriter configWriter=new FileWriter("test.txt");

            configWriter.write("BEGIN:\r\n");
            configWriter.write("TRACKS:\r\n");
            for(int i=0;i<traks.length;i++){
                configWriter.write(tracksNames[i]+" = "+traks[i]+"\r\n");
            }
            configWriter.write("ACTIONS:\r\n");
            Actions[] actions = Actions.values();
            for(int i =0;i<Actions.values().length;++i){
                configWriter.write(actions[i].name()+
                        "\t:\t"
                        +actionButtons.get(actions[i].name())+"\r\n");
            }
            configWriter.write("END;\r\n");


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

package github.jarek7410;

import com.studiohartman.jamepad.*;
import javazoom.jl.player.Player;

import java.util.HashMap;


public class Main{

    private static boolean running=true;

    private static final int numberOfPads=4;
    private static ControllerManager controllers ;
    private static final ControllerButton[] buttons=ControllerButton.values();

    private static Player player;
    private static boolean playMusic = false;
    private static int track = 0;
    private static Sound sound;
    private static HashMap<String, String> buttonActions;

    public static void main(String[] args) {
        //for frame
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable var2) {
            System.out.println("Error setting lookandfeel");
            var2.printStackTrace();
        }*/
        controllers = new ControllerManager(numberOfPads);
        controllers.initSDLGamepad();

        Config config = new Config();
        config.getConfigs();

        buttonActions=config.getButtonsAction();

        sound=new Sound(config);
        Thread thread = new Thread(sound);
        thread.start();

        track=-1;

        System.out.println("number of traks: "+ config.getNumberOfTreks());
        System.out.println("path to music from config.txt:");
        for(String s:config.traks()){
            System.out.println("\t"+s);
        }
        System.out.println();

        while(running){
            //time of refreshing of input
            //for power efficiency
            try{
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controllers.update();
            for (int i =0;i<numberOfPads;i++){
                ControllerIndex c = controllers.getControllerIndex(i);
                if( c.isConnected()){
                    try {
                        //System.out.print(c.getName()+",\t");
                        for (ControllerButton b : buttons) {
                            if (c.isButtonPressed(b)) {
                                //System.out.println("button " + b.name() + " is pressed");
                                switch (buttonActions.get(b.name())) {
                                    case "CLOSE" -> {
                                        sound.close();
                                        running = false;
                                        System.out.println(
                                                """
                                                            music and program are stopped
                                                            thanks for using this program

                                                        ######################################
                                                        ########### Have a nice day ##########
                                                        ######################################

                                                        https://github.com/jarek7410/pad_music""");
                                    }
                                    case "STOP" -> {
                                        sound.stop();
                                        System.out.println("music is stopped");
                                    }
                                    case "1" -> {
                                        System.out.println("track is changed");
                                        sound.setTrack(0,config);
                                        if(sound.getTrack()!=track){
                                            sound.stop();
                                            track=0;
                                        }
                                        sound.play();
                                    }
                                    case "2" -> {
                                        System.out.println("track is changed");
                                        sound.setTrack(1,config);
                                        if(sound.getTrack()!=track){
                                            sound.stop();
                                            track=1;
                                        }
                                        sound.play();
                                    }
                                    case "3" -> {
                                        System.out.println("track is changed");
                                        sound.setTrack(2,config);
                                        if(sound.getTrack()!=track){
                                            sound.stop();
                                            track=2;
                                        }
                                        sound.play();
                                    }
                                    case "4" -> {
                                        System.out.println("track is changed");
                                        sound.setTrack(3,config);
                                        if(sound.getTrack()!=track){
                                            sound.stop();
                                            track=3;
                                        }
                                        sound.play();
                                    }
                                    case "CHANGE" ->{
                                        if(track!=-1){
                                            sound.stop();
                                            sound.play();
                                        }
                                        System.out.println("Song is changed");
                                    }
                                    default -> System.out.println("not assigned: "+b.name());
                                }
                            }
                        }

                    } catch (ControllerUnpluggedException e) {
                        e.printStackTrace();
                    }
                }
            }

            //System.out.println();
        }

        controllers.quitSDLGamepad();
    }



}

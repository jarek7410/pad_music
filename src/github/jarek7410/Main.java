package github.jarek7410;

import com.studiohartman.jamepad.*;
import javazoom.jl.player.Player;


public class Main{

    private static boolean running=true;

    private static final int numberOfPads=4;
    private static ControllerManager controllers ;
    private static final ControllerButton[] buttons=ControllerButton.values();

    private static Player player;
    private static boolean playMusic = false;
    private static int track = 0;
    private static Sound sound;

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
        sound=new Sound(config);
        Thread thread = new Thread(sound);
        thread.start();

        track=-1;

        System.out.println("path to music from config.txt:");
        for(String s:config.traks()){
            System.out.println("\t"+s);
        }
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
                                System.out.println("button " + b.name() + " is pressed");
                                switch (b.name()) {
                                    case "BACK" -> {
                                        sound.close();
                                        running = false;
                                    }
                                    case "START" -> sound.stop();
                                    case "A" -> {
                                        sound.setTrack(0,config);
                                        if(sound.getTrack()!=track){
                                            sound.stop();
                                            track=0;
                                        }
                                        sound.play();
                                    }
                                    case "B" -> {
                                        sound.setTrack(1,config);
                                        if(sound.getTrack()!=track){
                                            sound.stop();
                                            track=1;
                                        }
                                        sound.play();
                                    }
                                    case "Y" -> {
                                        sound.setTrack(2,config);
                                        if(sound.getTrack()!=track){
                                            sound.stop();
                                            track=2;
                                        }
                                        sound.play();
                                    }
                                    case "X" -> {
                                        sound.setTrack(3,config);
                                        if(sound.getTrack()!=track){
                                            sound.stop();
                                            track=3;
                                        }
                                        sound.play();
                                    }
                                    case "RIGHTBUMPER" ->{
                                        if(track!=-1){
                                            sound.stop();
                                            sound.play();
                                        }
                                    }
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

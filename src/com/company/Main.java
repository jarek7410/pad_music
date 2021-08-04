package com.company;
//import java.util.Scanner;
import com.studiohartman.jamepad.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
//import com.studiohartman.jamepad.tester.ControllerTester;



public class Main implements Runnable{
    private static String testsong ="C:\\Users\\jarek\\Downloads\\the_witcher_soundtrack_mp3\\01 Dusk of a Northern Kingdom.mp3";
    private static boolean running=true;

    //private Scanner userInput = new Scanner(System.in);


    private static final int numberOfPads=1;
    private static ControllerManager controllers ;
    private static ControllerButton[] buttons=ControllerButton.values();

    private static Player player;
    private static boolean playMusic = false;
    private int track = 0;

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

        Main obj=new Main();
        Thread thread = new Thread(obj);
        thread.start();

        while(running){
            //time of refreshing of input
            //for power efficiency
            try{
                Thread.sleep(30L);
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
                                if (b.name() == "A") {
                                    running = false;
                                }else if (b.name() == "X") {
                                        if(!playMusic){
                                            playMusic=true;

                                        }

                                }else if(b.name()=="Y"){
                                    if(playMusic){
                                        player.close();
                                        playMusic=false;
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


    @Override
    public void run() {
        System.err.println("goooood");
        while(running){
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.print("loop, ");

            if(playMusic) {
                System.err.println("IT IS WORKING!!!!!");
                try {
                    InputStream is = new FileInputStream("C:\\Users\\jarek\\Downloads\\the_witcher_soundtrack_mp3\\04 Mighty.mp3");
                    player = new Player(is);
                    player.play();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
            }

        return;
    }
}

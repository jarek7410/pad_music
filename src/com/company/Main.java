package com.company;
//import java.util.Scanner;
import com.studiohartman.jamepad.*;
//import com.studiohartman.jamepad.tester.ControllerTester;



public class Main {

    private static boolean running=true;

    //private Scanner userInput = new Scanner(System.in);


    private static final int numberOfPads=1;
    private static ControllerManager controllers ;
    private static ControllerButton[] buttons=ControllerButton.values();

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
        while(running){
            //time of refreshing of input
            //for power efficiency
            try{
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controllers.update();
            for (int i =0;i<numberOfPads;i++){
                ControllerIndex c = controllers.getControllerIndex(i);
                if( c.isConnected()){
                    try {
                        System.out.print(c.getName()+",\t");
                        for (ControllerButton b : buttons) {
                            if (c.isButtonPressed(b)) {
                                System.out.println("button " + b.name() + " is pressed");
                                if (b.name() == "A") running = false;



                            }
                        }

                    } catch (ControllerUnpluggedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println();
        }

        controllers.quitSDLGamepad();
    }


}

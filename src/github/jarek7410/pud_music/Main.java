package github.jarek7410.pud_music;

import com.studiohartman.jamepad.*;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.HashMap;


public class Main{

    private static boolean running=true;

    private static final int numberOfPads=4;
    private static ControllerManager controllers ;
    private static final ControllerButton[] buttons=ControllerButton.values();
    //private static ControllerButton button;

    private static Player player;
    private static boolean playMusic = false;
    private static int track = 0;
    private static Sound sound;
    private static JFrame frame;
    private static JPanel panel;
    private static Window window;
    private static Container c;
    private static HashMap<String, String> buttonActions;
    private static Config config;
    private static Thread threadSound;

    public static void main(String[] args) {
        /*(try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable var2) {
            System.out.println("Error setting lookandfeel");
            var2.printStackTrace();
        }*/
        controllers = new ControllerManager(numberOfPads);
        controllers.initSDLGamepad();

        config  = new Config();
        config.loadConfigs();

        buttonActions=config.getButtonsAction();

        frame = new JFrame();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(640, 350));
        frame.setLocationRelativeTo((Component)null);
        frame.setVisible(true);
        frame.setTitle("pad music");
        frame.setLayout(new BorderLayout());
        window =new Window(config);
        window.setSize(new Dimension(640,350));

        c =frame.getContentPane();

        //c.add(new JLabel("working"));

        window =new Window(config);


        panel = new JPanel();
        panel.setMinimumSize(new Dimension(640,350));
        c.add(panel,BorderLayout.CENTER);


        sound = new Sound(config, window);
        threadSound =new Thread(sound);
        threadSound.start();

        track=-1;


        window.setSong("nothing is played");
        window.setPause();
        window.setMinimumSize(new Dimension(640,350));

        windowPrint();

        info();

        run();
    }

    private static void run(){
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
                                //button=b;
                                use(buttonActions.get(b.name()));
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

    private static void windowPrint(){
        panel.removeAll();
        panel.setSize(new Dimension(640,350));
            window.updatePanel();
        panel.add(window);
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        frame.validate();
        frame.repaint();
    }

    public static void use(String action){
        switch (action) {
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

                frame.dispatchEvent
                        (new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

            }
            case "STOP" -> {
                sound.stop();
                System.out.println("music is stopped");
                window.setPause();
            }
            case "ONE" -> play(0);
            case "TWO" -> play(1);
            case "THREE" -> play(2);
            case "FOUR" -> play(3);
            case "CHANGE" ->{
                if(track!=-1){
                    sound.stop();
                    sound.play();
                }
                System.out.println("Song is changed");
            }
            default -> System.out.println
                    ("wrong action");
        }
        windowPrint();
    }

    private static void info(){
        System.out.println("number of traks: "+ config.getNumberOfTreks());
        System.out.println("path to music from config.txt:");
        for(String s:config.traks()){
            System.out.println("\t"+s);
        }
        System.out.println("action assignment:");
        for(Actions a:Actions.values()){
            System.out.println(config.getActionButtons().get(a.name())+
                    ":\t"+a.name());
        }
        System.out.println();
    }

    private static void play(int Track){

        System.out.println("track is changed");
        sound.setTrack(Track,config);
        if(sound.getTrack()!=track){
            sound.stop();
            track=Track;
        }
        sound.play();
    }


}

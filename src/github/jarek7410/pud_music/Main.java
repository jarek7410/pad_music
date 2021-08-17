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

        config  = new Config();
        config.loadConfigs();

        argsHandling(args);
        /*(try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable var2) {
            System.out.println("Error setting lookandfeel");
            var2.printStackTrace();
        }*/
        controllers = new ControllerManager(numberOfPads);
        controllers.initSDLGamepad();


        buttonActions=config.getButtonsAction();

        if(config.frameRun)setWindow();

        sound = new Sound(config, window);
        threadSound =new Thread(sound);
        threadSound.start();

        track=-1;

        if(config.frameRun)windowPrint();

        info();

        run();
    }

    private static void setWindow(){
        frame = new JFrame();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(config.winDim);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("pad music");
        frame.setLayout(new BorderLayout());
        setMenu();

        c =frame.getContentPane();

        //c.add(new JLabel("working"));

        window =new Window(config);


        panel = new JPanel();
        panel.setMinimumSize(config.winDim);
        c.add(panel,BorderLayout.CENTER);


        window.setSong("nothing is played");
        window.setPause();
    }
    private static void setMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu setting = new JMenu("Settings");
        JMenuItem defaults = new JMenuItem("set Default ");
        JMenuItem i1 = new JMenuItem("item1");
        JMenuItem i2 = new JMenuItem("item2");
        setting.add(i1);setting.add(defaults);setting.add(i2);
        mb.add(setting);
        frame.setJMenuBar(mb);


    }

    private static void argsHandling(String[] args) {
        for(int i=0;i<args.length;i++){
            if(args[i].charAt(0)=='-'){
                if(args[i].equals("-window")){
                    if(args[i+1].equals("off")||args[i+1].equals("OFF"))config.frameRun=false;
                }
            }
        }
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

            if(config.frameRun)windowPrint();



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
                                try{
                                    use(buttonActions.get(b.name()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }finally {
                                    System.out.println("button is not in use");
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
        sound.close();

        controllers.quitSDLGamepad();
    }

    private static void windowPrint(){
        //panel.removeAll();
        window.updatePanel();
        panel.add(window);

        c.validate();
        c.repaint();
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

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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
        System.out.println("thread number: " + Thread.activeCount());
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

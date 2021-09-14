package github.jarek7410.pud_music;

import com.studiohartman.jamepad.*;
import github.jarek7410.pud_music.options.ButtonInfo;
import github.jarek7410.pud_music.options.OptionWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;


public class Main{


    private static final int numberOfPads=4;
    private static ControllerManager controllers ;
    private static final ControllerButton[] buttons=ControllerButton.values();
    //private static ControllerButton button;

    private static Sound sound;
    private static JFrame frame;
    private static JPanel panel;
    private static Window window;
    private static Container c;
    private static HashMap<String, String> buttonActions;
    private static Config config;
    private static Thread threadSound;

    private static JMenuItem buttonInfo;
    private static JMenuItem tester;

    private static ButtonInfo in;
    private static OptionWindow op;

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

        config.track=-1;

        if(config.frameRun)windowPrint();

        info();

        run();
    }

    private static void setWindow(){
        frame = new JFrame();


        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setSize(config.winDim);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("pad music");
        frame.setLayout(new BorderLayout());
        setMenu();

        c =frame.getContentPane();


        window =new Window(config);


        panel = new JPanel();
        panel.setMinimumSize(config.winDim);
        c.add(panel,BorderLayout.CENTER);


        window.setSong("nothing is played");
        window.setPause();
        //setMenu();

    }

    private static void setMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu setting = new JMenu("info");
        buttonInfo = new JMenuItem("Buttons info");
        tester = new JMenuItem("Button tester");
        JMenuItem i2 = new JMenuItem("item2");

        ActionListener l= e -> {
            Object o=e.getSource();
            if(o.equals(buttonInfo)){
                ///Thread t;
                 in=new ButtonInfo(config);
                //t = new Thread(in);
                ///t.start();
                in.run();
            }else if(o.equals(tester)){
                 op=new OptionWindow(config,window);
                op.run();
            }
        };
        buttonInfo.addActionListener(l);
        tester.addActionListener(l);




        setting.add(tester);setting.add(buttonInfo);setting.add(i2);
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
        while(config.running){
            //time of refreshing of input
            //for power efficiency

            try{
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //if(!frame.isActive())use("CLOSE");
            if(!frame.isVisible())use("CLOSE");

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

    protected static void windowPrint(){
        //panel.removeAll();
        window.updatePanel();
        panel.add(window);

        c.validate();
        c.repaint();
    }

    public static void use(String action){
        switch (action) {
            case "CLOSE" -> {
                if(sound!=null)sound.close();
                config.running = false;
                System.out.println(
                        """
                                --------------------------------------
                                    
                                    music and program are stopped
                                    thanks for using this program

                                ######################################
                                ########### Have a nice day ##########
                                ######################################

                                https://github.com/jarek7410/pad_music""");
                try {
                    if(frame!=null)frame.dispatchEvent
                            (new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    if(in!=null)in.dispatchEvent
                            (new WindowEvent(in, WindowEvent.WINDOW_CLOSING));
                    if(op!=null)op.dispatchEvent
                            (new WindowEvent(op, WindowEvent.WINDOW_CLOSING));
                }catch (Exception e){
                    System.err.println("error: closing of windows");
                }
            }
            case "STOP" -> {
                try{
                    sound.stop(-2);
                }catch (Exception e){
                    System.err.println("player is not set up\nchoose correct track\n "+Thread.currentThread().getName());

                }
                System.out.println("music is stopped");
                if(config.frameRun) {
                    if(window.lastPressedActionButton!=1)window.lastTrackPlayed =window.lastPressedActionButton;
                    window.lastButtonPressed(1);
                }
                //window.setPause();
            }
            case "ONE" -> play(0);
            case "TWO" -> play(1);
            case "THREE" -> play(2);
            case "FOUR" -> play(3);
            case "FIVE" -> play(4);
            case "SIX" -> play(5);
            case "SEVEN" -> play(0);
            case "EIGHT" -> play(7);
            case "CHANGE" ->{
                if(config.track!=-1){
                    sound.stop();
                    sound.play();
                    if(config.frameRun)window.lastButtonPressed(window.lastTrackPlayed);
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
        for(String s:config.getTraks()){
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
        if(Track!=config.track){
            config.setTrack(Track);
            sound.stop();
        }
        sound.play();
        if(config.frameRun)window.lastButtonPressed(config.track+3);
    }


}

package github.jarek7410.pud_music.options;

import github.jarek7410.pud_music.Actions;
import github.jarek7410.pud_music.Config;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ButtonInfo extends JFrame implements Runnable{
    Config config;

    JFrame frame;
    Container c;

    public ButtonInfo(Config config){
        this.config=config;
    }
    public void run(){
        frame = new JFrame();

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(180, Actions.values().length*20));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Button info");
        frame.setLayout(new GridLayout(Actions.values().length,1));

         HashMap<String, String> h =config.getActionButtons();
         Actions[] a =Actions.values();
         JLabel[]labels=new JLabel[a.length];
         JPanel p;
         for(int i =0;i<a.length;i++){
             labels[i]=new JLabel(a[i].name()+" : " +h.get(a[i].name()));
             frame.add(labels[i]);
         }
    }
}

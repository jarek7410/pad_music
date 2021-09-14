package github.jarek7410.pud_music.options;

import com.studiohartman.jamepad.ControllerButton;
import github.jarek7410.pud_music.Actions;
import github.jarek7410.pud_music.Config;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ActionButtonListWindow {
    Config config;

    JFrame frame;
    Container c;

    ActionButtonListWindow(Config config){
        this.config=config;
    }
    public void run(){
        frame = new JFrame();

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(200, Actions.values().length*20));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("key words list");
        frame.setLayout(new GridLayout(ControllerButton.values().length,1));


        Actions[] a =Actions.values();
        ControllerButton[] b=ControllerButton.values();
        for(ControllerButton bb:b){
            frame.add(new JLabel(bb.name()));
        }
        for(Actions aa:a){
            frame.add(new JLabel(aa.name()));
        }
    }
}

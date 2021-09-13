package github.jarek7410.pud_music.options;

import com.studiohartman.jamepad.ControllerButton;
import github.jarek7410.pud_music.Actions;
import github.jarek7410.pud_music.Config;
import github.jarek7410.pud_music.ListOfFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class OptionWindow extends JFrame{
    private Config config;
    public OptionWindow(Config config){
        this.config=config;
    }



    public void run() {
        this.setVisible(true);


        //this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(600,300);
        this.setLayout(new CardLayout());

        JPanel actionButtons=new JPanel();
        actionButtons.setLayout(new FlowLayout());
        //actionButtons.setPreferredSize(new Dimension(200,500));

        HashMap<String, String> hash =config.getActionButtons();
        actionButtonView a;
        JButton options=new JButton("tekst");
        options.setPreferredSize(new Dimension(150,40));
        options.addActionListener(e -> config.saveConfigs());
        options.setText("actions and buttons list");
        JPanel panel=new JPanel();
        panel.add(options);
        int i=1;
        Vector<String> listButtons=new Vector<>();
        for(ControllerButton b:ControllerButton.values()){
            listButtons.addElement(b.name());
        }
        String[] listOfButtons= ListOfFiles.VtoS(listButtons);
        for(Actions actions:Actions.values()){
            a=new actionButtonView();
            a.set(actions.name(),hash.get(actions.name()),listOfButtons);
            actionButtons.add(a);
        }

        actionButtons.add(panel);
        this.add(actionButtons);
    }

    class actionButtonView extends JPanel{
        public void set(String action,String button,String[] list){
            JLabel label=new JLabel(action);
            label.setPreferredSize(new Dimension(60,20));
            this.add(label);
            this.add(new JLabel(":"));
            JComboBox<String> field=new JComboBox(list);
            field.setSelectedItem(button);
            field.addActionListener(e -> {
                JComboBox<String> box= (JComboBox) e.getSource();
                config.changeOptionsBAAB((String) box.getSelectedItem(),box.getName());

            });
            field.setName(action);
            field.setPreferredSize(new Dimension(150,20));
            this.add(field);
        }
    }
}

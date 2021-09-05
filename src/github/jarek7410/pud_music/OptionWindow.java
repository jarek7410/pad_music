package github.jarek7410.pud_music;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

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
        this.setSize(400,200);

        JPanel actionButtons=new JPanel();
        actionButtons.setLayout(new GridLayout(20,1));
        actionButtons.setPreferredSize(new Dimension(200,350));
        this.add(actionButtons);

        HashMap<String, String> hash =config.getActionButtons();
        actionButtonView a=new actionButtonView();
        int i=1;
        for(Actions actions:Actions.values()){
            a.set(actions.name(),hash.get(actions.name()));
            actionButtons.add(a);
        }
    }

    class actionButtonView extends JPanel{
        public void set(String action,String button){
            JLabel label=new JLabel(action);
            label.setPreferredSize(new Dimension(60,20));
            this.add(label);
            this.add(new JLabel(":"));
            JTextField field=new JTextField(button);
            field.setPreferredSize(new Dimension(100,20));
            this.add(field);
        }
    }
}
